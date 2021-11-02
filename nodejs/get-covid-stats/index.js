const { Axios } = require("axios");
const API_ENDPOINT = "https://api.covid19api.com";
const COUNTRY_CODE = process.env.APPWRITE_FUNCTION_DATA || "";

// Parses COVID-19 stats and prints them.
function print_covid_stats(statsobj) {
  const country_name = statsobj["Country"] || "the world";
  const confirmed_cases = statsobj["NewConfirmed"];
  const recovered_today = statsobj["NewRecovered"];
  const deaths_today = statsobj["NewDeaths"];
  msg = `COVID-19 stats for ${country_name}:
     Confirmed cases today: ${confirmed_cases}
     Recovered today: ${recovered_today}
     Deaths today: ${deaths_today}`;
  console.log(msg);
}

// Fetches COVID-19 data. Uses ISO2 country code parameter.
async function get_covid_stats(country_code) {
  try {
    const axios = new Axios({
      baseURL: API_ENDPOINT,
    });
    const response = await axios.get("/summary");
    const global_summary = JSON.parse(response.data);
    if (!country_code) {
      print_covid_stats(global_summary["Global"]);
      return;
    }
    for (let country of global_summary["Countries"]) {
      if (country["CountryCode"] == country_code) {
        print_covid_stats(country);
        return;
      }
    }
    console.error(`ERROR: Couldn't find country code ${country_code}.`);
  } catch (error) {
    console.error(error);
  }
}

get_covid_stats(COUNTRY_CODE);
