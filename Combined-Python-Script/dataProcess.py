from math import *
import math
import csv

def toRad(x):
    """Convert given numeric degrees into radians"""
    return x*math.pi/180.0;
def distanceMiles(lat1, long1, lat2, long2):
    """ Uses Haversine formula to calculate the great- circle distance between two points on the earth's surface expressed in (lat,long) coordinates """
    R = 3958.7558657440545 # Mean radius of Earth in miles
    dLat = toRad(lat2-lat1)
    dLon = toRad(long2-long1)
    lat1 = toRad(lat1)
    lat2 = toRad(lat2)
    a = math.sin(dLat/2) * math.sin(dLat/2) +  math.sin(dLon/2) * math.sin(dLon/2) *  math.cos(lat1) * math.cos(lat2)
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1-a))
    d = R * c
    return (d)

def csvHandlerProcess():
    fileOut = open("distances.csv",'w+')
    file = open("dataCenters.csv")
    dRdr = csv.DictReader(file)
    dataCenters = []
    for row in dRdr:
        dataCenters.append(row)
    file.close()
    file = open("capLatLong.csv")
    dRdr = csv.DictReader(file)
    fileOut.write('CountryCode,Latitude,Longitude,')
    for i in range(1,len(dataCenters)+1):
        fileOut.write(str(i)+',')
    fileOut.write('\n')
    for country in dRdr:
        fileOut.write(country['CountryCode']+','+country['Latitude']+','+country['Longitude']+',')
        for dataCenter in dataCenters:
            dataLat = float(dataCenter['Latitude'])
            dataLong = float(dataCenter['Longitude'])
            countryLat = float(country['Latitude'])
            countryLong = float(country['Longitude'])
            distance = distanceMiles(dataLat, dataLong, countryLat, countryLong)
            fileOut.write(str(distance))
            fileOut.write(",")
        fileOut.write("\n")
