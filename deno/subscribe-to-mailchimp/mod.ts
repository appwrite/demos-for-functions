// Assert environment variables
if (Deno.env.get("APPWRITE_MAILCHIMP_API_KEY") === undefined)
  throw new Error("APPWRITE_MAILCHIMP_API_KEY is not defined");
if (Deno.env.get("APPWRITE_MAILCHIMP_LIST_ID") === undefined)
  throw new Error("APPWRITE_MAILCHIMP_LIST_ID is not defined");

// Parameters
const [apiKey, apiLocation] = Deno.env.get("APPWRITE_MAILCHIMP_API_KEY")!.split("-");
const listId: string = Deno.env.get("APPWRITE_MAILCHIMP_LIST_ID")!;
const { email } = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_DATA")!);

// Constants
const defaultStatus = "subscribed";
const url = `https://${apiLocation}.api.mailchimp.com/3.0/lists/${listId}/members`;

const body = {
  email_address: email,
  status: defaultStatus
};

fetch(
  url,
  {
    method: "POST",
    headers: {
      "Authorization": `apikey ${apiKey}`,
      "Content-Type": "application/json"
    },
    body: JSON.stringify(body)
  }
).then(res => res.json())
.then(console.log)
.catch(console.error);