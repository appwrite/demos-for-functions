from speedtest import Speedtest

def getInternetSpeed():
    
    s = Speedtest()
    dl = s.download()
    ul = s.upload()
    ## Download and Upload speed are in bps
    return(dl, ul)

try:
    dl_speed, ul_speed = getInternetSpeed()
    print("The Download speed is {} Mbps".format(round(dl_speed / (10**6), 2)))
    print("The Upload speed is {} Mbps".format(round(ul_speed / (10**6), 2)))
except Exception as e:
    print("Error caused by ",e)


