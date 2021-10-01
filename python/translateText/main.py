import six
from google.cloud import translate_v2 as translate


def translateText(text, sourceLanguage, destinationLanguage):
    translateClient = translate.Client()

    if isinstance(text, six.binary_type):
        text = text.decode("utf-8")

    result = translateClient.translate(text, target_language=destinationLanguage, source_language=sourceLanguage)

    return result["translatedText"]
