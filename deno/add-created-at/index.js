import * as appwrite from "https://deno.land/x/appwrite/mod.ts";
import { config as dotEnvConfig } from 'https://deno.land/x/dotenv@v1.0.1/mod.ts';

dotEnvConfig({ export: true });

const APPWRITE_ENDPOINT = Deno.env.get("APPWRITE_ENDPOINT");
const APPWRITE_PROJECT_ID = Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID");
const APPWRITE_API_KEY = Deno.env.get("APPWRITE_API_KEY");
const EVENT_DATA = Deno.env.get('APPWRITE_FUNCTION_EVENT_DATA');

const client = new appwrite.Client();

client
	.setEndpoint(APPWRITE_ENDPOINT)
	.setProject(APPWRITE_PROJECT_ID)
	.setKey(APPWRITE_API_KEY);

const database = new appwrite.Database(client);

const payload = JSON.parse(EVENT_DATA);
const { $id, $collection } = payload;

if (!payload.hasOwnProperty("createdAt")) {
	console.error("Rule for createdAt has not been created in the collection!");
	Deno.exit(0);
}

database
	.updateDocument($collection, $id, {
		createdAt: Math.floor(Date.now() / 1000),
	})
	.then(console.log)
	.catch(console.error);