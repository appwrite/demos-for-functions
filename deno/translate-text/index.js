// Get API key
const apiKey = Deno.env.get("GOOGLE_TRANSLATE_API_KEY");
//  Get the query parameters from Appwrite's environment variable
const payload = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_DATA"));
const { text, sourceLanguage, targetLanguage } = payload;

// Send request
try {
  const response = await fetch(
    `https://translation.googleapis.com/language/translate/v2?q=${text}&target=${targetLanguage}&source=${sourceLanguage}&key=${apiKey}`
  );

  // Handle error
  if (!response.ok) {
    throw new Error(response.statusText);
  }

  // Parse response
  const jsonData = await response.json();
  const result = jsonData.data?.translations?.[0]?.translatedText;

  console.log(result);
} catch (e) {
  console.error(e);
}
