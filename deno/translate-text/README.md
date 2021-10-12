# 📧 Your Function Name

A sample Google Translate API Function for translating text.

## ☁️ Make a New Cloud Function

Navigate to 'Functions' and 'Add Function.'
Use 'Deno 1.8' environment.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **GOOGLE_TRANSLATE_API_KEY** - API Key for Google Translate
- **APPWRITE_FUNCTION_DATA** - JSON that contains text, sourceLanguage and targetLanguage

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/translate-text
```

- Ensure that your folder structure looks like this

```
.
├── index.js
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz translate-text
```

- Navigate to Overview Tab of your Cloud Function
- Deploy Tag
- Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in Appwrite and press **Execute**. Enter JSON in the Custom Data textbox, for example:

```bach
{"text":"Labas, Pasauli!","sourceLanguage":"lt","targetLanguage":"en"}
```

Output Logs should contain translated text. In case there were any errors they will be displayed in the Error Logs.
