import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:dart_appwrite/dart_appwrite.dart';

void main(List<String> args) async {
  // Initialise the client SDK
  Map<String, String> envVars = Platform.environment;
  final Client client = Client();
  client
      .setEndpoint(envVars['APPWRITE_ENDPOINT']!) // This is manually set
      .setProject(envVars[
          'APPWRITE_FUNCTION_PROJECT_ID']) // this is available by default
      .setKey(envVars['APPWRITE_API_KEY']);

  // Initialise the storage SDK
  final storage = new Storage(client);

  // Read the file ID and file Name
  var payload = json.decode(envVars['APPWRITE_FUNCTION_EVENT_DATA']!);
  var fileId = payload['\$id'];
  var fileName = payload['name'];

// Download the file
  var image = await storage.getFileDownload(fileId: fileId);

// API call to Cloudmersive Image Recognition and Processing API
  final Endpoint =
      Uri.parse("https://api.cloudmersive.com/image/recognize/detect-objects");

  final headers = {
    'Content-Type': 'multipart/form-data',
    'Apikey': envVars['CLOUDMERSIVE_API_KEY']!
  };

  final response =
      await http.post(Endpoint, body: image.data, headers: headers);

  var objects = [];

// Processing response
  if (response.statusCode != 200) {
    throw Exception('Could not detect objects in  $fileName');
  } else {
    final res = json.decode(response.body);

    for (var item in res["Objects"]) {
      objects.add(item['ObjectClassName']);
    }
  }

  print("Objects found in $fileName are: ${objects}");
}
