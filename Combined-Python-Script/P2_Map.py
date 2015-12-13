from graphics import *
import graphics
import LatLongToPixels
from P2_Data import *

def initializeMap(mapFile, xPixels, yPixels):
    win = graphics.GraphWin('World Map', xPixels, yPixels)
    win.setCoords(0, 0, xPixels, yPixels)
    center = graphics.Point(xPixels/2, yPixels/2)
    image = graphics.Image(center, mapFile)
    image.draw(win)
    return win

def circlePlotter(lats,longs,datalist,win,latlong,dataCenter):
    size = len(lats)
    i=1
    for record in range(size):
        #print "Lat: " + lats[record] + "\tLong: " + longs[record]
        data = latlong.latLong2Pixels(float(lats[record])+0.750, float(longs[record])-3.50-(float(longs[record])/75))
        center = Point(float(data[0]),float(data[1]))
        c = Circle(center,5)
        color = "blue"
        if dataCenter:
            if i<37:
                #print 'Data Center ' + str(i) + ' - Black'
                color = 'black'
                c.setOutline('black')
            else:
                #print 'Data Center ' + str(i) + ' - Purple'
                color = 'magenta'
                c.setOutline('black')
            i+=1
        else:
            color = indexColor(datalist[record])
            c.setOutline('black')
        c.setFill(color)
        c.draw(win)
    return
