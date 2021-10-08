const accountSid = Deno.env.get("TWILIO_ACCOUNT_SID");
const authToken = Deno.env.get("TWILIO_AUTH_TOKEN");
const fromNumber = Deno.env.get("TWILIO_FROM_NUMBER");

const { toNumber, messageBody } = JSON.parse(
  Deno.env.get("APPWRITE_FUNCTION_DATA")
);

const sendWhatsappMessage = async (
  messageBody,
  accountSid,
  authToken,
  fromNumber,
  toNumber
) => {
  if (!accountSid || !authToken) {
    console.log(
      "Your Twilio account credentials are missing. Please add them."
    );
    return;
  }

  const url = `https://api.twilio.com/2010-04-01/Accounts/${accountSid}/Messages.json`;

  const encodedCredentials = base64.fromUint8Array(
    new TextEncoder().encode(`${accountSid}:${authToken}`)
  );
  const body = new URLSearchParams({
    To: `whatsapp:${toNumber}`,
    From: `whatsapp:${fromNumber}`,
    Body: messageBody,
  });

  const response = await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
      Authorization: `Basic ${encodedCredentials}`,
    },
    body,
  });
  return response.json();
};

const response = await sendWhatsappMessage(
  accountSid,
  authToken,
  fromNumber,
  toNumber
);
