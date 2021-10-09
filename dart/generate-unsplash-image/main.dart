import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

void main(List<String> arguments) async {
  Map<String, String> envVars = Platform.environment;

  final String unsplashKey = envVars['UNSPLASH_ACCESS_KEY']!;

  final unsplash = UnSplashSearch();

  // Get the keyword to search on unsplash from Appwrite's environment variable
  final keyword = envVars['APPWRITE_FUNCTION_DATA']!;

  // perform request
  final result = await unsplash.searchImage(keyword, unsplashKey);

  if (result == null) {
    print('There was a problem searching image with keyword: $keyword');
  }

  // er
  else {
    print(result.toString());
  }
}

// helper service class
class UnSplashSearch {
  static final _baseUrl = 'https://api.unsplash.com/search/photos?page=1&';

  /// search image using keyword passed and return map with image author and link
  Future<Map<String, dynamic>?> searchImage(
    String keyword,
    String accessKey,
  ) async {
    final _client = http.Client();

    try {
      String query = "query=" + keyword;
      String publicAuth = "&client_id=" + accessKey;
      String fullUrl = _baseUrl + query + publicAuth;

      final Uri _url = Uri.parse(fullUrl);

      final response = await _client.get(_url);

      if (response.statusCode == 200) {
        final _resp = jsonDecode(response.body) as Map<String, dynamic>;

        final searchResult = _resp["results"][0];

        return {
          "imageAuthor": searchResult["user"]["name"],
          "imageUrl": searchResult["links"]["download"],
        };
      }

      // exception
      else {
        final _resp = jsonDecode(response.body) as Map<String, dynamic>;
        print(_resp['errors']);
      }
    }

    // http err
    on http.ClientException catch (ce) {
      print(ce.message);
    }

    // err
    catch (e) {
      print(e.toString());
    }

    // close
     finally {
      _client.close();
    }
  }
}
