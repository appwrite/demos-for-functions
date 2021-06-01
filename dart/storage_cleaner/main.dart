import 'dart:io';

import 'package:dart_appwrite/dart_appwrite.dart';

void main(List<String> args) async {
  // Initialise the client SDK
  Map<String, String> envVars = Platform.environment;
  final Client client = Client();
  client
      .setEndpoint(envVars['APPWRITE_ENDPOINT'] ?? 'https://localhost/v1') // This is manually set
      .setProject(envVars['APPWRITE_FUNCTION_PROJECT_ID']) // this is available by default
      .setKey(
          envVars['APPWRITE_API_KEY']);

  // Initialise the storage SDK
  final storage = new Storage(client);

  int daysToExpire = int.parse(envVars['DAYS_TO_EXPIRE'] ?? "100");

  final res = await storage.listFiles(orderType: OrderType.desc, limit: 100);
  final data = res.data;
  final files = List.from(data['files']);
  var timestamp = DateTime.now()
      .subtract(Duration(days: daysToExpire))
      .millisecondsSinceEpoch;
  var deletedFiles = 0;
  for (final file in files) {
    if (file['dateCreated'] * 1000 < timestamp) {
      await storage.deleteFile(fileId: file['\$id']);
      print("Deleted ${file['\$id']}");
      deletedFiles++;
    }
  }
  print("Total files deleted: $deletedFiles");
}
