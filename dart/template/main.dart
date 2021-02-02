import 'package:dart_appwrite/dart_appwrite.dart';

void main(List<String> args) {
  // Initialise the client SDK
  final Client client = Client();
  client
          .setEndpoint('https://[HOSTNAME_OR_IP]/v1') // Your API Endpoint
          .setProject('5df5acd0d48c2') // Your project ID
          .setKey('919c2d18fb5d4...a2ae413da83346ad2') // Your secret API key
      ;

  // Initialise the storage SDK
  final storage = new Storage(client);
  final future = storage.listFiles();

  future.then((response) {
    print(response);
  }, onError: (error) {
    print(error);
  });
}
