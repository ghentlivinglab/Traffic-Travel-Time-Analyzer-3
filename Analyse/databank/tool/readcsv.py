#!/usr/bin/python

import sys
import csv
import re

if(len(sys.argv)==1):
    print "Usage: readcsv.py [csvfile]"
    exit(0)

with open(sys.argv[1]) as csvfile:
    reader = csv.reader(csvfile)
    rownum=0
    volgnr=0
    for row in reader:
        if rownum>0:
            if re.search( r'Traject (.)*', row[0], re.M|re.I):
                volgnr=0
                trajectnr = row[0][-2:]
                trajectnr = trajectnr.strip()
                aantal_ptn = row[1][-1:]
            else:
                if(str(trajectnr)!="V"):
                    print "insert into "+\
                        "waypoints(traject_id,volgnr,longitude,latitude) values"+ \
                        '((select ID from trajecten where letter="'+str(trajectnr)+'"),'+ \
                        str(volgnr)+","+row[0]+","+row[1]+");";
                volgnr=volgnr+1
        rownum=rownum+1
