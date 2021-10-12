using System;
using System.Threading.Tasks;
using SendGrid;
using Newtonsoft.Json;
using dotenv.net;
namespace subscribe_to_sendgrid
{
  class Program
  {

    static string sendGridKey;
    static string sendGridListId;
    static SendGridClient client;

    static async Task addToSendgrid(string emailAddress){
      var data = new RequestBody
      {
        list_ids = new string[1] { sendGridListId }
      ,
        contacts = new Contact[] {
          new Contact {
            email = emailAddress
          },
        }
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

    static async Task Main(string[] args)
    {
      // LOAD ENVIRONMENT VARIABLES FROM .env file if it exists
      DotEnv.Load();

      var envVars = DotEnv.Read();
      sendGridKey = envVars["SENDGRID_KEY"];
      sendGridListId = envVars["SENDGRID_LIST_ID"];

      client = new SendGridClient(sendGridKey);

      await addToSendgrid("ganesh@mail.com");
      
    }
  }
}
