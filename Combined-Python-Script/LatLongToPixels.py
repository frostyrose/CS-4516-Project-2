# LatLongToPixels.py -- CS-1004, A-term 2015, published October 9, 2015

""" This module is implemented as a class to provide a
    conversion function between pixel coordinates (as required
    by an image in graphics.py) and latitude and longitude, as
    represented by a map image."""

class LatLong:
    def __init__(self, xPixels, yPixels, latMin, latMax,
                  longMin, longMax):
        self.xWidth     = xPixels
        self.yHeight    = yPixels
        self.bottomEdge = latMin    # minimum latitude
        self.topEdge    = latMax    # maximum latitude
        self.leftEdge   = longMin   # minimum longitude
        self.rightEdge  = longMax   # maximum longitude

        self.latScale   = yPixels / (latMax - latMin)
        self.longScale  = xPixels / (longMax - longMin)

    def latLong2Pixels(self, latitude, longitude):
        """Converts latitude and longitude to x- and y- pixel
 positions. Assumes that the origin is in the lower left corner"""
        yOffset     = latitude - self.bottomEdge
        yPosition   = yOffset * self.latScale
        xOffset     = longitude - self.leftEdge
        xPosition   = xOffset * self.longScale
        return xPosition, yPosition

    def pixels2LatLong(self, x, y):
        """Converts x- and y- pixel positions to latitude and
longitude . Assumes that the origin is in the lower left corner"""
        lat = self.bottomEdge + y/self.latScale
        longs = self.leftEdge + x/self.longScale
        return lat, longs
