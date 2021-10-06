// Perform all your imports
const sdk = require('node-appwrite');
const fs = require('fs');

const { generateCsv } = require('./utils');

// Initialise the client SDK
let client = new sdk.Client();

// Initialise the storage and database SDK
let storage = new sdk.Storage(client);
let database = new sdk.Database(client);

client
	.setEndpoint(process.env.APPWRITE_ENDPOINT) // Your API Endpoint
	.setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID) // Your project ID available by default
	.setKey(process.env.APPWRITE_API_KEY); // Your secret API key

//retrieve all collections
//for each collection, retrieve all documents and post to storage api

const documents = [];

(async () => {
	let retrievedCollections = await database.listCollections();
	for (let collection of retrievedCollections.collections) {
		let retrievedDocuments = await database.listDocuments(collection.$id);
		for (let document of retrievedDocuments.documents) {
			documents.push(document);
		}
	}
	const generatedCsv = generateCsv(documents);
	fs.writeFile('data.csv', generatedCsv, function (err) {
		if (err) console.log(err);
	});
	await storage.createFile(fs.createReadStream(__dirname + '/data.csv'));
})();
