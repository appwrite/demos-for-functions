import 'dart:io';

import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:dart_appwrite/dart_appwrite.dart';

void compressAndStore(String sourceUrl) async {
  try {
    var url = Uri.parse("https://api.tinify.com/shrink");
    // String tinyPNGApiKey = "zDm5Pb08BxphdRZp4ndw0vnvqVTSXlWl";
    String tinyPNGApiKey = Platform.environment['TINYPNG_API_KEY'];
    String base64Encoded = base64.encode(utf8.encode('api:$tinyPNGApiKey'));

    Map data = {
      "source": {"url": sourceUrl}
    };

    String body = jsonEncode(data);
    var header = {
      'Authorization': 'Basic $base64Encoded',
      'Content-Type': 'application/json'
    };
    // print(header)

    // Compress Image
    http.Response response = await http.post(
      url,
      headers: header,
      body: body,
    );

    // print(response.body)

    // if(response.statusCode != 200) throw new Error(response.body);

    // get Download Url recieved in resonse for compress image
    var decodedData = jsonDecode(response.body) as Map;
    var downloadUrl = Uri.parse(decodedData['output']['url']);

    // get compress image from download url
    http.Response compressImageResponse = await http.get(
      downloadUrl,
      headers: header,
    );

    // init Dart SDK
    Client client = Client();
    Storage storage = Storage(client);

    client
        .setEndpoint(Platform.environment['API_ENDPOINT']) // Your API Endpoint
        .setProject(Platform
            .environment['APPWRITE_FUNCTION_PROJECT_ID']) // Your project ID
        .setKey(Platform.environment['API_SECRET']); // Your secret API key

    // Store File Appwrite Storage

    var result = await storage.createFile(
      file: MultipartFile.fromBytes('file', compressImageResponse.bodyBytes,
          filename: DateTime.now().toString()),
    );

    print(result.data);
    exit(0);
  } catch (err) {
    print(err);
    exit(0);
  }
}

void main() {
  String exampleSourceURL = Platform.environment['APPWRITE_FUNCTION_DATA'];
  compressAndStore(exampleSourceURL);
}
