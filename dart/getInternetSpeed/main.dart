import 'dart:convert';
import 'dart:io';

// void main() {
//   final internetSpeedTest = InternetSpeedTest();
//   double downloadRate = 0;
//   double uploadRate = 0;
//   String downloadProgress = '0';
//   String uploadProgress = '0';

//   String unitText = 'Mb/s';
//   internetSpeedTest.startDownloadTesting(
//       onDone: (double transferRate, SpeedUnit unit) {
//     print('Download speed: $transferRate $unit');
//   }, onError: (String errorMessage, String speedTestError) {
//     print('the errorMessage $errorMessage, the speedTestError $speedTestError');
//   }, onProgress: (double progress, double transferRate, SpeedUnit unit) {
//     print('Download progress: $progress');
//     downloadRate = transferRate;
//     downloadProgress = progress.toString();
//   });

//   internetSpeedTest.startUploadTesting(
//       onDone: (double transferRate, SpeedUnit unit) {
//     print('Upload speed: $transferRate $unit');
//   }, onError: (String errorMessage, String speedTestError) {
//     print('the errorMessage $errorMessage, the speedTestError $speedTestError');
//   }, onProgress: (double progress, double transferRate, SpeedUnit unit) {
//     print('Upload progress: $progress');
//     uploadRate = transferRate;
//     uploadProgress = progress.toString();
//   });
// }

void main() async {
  Map<String, String> results = await speedTest();
  print("Download speed: ${results['download']} Mbps");
  print("Upload speed: ${results['upload']} Mbps");
}

//This function will return a map with download and upload speed measured using the SpeedTest CLI.
Future<Map<String, String>> speedTest() async {
  var result = await Process.run('speedtest', ['-f', 'json', '-u', 'Mbps']);
  var json = jsonDecode(result.stdout);
  print(json);
  int dw = json['download']['bandwidth'];
  int up = json['upload']['bandwidth'];
  print(dw);
  print(up);
  double download = dw.toDouble() / 125000.0;
  double upload = up.toDouble() / 125000.0;

  return {
    'download': download.toString(),
    'upload': upload.toString(),
  };
}
