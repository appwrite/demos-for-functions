import 'dart:convert';
import 'dart:io';

import 'src/twilio.dart';

String ensureEnvVariable(String key) {
  final value = Platform.environment[key];

  if (value == null) {
    print(
        'Could not find environment variable $key. Please set it following the instructions in the readme file.');
    exit(1);
  }

  return value;
}

void main(List<String> arguments) async {
  final _accountSid = ensureEnvVariable('TWILIO_ACCOUNT_SID');
  final _authToken = ensureEnvVariable('TWILIO_AUTH_TOKEN');
  final _sender = ensureEnvVariable('TWILIO_SENDER');

  final data = jsonDecode(ensureEnvVariable('APPWRITE_FUNCTION_DATA'));

  // Create an authenticated client instance for Twilio API
  var client = Twilio(_accountSid, _authToken);

  Map message = await client.messages.create(
    {
      'body': data['text'],
      'from': _sender,
      'to': data['phoneNumber'],
    },
  );

  print(message);
}
