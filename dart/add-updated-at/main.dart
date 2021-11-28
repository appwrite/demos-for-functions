import 'dart:io';
import 'dart:convert';
import 'package:dart_appwrite/dart_appwrite.dart';

String ensureEnvVariable(String key) {
  final value = Platform.environment[key];
  if (value == null) {
    print(
        'Could not find environment variable $key. Please set it following the instructions in the readme file.');
    exit(1);
  }
  //print('Value for $key is $value!!');
  return value;
}

void main(List<String> args) async {
  // Initialise the client SDK
  Map<String, String> envVars = Platform.environment;
  final Client client = Client();
  client
      .setEndpoint(envVars['APPWRITE_ENDPOINT']!) // This is manually set
      .setProject(envVars[
          'APPWRITE_FUNCTION_PROJECT_ID']) // this is available by default
      .setKey(envVars['APPWRITE_API_KEY']);

  String functionData = envVars['APPWRITE_FUNCTION_EVENT_DATA'].toString();
  Map<String, dynamic> payload = json.decode(functionData);
  String UPDATED_AT_KEY = "updatedAt";
  final collectionId = payload["\$collection"];
  final documentId = payload["\$id"];
  print('Collection ID: $collectionId, Document ID: $documentId');
  final currentTimeStamp = DateTime.now().toString();
  print('Current Time Stamp: $currentTimeStamp');
  if (!payload.containsKey(UPDATED_AT_KEY)) {
    print("Collection doesn't contain \"updatedAt\" key!");
    return;
  }

  Database database = new Database(client);
  Response response = await database.updateDocument(
    collectionId: collectionId,
    documentId: documentId,
    data: {UPDATED_AT_KEY: currentTimeStamp},
  );
  print(response.data);
  print("Updated the field updatedAt with current timestamp successfully!");
}
