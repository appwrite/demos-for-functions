# 📧  Save Contact to SendGrid List
<!--  A brief descripption about your Cloud Function  -->

## 📝 Environment Variables
<!-- Tell the users of your Cloud function, what Environment Variables your function uses. Use the following format -->

* **SENDGRID_KEY** - API key for sendgrid APIs <!-- Short Description --> 
* **SENDGRID_LIST_ID** - List id in which you want to add the emails <!-- Short Description -->

## 🚀 Building and Packaging
<!-- 
Highlight the steps required to build and deploy this cloud function. 

Take a look at this example (https://github.com/appwrite/demos-for-functions/blob/master/dotnet/welcome-email/README.md) for more information.  

Make sure you mention the instructions clearly and also mention the entrypoint command for the function 
-->

This is a sample dotnet console application so building can be done via 

```
dotnet run
```

and you can specify certain build configurations like linux, windows

E.g.

```
dotnet subscribe-to-sendgrid.dll
```

While executing you can pass in a string containing multiple email addresses and
the function will split email address and add email addresses to list one by one
writing result of every transaction in the console.

## 🎯 Trigger
<!-- Clearly explain the triggers that this cloud function relies on to work correctly. Take a look at the below example: 
Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
 --> 

You can trigger this function as and when you wish to. Mostly it can be done when
someone fills in a contact form and wishes to be in connect further along with
the company giving out that form.