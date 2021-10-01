const accountSID = Deno.env.get("TWILIO_ACCOUNT_SID");
const authToken = Deno.env.get("TWILIO_AUTH_TOKEN");
const sender = Deno.env.get("TWILIO_SENDER");

const {
    to,
    message,
} = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_DATA"));

fetch(`https://api.twilio.com/2010-04-01/Accounts/${accountSID}/Messages.json`, {
    method: "POST",
    headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        "Authorization": `Basic ${btoa(`${accountSID}:${authToken}`)}`,
    },
    body: new URLSearchParams({
        To: to,
        From: sender,
        Body: message,
    }).toString(),
}).then((res) => res.json()).then((data) => {
    console.log(data);
}).catch(console.error);
