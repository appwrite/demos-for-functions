import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;

main(List<String> args) async {
  Map<String, String> envVars = Platform.environment;
  final mailgun = MailgunMailer(
    apiKey: envVars['MAILGUN_API_KEY'],
    domain: envVars['MAILGUN_DOMAIN'],
  );

  // Get the name and email of the newly created user from Appwrite's environment variable
  final payload = jsonDecode(envVars['APPWRITE_FUNCTION_EVENT_PAYLOAD']);
  final name = payload['name'];
  final email = payload['email'];

// Create your email
  final sent = await mailgun.send(
      from: 'Welcome to My Awesome App <welcome@my-awesome-app.io>',
      to: email,
      subject: 'Welcome on board ${name}!',
      text: 'Hi ${name}\nGreat to have you with us. ! 😍');
  if (sent) {
    print("email sent successfully.");
  } else {
    print("failed to send message");
  }
}

class MailgunMailer {
  final String domain;
  final String apiKey;

  MailgunMailer({this.domain, this.apiKey});

  Future<bool> send(
      {String from,
      List<String> to = const [],
      List<String> cc = const [],
      List<String> bcc = const [],
      List<dynamic> attachments = const [],
      String subject,
      String html,
      String text,
      String template,
      Map<String, dynamic> options}) async {
    var client = http.Client();
    try {
      var request = http.MultipartRequest(
          'POST',
          Uri(
              userInfo: 'api:$apiKey',
              scheme: 'https',
              host: 'api.mailgun.net',
              path: '/v3/$domain/messages'));
      if (subject != null) {
        request.fields['subject'] = subject;
      }
      if (html != null) {
        request.fields['html'] = html;
      }
      if (text != null) {
        request.fields['text'] = text;
      }
      if (from != null) {
        request.fields['from'] = from;
      }
      if (to.length > 0) {
        request.fields['to'] = to.join(", ");
      }
      if (cc.length > 0) {
        request.fields['cc'] = cc.join(", ");
      }
      if (bcc.length > 0) {
        request.fields['bcc'] = bcc.join(", ");
      }
      if (template != null) {
        request.fields['template'] = template;
      }
      if (options != null) {
        if (options.containsKey('template_variables')) {
          request.fields['h:X-Mailgun-Variables'] =
              jsonEncode(options['template_variables']);
        }
      }
      if (attachments.length > 0) {
        request.headers["Content-Type"] = "multipart/form-data";
        for (var i = 0; i < attachments.length; i++) {
          var attachment = attachments[i];
          if (attachment is File) {
            request.files.add(await http.MultipartFile.fromPath(
                'attachment', attachment.path));
          }
        }
      }
      var response = await client.send(request);
      if (response.statusCode != HttpStatus.ok) {
        return false;
      }

      return true;
    } catch (e) {
      return false;
    } finally {
      client.close();
    }
  }
}
