using System;
using System.Threading.Tasks;
using SendGrid;
using Newtonsoft.Json;
using dotenv.net;
namespace subscribe_to_sendgrid
{
  class Program
  {
    static async Task Main(string[] args)
    {
      // LOAD ENVIRONMENT VARIABLES FROM .env file if it exists
      DotEnv.Load();

      var envVars = DotEnv.Read();
      var sendGridKey = envVars["SENDGRID_KEY"];
      var sendGridListId = envVars["SENDGRID_LIST_ID"];

      var client = new SendGridClient(sendGridKey);
      var data = new RequestBody
      {
        list_ids = new string[1] { sendGridListId }
      ,
        contacts = new Contact[] {new Contact {
            first_name = DateTime.Now.ToString(),
            email = "ganesht049@gmail.com"
        }}
      };

      var jsonString = JsonConvert.SerializeObject(data);

      Console.WriteLine(jsonString);

      var response = await client.RequestAsync(
          method: SendGridClient.Method.PUT,
          urlPath: "marketing/contacts",
          requestBody: jsonString
      );

      Console.WriteLine(response.StatusCode);
    }
  }
}
