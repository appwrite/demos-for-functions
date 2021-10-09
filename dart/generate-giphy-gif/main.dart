import 'dart:convert';
import 'dart:io' show Platform;

// We are using an external http package to make the http requests.
import 'package:http/http.dart' as http;

void main() async {
  //  We are creating a variable env to store the environment variables.
  // If you hover over the description the Platform you can see the explanation of what
  //  exactly this does.
  Map<String, String> env = Platform.environment;
  Uri url = Uri();
//   Create a giphy search url
  url = url.replace(
    scheme: 'https',
    host: 'api.giphy.com',
    path: 'v1/gifs/search',
    queryParameters: {
      'api_key': env['GIPHY_API_KEY'], // The Api key which we will store in Appwrite Console.
      //  Note you can change the var name to something else too.

      'q': env['APPWRITE_FUNCTION_DATA'], // The search query which we will pass during execution
      'limit': '1', // Since we want the first top result we set the limit to 1.
      //  This parameter is optional. To read about what parameters you can pass .
      //  https://developers.giphy.com/docs/api/endpoint#search -> Refer to this
    },
  );
  // print(url);  A test print to see if the url formation is correct

  //  Finally we are making an http request to the url.
  //  Since the data we will receive is in json format we are using the jsonDecode
  http.Response response = await http.get(url);

  //  To check which data you are receiving either print this or add a breakpoint
  //  to check the values
  var data = json.decode(response.body);

  //  This will print the url and thus the response.
  print(data['data'][0]['url']);
}
