import 'dart:convert';
import 'dart:io';

void main() async {
  Map<String, String> results = await speedTest();
  print("Download speed: ${results['download']} Mbps");
  print("Upload speed: ${results['upload']} Mbps");
}

//This function will return a map with download and upload speed measured using the SpeedTest CLI.
Future<Map<String, String>> speedTest() async {
  var result = await Process.run('speedtest', ['-f', 'json', '-u', 'Mbps']);
  var json = jsonDecode(result.stdout);
  int dw = json['download']['bandwidth'];
  int up = json['upload']['bandwidth'];
  double download = dw.toDouble() / 125000.0;
  double upload = up.toDouble() / 125000.0;

  return {
    'download': download.toString(),
    'upload': upload.toString(),
  };
}
