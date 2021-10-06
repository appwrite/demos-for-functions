import * as sdk from "https://deno.land/x/appwrite/mod.ts";

// Init SDK
let client = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID")) // Your project ID available by default
  .setKey(Deno.env.get("APPWRITE_API_KEY")) // Your secret API key
;

// Environment variables
const API_ENDPOINT = "https://api.covid19api.com";
const COUNTRY_CODE = Deno.env.get("APPWRITE_FUNCTION_DATA") || "";

// Parses COVID-19 stats and prints them.
function printCovidStats(stats) {
  const country_name = stats["Country"] || "the world";
  const confirmed_cases = stats["NewConfirmed"];
  const recovered_today = stats["NewRecovered"];
  const deaths_today = stats["NewDeaths"];
  const msg = `COVID-19 stats for ${country_name}:
       Confirmed cases today: ${confirmed_cases}
       Recovered today: ${recovered_today}
       Deaths today: ${deaths_today}`;
  console.log(msg);
}

// Fetchs COVID-19 data, Uses IS02 country code parameter.
async function getCovidStats() {
  const jsonResponse = await fetch(`${API_ENDPOINT}/summary`);
  const data = await jsonResponse.json();
  if (!COUNTRY_CODE) {
    printCovidStats(data.Global);
    return;
  }

  const countryData = data.Countries.filter((country) =>
    country.CountryCode === COUNTRY_CODE
  );

  printCovidStats(countryData[0]);
}

getCovidStats();