import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

String ensureEnvVariable(String key) {
  final value = Platform.environment[key];
  if (value == null) {
    print(
        'Could not find environment variable $key. Please set it following the instructions in the readme file.');
    exit(1);
  }

  return value;
}

// Dart by default appends a charset to the Content-Type while SendGrid rejects
//  exactly that. By using this client, we can force a clean content type.
class CleanContentTypeClient extends http.BaseClient {
  final http.Client _inner;
  final String _contentType;

  CleanContentTypeClient(this._contentType, this._inner);

  Future<http.StreamedResponse> send(http.BaseRequest request) {
    request.headers['Content-Type'] = _contentType;
    return _inner.send(request);
  }
}

void main() async {
  final email = Platform.environment['APPWRITE_FUNCTION_DATA']!;
  final sendgridApiKey = ensureEnvVariable('SENDGRID_API_KEY');
  final newsletterListId = ensureEnvVariable('SENDGRID_NEWSLETTER_ID');

  final url = Uri.parse('https://api.sendgrid.com/v3/marketing/contacts');
  final headers = {
    'Authorization': 'Bearer $sendgridApiKey',
  };
  final body = {
    'list_ids': [newsletterListId],
    'contacts': [
      {"email": email}
    ],
  };

  final client = http.Client();
  final contentTypeClient = CleanContentTypeClient('application/json', client);
  final response = await contentTypeClient.put(
    url,
    headers: headers,
    body: jsonEncode(body),
  );
  if (response.statusCode != 202) {
    print(
        'Error adding contact to list! ${response.statusCode} ${response.body}');
    exit(1);
  }

  print('Successfully queued contact to be added to the list!');
}
