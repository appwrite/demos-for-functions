from speedtest import Speedtest

s = Speedtest()
s.download()
s.upload()

results_dict = s.results.dict()

pretty_results_dict = {
    "download_speed": f"{(results_dict['download'] / 1000.0 / 1000.0):.2f} Mbps",
    "upload_speed": f"{(results_dict['upload'] / 1000.0 / 1000.0):.2f} Mbps",
    "ping": f"{results_dict['ping']} ms",
}

print(pretty_results_dict)
