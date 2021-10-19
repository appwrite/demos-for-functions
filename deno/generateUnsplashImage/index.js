// Get Unsplash API from env
const UNSPLASH_KEY = Deno.env.get("UNSPLASH_ACCESS_KEY");

// Set API endpoint (per_page=1 to get a single result only)
let API = new URL("https://api.unsplash.com/search/photos?per_page=1");

// Returns object {imageUrl, imageAuthor}
function getUnsplashImage(/** @type {string} */ query) {
  // Append the search params with query
  API.searchParams.append("query", query);

  // Make the request
  return fetch(API.toString(), {
    headers: {
      Authorization: `Client-ID ${UNSPLASH_KEY}`, // Authentication header
    },
  })
    .then((res) => {
      if (res.status != 200) throw Error(res.statusText);
      return res.json();
    })
    .then((jsonBody) => {
      if (jsonBody.total == 0) throw Error("No results found!"); // If no results found

      jsonBody = jsonBody.results[0];

      const imageUrl = jsonBody.urls.raw,
        imageAuthor = jsonBody.user.name;

      return { imageUrl, imageAuthor };
    })
    .catch((err) => {
      throw err;
    });
}

// Run the function
try {
  let keyword = Deno.env.get("APPWRITE_FUNCTION_DATA");
  const result = await getUnsplashImage(keyword);

  console.log(JSON.stringify(result));
} catch (err) {
  console.error("UNSPLASH API call error:", err.message);
}
