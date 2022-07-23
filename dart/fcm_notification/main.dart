import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

void main(List<String> args) async {
  const idPostCollection = 'post'; // Your collection
  const idTitleAttribute = 'title'; // Your title attribut
  const idBodyAttribute = 'body'; // Your body attribut

  // Env variables
  final Map<String, String> envVars = Platform.environment;
  if (!isEnvVariableExist(envVars)) exit(1);

  String functionData = envVars['APPWRITE_FUNCTION_EVENT_DATA'].toString();
  Map<String, dynamic> payload = json.decode(functionData);

  print('Payload: $payload');

  String? collectionId = payload[r'$collection'];
  if (collectionId != idPostCollection) {
    print('This is not the expected collection: $collectionId');
    exit(1);
  }

  String? title = payload[idTitleAttribute];
  if (title == null) {
    print('Title is null');
    exit(1);
  }

  String? body = payload[idBodyAttribute];
  if (body == null) {
    print('Body is null');
    exit(1);
  }

  http.Response fcmResponse = await http.post(
    Uri.parse('https://fcm.googleapis.com/fcm/send'),
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'key=' + envVars['FCM_SERVER_KEY']!,
    },
    body: jsonEncode({
      'notification': {
        'title': title,
        'body': body,
      },
      'priority': 'high',
      'to': '/topics/post',
    }),
  );

  if (fcmResponse.statusCode != 200) {
    print('Notification not delivered: ' + fcmResponse.body);
  } else {
    print('Notification sent: ' + fcmResponse.body);
  }
}

bool isEnvVariableExist(Map<String, String> envVars) {
  bool result = true;

  List<String> keys = [
    'APPWRITE_FUNCTION_EVENT_DATA',
    'FCM_SERVER_KEY',
  ];

  for (String key in keys) {
    String? value = envVars[key];
    if (value == null) {
      print('Could not find environment variable $key.');
      result = false;
    }
  }

  return result;
}
