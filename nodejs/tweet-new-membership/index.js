
// Import the twitter-lite wrapper
Twitter = require('twitter-lite');

// Initialize a client
const client = new Twitter({
    consumer_key: process.env.TWITTER_CONSUMER_KEY,
    consumer_secret: process.env.TWITTER_CONSUMER_SECRET,
    access_token_key: process.env.TWITTER_ACCESS_TOKEN_KEY,
    access_token_secret: process.env.TWITTER_ACCESS_TOKEN_SECRET
  });


// Get the name new member from Appwrite's environment variable
const payload = JSON.parse(process.env.APPWRITE_FUNCTION_EVENT_DATA);
const name = payload['name'];


// Send a tweet 
client.post("statuses/update", {
    status: `Welcome ${name}!`,
})
.then((response) => {
    console.log(`Tweet sent for user ${name}!`, response)
})
.catch((err) => {
    console.log(err.errors)
});
