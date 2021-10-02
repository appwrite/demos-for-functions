import 'package:googleapis/translate/v3.dart';
import 'package:http/http.dart';

Future<String> translateText(
    String text, String sourceLanguage, String destinationLanguage) async {
  Client client = Client();
  TranslateApi translateApi = TranslateApi(client);

  /// [request] - The metadata request object.

  final request = TranslateTextRequest(
    contents: [text],
    sourceLanguageCode: sourceLanguage,
    targetLanguageCode: destinationLanguage,
  );

  /// [parent] - Project or location to make a call. Must refer to a
  /// caller's project.
  /// `Replace {project-number-or-id} this with your project number or Id`

  final parent = "projects/{project-number-or-id}";

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
