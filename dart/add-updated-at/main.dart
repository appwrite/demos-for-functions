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

  Map<String, dynamic> payload =
      jsonDecode(ensureEnvVariable('APPWRITE_FUNCTION_EVENT_DATA'));
  var UPDATED_AT_KEY = "updatedAt";
  final collectionId = payload["\$collection"];
  final documentId = payload["\$id"];
  final currentTimeStamp = DateTime.now();

  if (!payload.containsKey(UPDATED_AT_KEY)) {
    return;
  }

  final Database database = Database(client);
  database.updateDocument(
    collectionId: collectionId,
    documentId: documentId,
    data: {UPDATED_AT_KEY: currentTimeStamp},
  );

  print("Updated the field updatedAt with current timestamp successfully!");
}
