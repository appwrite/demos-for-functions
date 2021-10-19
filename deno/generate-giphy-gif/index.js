const API_KEY = Deno.env.get('GIPHY_API_KEY');

const DEFAULT_REQUEST_ERROR = 'An error has occurred.';
const REQUEST_ERRORS = {
	403: 'Forbidden. Please specify a valid API key.',
	500: 'Server internal error. Please try again later.'
};

async function execFunction() {

	const eventData = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_EVENT_DATA"));
	const { keyword } = eventData;

	if (!keyword) {
		console.error('keyword parameter is missing in the function data.')
		return;
	}
	return generateGiphyGif(keyword);
}

async function generateGiphyGif(searchKeyword) {
	try {
		const url = `https://api.giphy.com/v1/gifs/search?api_key=${API_KEY}&q=${searchKeyword}&limit=1&offset=0&rating=g&lang=en`;
		const response = await fetch(url);
		const responseBody = await response.json();

		if (response.status !== 200) {
			return console.error(`${REQUEST_ERRORS[response.status] || DEFAULT_REQUEST_ERROR}`);
		}
		const { url: fetchedGifUrl } = responseBody.data[0];
		console.log('url: ', fetchedGifUrl);
		return {
			url: fetchedGifUrl
		};
	} catch (err) {
		console.error(DEFAULT_REQUEST_ERROR, err);
		return {
			error: err
		};
	}
}

execFunction();
