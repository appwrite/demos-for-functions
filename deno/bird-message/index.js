const MESSAGE_BIRD_API_KEY = Deno.env.get("MESSAGE_BIRD_API_KEY");
const MESSAGE_BIRD_FROM = Deno.env.get("MESSAGE_BIRD_FROM");

const { toNumber, messageBody } = JSON.parse(
  Deno.env.get("APPWRITE_FUNCTION_DATA")
);

const body = new URLSearchParams({
  recipients: [toNumber],
  originator: MESSAGE_BIRD_FROM,
  body: messageBody,
});

fetch("https://rest.messagebird.com/messages", {
  method: "POST",
  headers: {
    Authorization: `AccessKey ${MESSAGE_BIRD_API_KEY}`,
    "Content-Type": "application/x-www-form-urlencoded",
  },
  body,
})
  .then((response) => response.text())
  .then((result) => console.log(result))
  .catch((error) => console.log("error", error));
