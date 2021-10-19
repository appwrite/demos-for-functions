import * as sdk from "https://deno.land/x/appwrite@0.4.1/mod.ts";
import { config } from "https://deno.land/x/dotenv@v3.0.0/mod.ts";

// Export dotenv file to Deno.env
config({ export: true });

const client = new sdk.Client();

client
  .setEndpoint(Deno.env.get("APPWRITE_API_ENDPOINT")) // Your API Endpoint
  .setProject(Deno.env.get("APPWRITE_PROJECT_ID")) // Your project ID
  .setKey(Deno.env.get("APPWRITE_API_KEY")); // Your secret API key

const { email } = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_DATA"));

const contactInfo = {
  // deno-lint-ignore camelcase
  list_ids: [Deno.env.get("SENDGRID_LIST_ID")],
  contacts: [
    {
      email,
    },
  ],
};

const response = await fetch("https://api.sendgrid.com/v3/marketing/contacts", {
  method: "PUT",
  headers: {
    Authorization: "Bearer " + Deno.env.get("SENDGRID_API_KEY"),
    "Content-Type": "application/json",
  },
  body: JSON.stringify(contactInfo),
});

console.log(JSON.parse(await response.text())["job_id"]);
