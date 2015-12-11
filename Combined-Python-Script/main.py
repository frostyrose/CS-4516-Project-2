from mapRunner import *
from dataProcess import *
from rttCalc import *
from shutil import *

copyfile("dataCentersOrig.csv","dataCenters.csv")
fileOut = open("dataCenters.csv",'a')
numNewCenters = int(input("How many Data Centers would you like to add? (Int >= 0) >>>"))
while numNewCenters > 0:
    name = str(raw_input("Enter New Data Center Name>>>"))
    lat = str(raw_input("Enter Latitute (float in range[-60.00,90.00])"))
    log = str(raw_input("Enter Longitude (flat in range[-180.0,180.0])"))
    fileOut.write(name + ',' + lat + ',' + log + '\n')
    numNewCenters-=1
fileOut.close()
csvHandlerProcess()
csvHandlerRTT()
createMap()
