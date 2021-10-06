import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetCovidStats {

    public static String fetchEnvironmentVariable(String variable){
        return System.getenv(variable);
    }

    public static String makeApiCall(String uri) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        String response = "";
        if(con.getResponseCode() == 200){
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            response = content.toString();
            in.close();
        }
        con.disconnect();
        return response;
    }

    public static JSONObject stringToJson(String string) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(string);
        return json;
    }

    public static void printStats(JSONObject countryObj){
        String statusMessage = "";
        statusMessage += "Region: " + ((countryObj.get("Country") != null) ? countryObj.get("Country") : "Global");
        statusMessage += "\nNew Confirmed: " + countryObj.get("NewConfirmed");
        statusMessage += "\nNew Deaths: " + countryObj.get("NewDeaths");
        statusMessage += "\nNew Recovered: " + countryObj.get("NewRecovered");
        System.out.println(statusMessage);
    }

    public static void main(String[] args) {
        String uri = "https://api.covid19api.com";
        String COUNTRY_CODE = fetchEnvironmentVariable("COUNTRY_CODE");

        if (COUNTRY_CODE == null){
            COUNTRY_CODE = "";
        }

        try {
            String response =  makeApiCall(uri + "/summary");
            JSONObject json = stringToJson(response);

            if (COUNTRY_CODE == ""){
                //global data
                Object globalStatObject = json.get("Global");
                printStats((JSONObject) globalStatObject);
            } else {
                //country data
                ArrayList<JSONObject> countryObjectList = (ArrayList<JSONObject>) json.get("Countries");
                for (int i = 0; i < countryObjectList.size(); i++) {
                    if (countryObjectList.get(i).get("CountryCode").equals(COUNTRY_CODE)){
                        printStats(countryObjectList.get(i));
                        break;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
