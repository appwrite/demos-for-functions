import 'dart:convert';
import 'package:http/http.dart' as http;

Future<void> start(final request, final response) async {
  Uri url = Uri();
  if(request.env['GIPHY_API_KEY'] == null) {
    throw Exception("GIPHY_API_KEY is required");
  }

  url = url.replace(
    scheme: 'https',
    host: 'api.giphy.com',
    path: 'v1/gifs/search',
    queryParameters: {
      'api_key': request.env['GIPHY_API_KEY'], // The Api key which we will store in Appwrite Console.
      //  Note you can change the var name to something else too.

      'q': env['APPWRITE_FUNCTION_DATA'], // The search query which we will pass during execution
      'limit': '1', // Since we want the first top result we set the limit to 1.
      //  This parameter is optional. To read about what parameters you can pass .
      //  https://developers.giphy.com/docs/api/endpoint#search -> Refer to this
    },
  );
  
  http.Response response = await http.get(url);

  var data = json.decode(response.body);
  response.send(data['data'][0]['url']);
}
