import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

void main() async {
  Map<String, String> env = Platform.environment;
  Uri url = Uri();
//   Create a giphy search url
  url = url.replace(
    scheme: 'https',
    host: 'api.giphy.com',
    path: 'v1/gifs/search',
    queryParameters: {
      'api_key': env['GIPHY_API_KEY'],
      'q': env['APPWRITE_FUNCTION_DATA'],
      'limit': '1',
    },
  );
  // print(url);
  http.Response response = await http.get(url);
  var data = json.decode(response.body);
  print(data['data'][0]['url']);
}
