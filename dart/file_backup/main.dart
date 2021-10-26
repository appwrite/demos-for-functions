import 'dart:convert';
import 'dart:io' show Platform, exit;
import 'dart:typed_data';

import 'package:dart_appwrite/dart_appwrite.dart';
import 'package:http/http.dart' as http;

Future<void> backup(
  Uint8List buffer,
  String originalFileName,
  String dropboxAuthToken,
) async {
  final dropboxEndpoint =
      Uri.parse('https://content.dropboxapi.com/2/files/upload');

  final headers = {
    'Authorization': 'Bearer $dropboxAuthToken',
    'Content-Type': 'application/octet-stream',
    'Dropbox-API-Arg': '{"path": "/$originalFileName", "mode": "add" }'
  };

  final dropboxResponse =
      await http.post(dropboxEndpoint, body: buffer, headers: headers);

  if (dropboxResponse.statusCode != 200) {
    throw Exception(
        'Could not backup file $originalFileName! Dropbox response was: ${dropboxResponse.body}');
  }
}

void main(List<String> args) async {
  final env = Platform.environment;
  final endpoint = env['APPWRITE_ENDPOINT'];
  if (endpoint == null) {
    print(
        'Appwrite endpoint not specified. Please set an environment variable "APPWRITE_ENDPOINT" with your endpoint to the function');
    exit(1);
  }

  final client = Client()
    ..setEndpoint(endpoint)
    ..setProject(env['APPWRITE_FUNCTION_PROJECT_ID'])
    ..setKey(env['APPWRITE_API_KEY']);

  final payload = jsonDecode(env['APPWRITE_FUNCTION_EVENT_DATA']!);
  final fileId = payload[r'$id']!;
  final originalName = payload[r'name']!;

  final storage = Storage(client);

  final dropboxKey = env['DROPBOX_KEY'];
  if (dropboxKey == null) {
    print(
        'Dropbox key not specified. Please set an environment variable "DROPBOX_KEY" containing your dropbox key');
  }

  final response = await storage.getFileDownload(fileId: fileId);

  try {
    await backup(response.data!, originalName, dropboxKey!);
    print('Successfully backed up $originalName');
  } catch (e) {
    print('Failed backing up the file: $e');
    exit(1);
  }
}
