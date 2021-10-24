# 📧 Subscribe to mailchimp newsletter using Mailchimp API V3

A sample Dart Cloud Function for subscribe mailchimp newsletter

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_MAILCHIMP_API_KEY** - Your Mailchimp API key
- **APPWRITE_MAILCHIMP_LIST_ID** - Your Mailchimp List ID

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/subscribe-to-mailchimp

# If using Windows use "SET" instead of "export"
$ export PUB_CACHE=.appwrite/
$ dart pub get
```

- Ensure that your folder structure looks like this

```
.
├── main.dart
├── .appwrite
├── pubspec.lock
└── pubspec.yaml
```

- Create a tarfile

```bash
$ cd demos-for-functions/dart/subscribe-to-mailchimp
$ tar -zcvf code.tar.gz .
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "dart main.dart") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## Trigger

Head over to your function in the Appwrite console and just press **Execute Now**.
You must pass an e-mail address String, e.g.:

```
"email@appwrite.io"
```

Example response:

```json
{
  id: "id",
  email_address: "email_address",
  unique_email_id: "unique_email_id",
  contact_id: "contact_id",
  full_name: "",
  web_id: "web_id",
  email_type: "html",
  status: "subscribed",
  consents_to_one_to_one_messaging: true,
  merge_fields: { FNAME: "", LNAME: "", ADDRESS: "", PHONE: "", BIRTHDAY: "" },
  interests: {},
  stats: { avg_open_rate: 0, avg_click_rate: 0 },
  ip_signup: "",
  timestamp_signup: "",
  ip_opt: "ip_opt",
"timestamp_opt":"2021-10-21T01:32:30+00:00",
   "member_rating":2,
   "last_changed":"2021-10-21T01:32:30+00:00",
  language: "",
  vip: false,
  email_client: "",
  location: { latitude: 0, longitude: 0, gmtoff: 0, dstoff: 0, country_code: "", timezone: "" },
  source: "API - Generic",
  tags_count: 0,
  tags: [],
  list_id: "list_id",
  _links: [
    {
      rel: "parent",
      href: "href",
      method: "method",
      targetSchema: "targetSchema",
      schema: "schema"
    },
  ]
}
```
