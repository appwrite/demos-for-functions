const sdk = require('node-appwrite');
const dropbox = require('dropbox');

const client = new sdk.Client();
client.setEndpoint(process.env.APPWRITE_ENDPOINT)
  .setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID)
  .setKey(process.env.APPWRITE_API_KEY);

const storage = new sdk.Storage(client);

const payload = JSON.parse(process.env.APPWRITE_FUNCTION_EVENT_DATA);
const { $id: fileId, name } = payload;

const dbx = new dropbox.Dropbox({ accessToken: process.env.DROPBOX_KEY });

(async () => {
  try {
    const buffer = await storage.getFileDownload(fileId);
    await dbx.filesUpload({path: `/${name}`, contents: buffer });
    console.log('Successfully backed up ' + name + ' to dropbox!');
  } catch (err) {
    console.error('Could not back up file: ', err);
    process.exit(1);
  }
})();
