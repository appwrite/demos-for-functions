import 'dart:convert';
import 'dart:io';
import 'package:googleapis/translate/v3.dart';
import 'package:googleapis/storage/v1.dart';
import 'package:googleapis_auth/auth_io.dart';

Future<String> translateText(
  String? text,
  String? sourceLanguage,
  String? destinationLanguage,
) async {
  Map<String, String> envVars = Platform.environment;

  final client = await clientViaApplicationDefaultCredentials(scopes: [
    StorageApi.devstorageReadOnlyScope,
  ]);

  TranslateApi translateApi = TranslateApi(client);

  /// [request] - The metadata request object.

  final request = TranslateTextRequest(
    contents: [text ?? ""],
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
  Map<String, String> envVars = Platform.environment;

  // Get text ,source language and destination language from Appwrite's environment variable
  final payload = jsonDecode(envVars['APPWRITE_FUNCTION_EVENT_DATA']!);

  final text = payload["text"];
  final sourceLanguage = payload["sourceLanguage"];
  final destinationLanguage = payload["destinationLanguage"];

  // Validation to check if all parameters were given
  if (text == null) {
    print("[text] to be translated not provided.");
  } else if (sourceLanguage == null) {
    print("[sourceLanguage] not provided.");
  } else if (destinationLanguage == null) {
    print("[destinationLanguage] not provided.");
  } else {
    var result = await translateText(text, sourceLanguage, destinationLanguage);
    print('Translated text: $result');
  }
}
