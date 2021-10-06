# 📧  Your Function Name
A sample Deno Cloud Function that leverages Appwrite Storage API to create backups of all the collection of a database made using Appwrite.

## 📝 Environment Variables
Add the following environment variables in your Cloud Function settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.read`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **COLLECTION_ID** - The id of collection which is needed to be Backed Up.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

1. **Move to the functions directory**

```bash
$ cd demos-for-functions/deno/backup-collections
```

2. **Add the .env file to the folder**
```bash
APPWRITE_ENDPOINT=""
APPWRITE_FUNCTION_PROJECT_ID=""
APPWRITE_API_KEY=""
```

3. **Add the import at top of the file to import environment variables**

```deno
import "https://deno.land/x/dotenv/load.ts";
```

4. **Run the function**
```bash
$ deno run --allow-env --allow-read --allow-write index.js
```

5. **Deploy to appwrite using Appwrite CLI**
```bash
appwrite functions createTag \
    --functionId=<functionId> \
    --command="deno run --allow-env --allow-read --allow-write index.js" \
    --code="<code-directory>"
```

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `storage.files.create` event. 
Or, Just press Excecute
