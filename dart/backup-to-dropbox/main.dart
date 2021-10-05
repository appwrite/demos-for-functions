import 'dart:convert';
import 'dart:io';

import 'package:dart_appwrite/dart_appwrite.dart';
import 'package:http/http.dart' as http;

import 'range.dart';

/*
test 
/// TODO: paste your dropbox oauth2 token here
final String token =
    'sl.A5o7QJ1B-ltQGVXRknejPbuzrr0lE_A0st1VS-qYM2QESL0JsDwi1bDVyNtqnVchaj7n4B1TkYt6a83c-Qo7lKofIUkSNe6opk9Gt-1iIAOsnOccROrssD5eXTvzn7M6QQJZ7-Te';

/// your appwrite api key
// with scopes
// files.read , document.read, storage.read, storage.write
final String apiKey =
    'e89ada78fdae258d4b6543655872e39acc04f8eb9d3e5194b348ce59490749dbf9039a42a8ad4799d789b2d4b435069457cb58ead259277025e73f09f71985072ac4fac6c22a685d0e2f73eea1f90118c6d4ddc48372dc9e2e5a2956710e8d0854beabed943e4e2ed19e3d23981d506574702ba2983865621838086f6191866a';

/// your appwrite endpoint
final String endpoint = 'http://localhost:4700/v1';

/// appwrite project id
final String projectId = '61560a8305493';

*/
/// TODO: paste your dropbox oauth2 token here
final String token =
    'sl.A5o7QJ1B-ltQGVXRknejPbuzrr0lE_A0st1VS-qYM2QESL0JsDwi1bDVyNtqnVchaj7n4B1TkYt6a83c-Qo7lKofIUkSNe6opk9Gt-1iIAOsnOccROrssD5eXTvzn7M6QQJZ7-Te';

/// your appwrite api key
// with scopes
// files.read , document.read, storage.read, storage.write
final String apiKey =
    'e89ada78fdae258d4b6543655872e39acc04f8eb9d3e5194b348ce59490749dbf9039a42a8ad4799d789b2d4b435069457cb58ead259277025e73f09f71985072ac4fac6c22a685d0e2f73eea1f90118c6d4ddc48372dc9e2e5a2956710e8d0854beabed943e4e2ed19e3d23981d506574702ba2983865621838086f6191866a';

/// your appwrite endpoint
final String endpoint = 'http://localhost:4700/v1';

/// appwrite project id
final String projectId = '61560a8305493';

void main(List<String> arguments) async {
  Map<String, String> envVars = Platform.environment;

  print('-- starting --');

  final Client client = Client();
  // client
  //     .setEndpoint(envVars['APPWRITE_ENDPOINT']!) // This is manually set
  //     .setProject(envVars[
  //         'APPWRITE_FUNCTION_PROJECT_ID']) // this is available by default
  //     .setKey(envVars['APPWRITE_API_KEY']);
  client.setEndpoint(endpoint).setProject(projectId).setKey(apiKey);

  // final String dropBoxToken = envVars['DROPBOX_TOKEN']!;

  final Database database = Database(client);

  // number of documents to fetch as limit per request
  final int _limit = 100;

  final result = await database.listCollections(limit: _limit);

  final int totalCollections = result.data['sum'];

  int _iterations = totalCollections ~/ _limit;

  if (_iterations > 0) {
    //
    for (var item in range(0, _iterations)) {}
  }

  var _serverCollections = result.data['collections'] as List;

  final List<Collection> collections = _serverCollections
      .map((collec) => Collection(id: collec['\$id'], name: collec['name']))
      .toList();

  print(collections);

  // create service class instance
  print('-- done --');
}

/// model class to hold collection id and collection name
class Collection {
  final String id;
  final String name;

  Collection({
    required this.id,
    required this.name,
  });

  @override
  String toString() => 'Collection(id: $id, name: $name)';
}

// helper service class
class DropBox {
  static final Uri _url =
      Uri.parse("https://content.dropboxapi.com/2/files/upload");

  /// determine mode of file upload, best to set to `overwrite` to avoid conflicts in case of same file name
  ///
  /// trying to be uploaded on dropbox
  ///
  /// valid options are >>> add, overwrite, update
  final String writeMode;

  /// rename file strategy, if same file exist either rename or leave as is, default to `true`
  final bool autorename;

  /// the path on your dropbox cloud server storage to store the file
  ///
  ///  Path in the user's Dropbox to save the file.
  ///
  /// String(pattern="(/(.|[\r\n])*)|(ns:[0-9]+(/.*)?)|(id:.*)")
  final String path;

  // dropbox oauth2 token
  late String _token;

  // headers
  late Map<String, String> _headers;

  DropBox({
    this.writeMode = 'overwrite',
    this.autorename = true,
    required this.path,
    required String dropBoxToken,
  }) {
    // add token
    _token = dropBoxToken;

    // create api args
    final _apiArgs = {
      "path": path,
      "mode": {
        ".tag": writeMode,
      },
      "autorename": autorename,
    };

    // create request headers
    _headers = {
      'Authorization': 'Bearer $_token',
      "Content-Type": "application/octet-stream",
      "Dropbox-API-Arg": jsonEncode(_apiArgs),
    };
  }

  /// upload a file to your dropbox cloud storage
  Future<String?> upload(File fileToUpload) async {
    final _client = http.Client();

    var filePath = fileToUpload.path;

    try {
      final bytes = await fileToUpload.readAsBytes();

      final response = await _client.post(_url, body: bytes, headers: _headers);

      if (response.statusCode == 200) {
        final res = response.body;

        // success
        return '[INFO] File: $filePath successfully uploaded on your dropbox account';
      }

      // something went wrong
      else {
        print('[ERROR] Failed to upload: $filePath to dropbox!');

        final err = response.body;

        return err.toString();
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
