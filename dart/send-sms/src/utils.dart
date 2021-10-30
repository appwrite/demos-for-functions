import 'dart:convert' show base64, utf8;

/// returns base64 encoded Twilio credentials
///
/// used in authorization headers of http requests
String toAuthCredentials(String accountSid, String authToken) =>
    base64.encode(utf8.encode(accountSid + ':' + authToken));
