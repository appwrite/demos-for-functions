import 'dart:io';
import 'package:googleapis/translate/v3.dart';
import 'package:googleapis/storage/v1.dart';
import 'package:googleapis_auth/auth_io.dart';

Future<String> translateText(
    String text, String sourceLanguage, String destinationLanguage) async {

  Map<String, String> envVars = Platform.environment;
  
  final client = await clientViaApplicationDefaultCredentials(scopes: [
    StorageApi.devstorageReadOnlyScope,
  ]);
  
  
  TranslateApi translateApi = TranslateApi(client);

  /// [request] - The metadata request object.

  final request = TranslateTextRequest(
    contents: [text],
    sourceLanguageCode: sourceLanguage,
    targetLanguageCode: destinationLanguage,
  );

  /// [parent] - Project or location to make a call. Must refer to a
  /// caller's project.
  /// `PROJECT_ID` is manually set
  final parent = "projects/${envVars['PROJECT_ID']}";

  final TranslateTextResponse result =
      await translateApi.projects.translateText(request, parent);

  return result.translations?[0].translatedText ?? "";
}

void main(List<String> args) async {
  final text = 'Hello world';
  final sourceLanguage = 'en-US';
  final destinationLanguage = 'sr-Latn';

  var result = await translateText(text, sourceLanguage, destinationLanguage);
  print(result);
}
