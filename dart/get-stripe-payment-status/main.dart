import 'dart:io';
import 'dart:convert';
import 'package:http/http.dart' as http;

void main() async {
Map<String, String> environmentVariables = Platform.environment;


  final String stripeKey = environmentVariables['STRIPE_KEY'];
  final String stripePaymentId = environmentVariables['APPWRITE_FUNCTION_DATA'];

Map<String,String> authHeader = {
         'Authorization': 'Bearer $stripeKey',
 };

  var url = Uri.parse('https://api.stripe.com/v1/payment_intents/$stripePaymentId');

  try {
    var response = await http.get(url,headers: authHeader);
    var data = jsonDecode(response.body);
    print(data['status']); // respomse
  } catch (e) {
    print('Error retrieving status $e');
  }

}