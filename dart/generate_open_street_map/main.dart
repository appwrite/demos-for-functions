import 'dart:convert';
import 'dart:math';
import 'dart:io';

import 'package:http/http.dart' as http;
import 'package:dart_appwrite/dart_appwrite.dart';
import 'package:vector_math/vector_math.dart';

const zoomLevel = 15;

String ensureEnvVariable(String key) {
  final value = Platform.environment[key];
  if (value == null) {
    print(
        'Could not find environment variable $key. Please set it following the instructions in the readme file.');
    exit(1);
  }

  return value;
}

// Adapted from https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Mathematics
Point<int> coordinatesToTilePoint(double latitude, double longitude) {
  final x = (pow(2, zoomLevel) * ((longitude + 180) / 360)).floor();

  final radLat = radians(latitude);
  final y = (pow(2, zoomLevel - 1) *
          (1 - (log(tan(radLat) + (1 / cos(radLat))) / pi)))
      .floor();

  return Point(x, y);
}

Future<List<int>> fetchTile(double latitude, double longitude) async {
  final point = coordinatesToTilePoint(latitude, longitude);
  final tileUrl =
      'https://tile.openstreetmap.org/${zoomLevel}/${point.x}/${point.y}.png';

  print(tileUrl);

  final response = await http.get(Uri.parse(tileUrl));
  if (response.statusCode != 200) {
    print('There was an error loading the tile for $point: ${response.body}');
    exit(1);
  }

  return response.bodyBytes;
}

void main() async {
  final client = Client();
  client
      .setKey(ensureEnvVariable('APPWRITE_API_KEY'))
      .setProject(ensureEnvVariable('APPWRITE_FUNCTION_PROJECT_ID'))
      .setEndpoint(ensureEnvVariable('APPWRITE_ENDPOINT'));

  final data = jsonDecode(ensureEnvVariable('APPWRITE_FUNCTION_DATA'));

  final storage = Storage(client);
  final latitude = data['latitude'];
  final longitude = data['longitude'];

  final imageBytes = await fetchTile(latitude, longitude);
  final filename = 'osm_tile_${latitude}_${longitude}_z$zoomLevel.png';

  await storage.createFile(
    file: MultipartFile.fromBytes('file', imageBytes, filename: filename),
  );
}
