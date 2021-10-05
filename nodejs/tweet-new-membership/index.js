
// Import the twitter-lite wrapper
Twitter = require('twitter-lite');

// Initialize a client
const client = new Twitter({
    consumer_key: process.env.TWITTER_CONSUMER_KEY,
    consumer_secret: process.env.TWITTER_CONSUMER_SECRET,
    access_token_key: process.env.TWITTER_ACCESS_TOKEN_KEY,
    access_token_secret: process.env.TWITTER_ACCESS_TOKEN_SECRET
  });


// Send a tweet 
client.post("statuses/update", {
    status: `A new user has joined!`,
})
.then((response) => {
    console.log(`Tweet sent for new user!`, response)
})
.catch((err) => {
    console.error(err.errors)
});
