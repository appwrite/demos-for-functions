import os
import sys
import json
from translate import Translator


if os.environ.get("APPWRITE_FUNCTION_DATA", None) is None:
    sys.exit("Search query not provided")


query = os.environ["APPWRITE_FUNCTION_DATA"]
try:
    query = json.loads(query)
except Exception as e:
    sys.exit(e)

if query.get('text', None) is None:
    sys.exit("Query not valid! Exiting..")
else:
    in_text = query.get('text')

if query.get('dest', None) is None:
    sys.exit("Query not valid! Exiting..")
else:
    to_lang = query.get('dest')

if query.get('source', None) is None:
    translator = Translator(to_lang=to_lang)
else:
    translator = Translator(from_lang=query.get('source'), to_lang=to_lang)


try:
    translation = translator.translate(in_text)
    print(translation)
except Exceptionas as e:
    print(e)
