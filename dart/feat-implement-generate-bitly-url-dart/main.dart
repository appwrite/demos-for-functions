import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

void main(List<String> arguments) async {
  Map<String, String> envVars = Platform.environment;

  // create service class instance
  final shortener = BitlyUrlShortener();

  final String bitlyToken = envVars['BITLY_TOKEN']!;

  // Get the long url from Appwrite's environment variable
  final payload = jsonDecode(envVars['APPWRITE_FUNCTION_DATA']!);

  final longUrl = payload['url'];

  // create header payload
  final Map<String, String> _headers = {
    'Authorization': 'Bearer $bitlyToken',
    'Content-Type': 'application/json',
  };

// create payload
  final Map<String, String> _payload = {
    "long_url": longUrl,
    "domain": "bit.ly",
    //"group_guid": "Ba1bc23dE4F"
  };

  // perform request
  final result = await shortener.shortLink(_headers, _payload);

  if (result == null) {
    print('Failed to shorten url: $longUrl');
  }

  // er
  else {
    print(result.toString());
  }
}

// helper service class
class BitlyUrlShortener {
  static final Uri _url = Uri.parse('https://api-ssl.bitly.com/v4/shorten');

  Future<String?> shortLink(
    Map<String, String> headers,
    Map<String, String> payload,
  ) async {
    final _client = http.Client();

    try {
      final response =
          await _client.post(_url, headers: headers, body: payload);

      final _resp = jsonDecode(response.body) as Map<String, dynamic>;

      if (response.statusCode == 200 || response.statusCode == 201) {
        // success, return link

        return _resp['link'];
      }

      // exception
      else {
        return _resp['description'];
      }
    }

    // http err
    on http.ClientException catch (ce) {
      return ce.message;
    }

    // err
    catch (e) {
      return e.toString();
    }

    // close
     finally {
      _client.close();
    }
  }
}
