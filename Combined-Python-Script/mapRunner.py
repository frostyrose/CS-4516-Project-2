from P2_Map import *
from LatLongToPixels import *
from P2_Data import *
from P2_Filter import *

def createMap():
    latlong = LatLong(1764,762,-60,90,-180,168)
    win = initializeMap("world.gif",1764,762)
    dataCenterData = csvreader('dataCenters.csv',True)
    print len(dataCenterData[0])
    locationData = csvreader('minIndexes.csv',False)
    #print locationData
    circlePlotter(dataCenterData[0],dataCenterData[1],dataCenterData[2],win,latlong,True)
    circlePlotter(locationData[0],locationData[1],locationData[2],win,latlong,False)
