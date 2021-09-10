// Perform all your imports

const nodemailer = require('nodemailer');
let SMTPsecure;

//Get your env variables
const SMTPconfig = {
	host: process.env.SMTP_HOST,
    port: process.env.SMTP_PORT,
	user: process.env.SMTP_USER,
    password: process.env.SMTP_PASSWORD,
    from: process.env.SMTP_FROM,
};

// Get the name and email of the newly created user from Appwrite's environment variable
const payload = JSON.parse(process.env.APPWRITE_FUNCTION_EVENT_DATA);
const name = payload['name'];
const email = payload['email'];


//Checks if you use SMTP secure
if( SMTPconfig.port == 465){
    SMTPsecure = true;
}else{
    SMTPsecure = false;
}

//Initialize nodemailer
var transporter = nodemailer.createTransport({
host: SMTPconfig.host,
    port: SMTPconfig.port,
    secure: SMTPsecure, 
    auth: {
      user: SMTPconfig.user,
      pass: SMTPconfig.password,
    },})

//Create your email
var mailOptions = {
  from: SMTPconfig.from,
  to: email,
  subject: `Welcome on board ${name}!`,
  text: `Hi ${name}\nGreat to have you with us. ! 😍`
};


//send email
transporter.sendMail(mailOptions, function(error, info){
  if (error) {
    console.log(error);
  } else {
    console.log('Email sent: ' + info.response);
  }
}); 