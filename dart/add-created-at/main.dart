import 'package:dart_appwrite/dart_appwrite.dart';
import 'dart:io';
import 'dart:convert';

void main() async {
Map<String, String> environmentVariables = Platform.environment;
final Client client = Client();
client
.setEndpoint(environmentVariables['APPWRITE_ENDPOINT'])
.setProject(environmentVariables['APPWRITE_PROJECT_ID'])
.setKey(environmentVariables['API_KEY']);

final db = Database(client); //initialising database

// Get the collectionID and also the ID of the newly created document from Appwrite's environment variable
  final payload = jsonDecode(environmentVariables['APPWRITE_FUNCTION_EVENT_DATA']);


  final String documentId = payload['\$id'];
  final String collectionId = payload['\$collection'];
  var timestamp = DateTime.now().millisecondsSinceEpoch;

  try{
    var documentUpdateResult = await db.updateDocument(
    collectionId: collectionId,
    documentId: documentId,
    data: {
      'createdAt': timestamp
    },
  );
  print('Updated document successfully. $documentUpdateResult');
  }catch(error){
    print('Error updating document $error');
  }

}