import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;

main(List<String> args) async {
  Map<String, String> envVars = Platform.environment;

  // Assert environment variables are set
  if (envVars["MAILCHIMP_API_KEY"] == null)
    throw Exception("MAILCHIMP_API_KEY is not defined.");
  if (envVars["MAILCHIMP_LIST_ID"] == null)
    throw Exception("MAILCHIMP_LIST_ID is not defined.");
  if (envVars["APPWRITE_FUNCTION_DATA"] == null)
    throw Exception("You need to pass an email.");

  // Get the API parameters from Appwrite's environment variable
  final String apiKey = envVars['MAILCHIMP_API_KEY']!;
  final String listId = envVars['MAILCHIMP_LIST_ID']!;
  final String email = envVars['APPWRITE_FUNCTION_DATA']!;
  final String dc = apiKey.split('-').last;

  // constants
  final defaultStatus = 'subscribed';
  final String url = 'https://$dc.api.mailchimp.com/3.0/lists/$listId/members';

  final body = {'email_address': email, 'status': defaultStatus};
  final headers = {
    'Authorization': 'apikey $apiKey',
    'Content-Type': 'application/json'
  };

  try {
    var response = await http.post(
      Uri.parse(url),
      headers: headers,
      body: jsonEncode(body),
    );
    print(response.body);
  } catch (e) {
    print(e);
  }
}
