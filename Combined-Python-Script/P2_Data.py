import csv
def indexColor(index):
    index = float(index)
    color = "blue"
    if index >= 0.45:
        color = "red"
    elif index >= 0.30:
        color = "orange"
    elif index >= 0.15:
        color = "yellow"
    else:
        color = "green"
    return color

def csvreader(filename,dataCenter):
    file = open(filename)
    dRdr = csv.DictReader(file)
    lat = []
    longs = []
    data = []
    for row in dRdr:
        nlat = row['Latitude']
        nlong = row['Longitude']
        lat.append(nlat)
        longs.append(nlong)
        if dataCenter:
            data.append("0")
        else:
            nIndex = row['index']
            data.append(nIndex)
    file.close()
    return(lat,longs,data)
