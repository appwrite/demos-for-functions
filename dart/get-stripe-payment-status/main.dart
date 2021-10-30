import 'dart:io';
import 'dart:convert';

void main() async {
Map<String, String> environmentVariables = Platform.environment;
  final payload = jsonDecode(environmentVariables['APPWRITE_FUNCTION_DATA']);


  final String stripePaymentId = payload['stripePaymentId'];

 


  try{

  }catch(error){
    print('Error fetching status $error');
  }

}