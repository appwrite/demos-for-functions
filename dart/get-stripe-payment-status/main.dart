import 'dart:io';
import 'dart:convert';
import 'package:dio/dio.dart';

void main() async {
Map<String, String> environmentVariables = Platform.environment;


  final String stripeKey = environmentVariables['STRIPE_KEY'];
  final String stripePaymentId = environmentVariables['APPWRITE_FUNCTION_DATA'];

Map<String,dynamic> header = {
     'headers': {
         'Authorization': 'Bearer $stripeKey',
     },
 };

  final String url = 'https://api.stripe.com/v1/payment_intents/$stripePaymentId';

 
Dio().options.headers['Authorization'] = "Bearer " + stripeKey;

  try {
    var response = await Dio().get(url);
    print(response); // respomse
  } catch (e) {
    print('Error retrieving status $e');
  }

}