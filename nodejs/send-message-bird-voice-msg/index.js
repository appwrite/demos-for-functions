// Perform all your imports
const fetch = (...args) => import('node-fetch').then(({default: fetch}) => fetch(...args));

(async () => {
  if (process.env.MESSAGE_BIRD_ACCESS_KEY === undefined) return console.error("Messagebird accesskey not set.")

  // Get message body and recipient from from appwrite data
  const payload = JSON.parse(process.env.APPWRITE_FUNCTION_DATA);

  const phoneNumber = payload['phoneNumber'];
  const text = payload['text'];

  try {
    const response = await fetch("https://rest.messagebird.com/voicemessages", {
      method: 'post',
      body: JSON.stringify({ body: text, recipients: [ phoneNumber ] }), 
      headers: {
        "Content-Type": "application/json",
        "Authorization": `AccessKey ${process.env.MESSAGE_BIRD_ACCESS_KEY}`}
    })

    const data = await response.json();

    console.log(data.recipients.items[0].status)
  } catch (error) {
    console.error(error)
  }
})()
