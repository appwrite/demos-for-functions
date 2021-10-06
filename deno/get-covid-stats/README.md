# 📧  Your Function Name
A sample Deno Cloud Function that leverages COVID-19 API to print the details about the COVID stats of the input country or global using Appwrite.

## 📝 Environment Variables
Add the following environment variables in your Cloud Function settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.read`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_FUNCTION_PROJECT_ID** - Your Appwrite Function ID

Optional Environment Variables:
* **APPWRITE_FUNCTION_DATA** - Country code in ISO2 format, For example `US`, `HN` etc.

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
APPWRITE_FUNCTION_DATA="" ( optional )
```

3. **Add the import at top of the file to import environment variables**

```deno
import "https://deno.land/x/dotenv/load.ts";
```

4. **Run the function**
```bash
$ deno run --allow-env --allow-read --allow-net index.js
```

5. **Deploy to appwrite using Appwrite CLI**
```bash
appwrite functions createTag \
    --functionId=<functionId> \
    --command="deno run --allow-env --allow-net --allow-read index.js" \
    --code="<code-directory>"
```

## 🎯 Trigger

Head over to your function in the Appwrite console , Just press Excecute.