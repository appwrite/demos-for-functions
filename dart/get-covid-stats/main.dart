import "dart:convert";
import "dart:io";

import "package:http/http.dart" as http;

void main(List<String> args) {
  Map<String, String> envVars = Platform.environment;

  const String apiEndpoint = "https://api.covid19api.com";
  final String? countryCode = envVars["APPWRITE_FUNCTION_DATA"];
  get_covid_stats(countryCode: countryCode, apiEndpoint: apiEndpoint);
}

// Fetches COVID-19 data. Uses ISO2 country code parameter.
void get_covid_stats(
    {required String? countryCode, required String apiEndpoint}) async {
  final Uri url = Uri.https(apiEndpoint, "/summary");
  final response = await http.get(url);
  if (response.statusCode == 200) {
    final Map summary = jsonDecode(response.body);
    if (countryCode == null) {
      print_covid_stats(summary["Global"]);
    } else {
      final List countries = summary["Countries"];

      final countryIndex = countries
          .indexWhere((country) => country["CountryCode"] == countryCode);

      if (countryIndex != -1) {
        print_covid_stats(countries[countryIndex]);
      } else {
        throw ("ERROR: Couldn't find any data for '$countryCode'");
      }
    }
  } else {
    throw ("ERROR: Failed to connect to service");
  }
}

// Parses COVID-19 stats.
void print_covid_stats(Map stats) {
  final countryName =
      stats.containsKey("Country") ? stats["Country"] : "the world";
  final confirmedCases = stats["NewConfirmed"];
  final recoveredToday = stats["NewRecovered"];
  final deathsToday = stats["NewDeaths"];
  print("""
    COVID-19 stats for $countryName:
    Confirmed cases today: $confirmedCases
    Recovered today: $recoveredToday
    Deaths today: $deathsToday
  """);
}
