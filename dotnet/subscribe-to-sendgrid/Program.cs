using System;
using System.Collections.Generic;
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

      // create the request body that is to be sent
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

      // convert the object to string
      var jsonString = JsonConvert.SerializeObject(data);

      // make the request to client with appropriate method 
      // and body content
      var response = await client.RequestAsync(
          method: SendGridClient.Method.PUT,
          urlPath: "marketing/contacts",
          requestBody: jsonString
      );

      Console.WriteLine(response.StatusCode);
    }

    static async Task Main(string[] args)
    {
      // load ENVIRONMENT VARIABLES from `.env` file if it exists
      DotEnv.Load();

      // load sendgrid key and id of the list that is to be updated
      sendGridKey = Environment.GetEnvironmentVariable("SENDGRID_KEY");
      sendGridListId = Environment.GetEnvironmentVariable("SENDGRID_LIST_ID");

      // create a sendgrid api client
      client = new SendGridClient(sendGridKey);

      // call the function to add it to sendgrid for every arg passed
      foreach(var arg in args){
        await addToSendgrid(arg);
      }
    }
  }
}
