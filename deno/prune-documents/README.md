# 📧  Prune documents
This function scans all documents in all collections to read their `createdAt` attribute. If a document has this attribute and it's older than 5 years, it removes the document. Function outputs is string contains deleted document id and collection id.

## 📝 Environment Variables

- **APPWRITE_ENDPOINT** - Your Appwrite Project Endpoint ( can be found at `settings` tab on your Appwrite console)
- **APPWRITE_API_KEY** - Your Appwrite Project API Keys ( can be found at `API Keys` tab on your Appwrite console, it needs `collections.read`, `documents.read` and `documents.write` permissions)
- **APPWRITE_FUNCTION_PROJECT_ID** - Your Appwrite Project Id ( can be found at `settings` tab on your Appwrite console)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/prune-documents
```

* Ensure that your folder structure looks like this 
```
.
├── mod.ts
├── README.md
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz prune-documents
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-env --allow-read --allow-net mod.ts") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 💽 Database

To make this function run in your database, you need to have a collection contains `createdAt` rule with numeric type. And the need to filled with date in milliseconds unix timestamp format.

## Usage
* Manually:
  * Go to the Function **Overview** Tab
  * Click **Execute Now**
* Add function execution to schedule:
  * Go to the Function **Settings** Tab
  * Fill CRON schedule, for example `0 6 * * *` — will execute function every day at 6am
