// Perform all your imports
const axios = require('axios').default;

(async () => {
    // Return and log error if environment variables are not set
    if (!process.env.GIPHY_API_KEY) return console.error("GIPHY_API_KEY environment variable not set")
    if (!process.env.APPWRITE_FUNCTION_DATA) return console.error("Please pass in a search query") 
    try {
        const res = await axios.get(`https://api.giphy.com/v1/gifs/search?api_key=${process.env.GIPHY_API_KEY}&q=${process.env.APPWRITE_FUNCTION_DATA}&limit=1`) // Make the request to the giphy API
        console.log(res.data.data[0].url)   // Log the gif url
    } catch (e) {
        console.error(e)    // Log an error if one occured
    }
})();

