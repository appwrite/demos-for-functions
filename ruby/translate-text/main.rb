# Import the helper libraries
require "free_google_translate"
require "json"

# Get the text, sourceLanguage and destinationLanguage from Appwrite's environment variable
payload = JSON.parse(ENV["APPWRITE_FUNCTION_DATA"])
text=payload["text"]
sourceLanguage=payload["sourceLanguage"]
destinationLanguage=payload["destinationLanguage"]

# Translate your text
translation = GoogleTranslate.translate from: sourceLanguage, to: destinationLanguage, text: text
puts translation
