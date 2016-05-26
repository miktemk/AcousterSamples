import sys, os, re
import time, datetime
import getopt
import Image

def resizeImage(fileSrc, fileDest, divideFactor):
	format = "JPEG"
	fileName, fileExtension = os.path.splitext(fileSrc)
	fileExtension = fileExtension.lower()
	if fileExtension == ".png":
		format = "PNG"
	elif fileExtension == ".gif":
		format = "GIF"
	im = Image.open(fileSrc)
	width, height = im.size
	im.thumbnail((int(width/divideFactor), int(height/divideFactor)), Image.ANTIALIAS)
	im.save(fileDest, format)

#TODO: remove the tmp shit... aka comment this shit out
#if not os.path.exists("res/tmp-drawable-mdpi"): os.mkdir("res/tmp-drawable-mdpi")
#if not os.path.exists("res/tmp-drawable-ldpi"): os.mkdir("res/tmp-drawable-ldpi")

# create if not exists
if not os.path.exists("res/drawable-mdpi"):
	os.mkdir("res/drawable-mdpi")
if not os.path.exists("res/drawable-ldpi"):
	os.mkdir("res/drawable-ldpi")

# list dir
dirname = "res/drawable-hdpi"
dirList = os.listdir(dirname)
for fname in dirList:
	fullPath = dirname + "/" + fname
	if os.path.isdir(fullPath):
		continue
	print "image: ", fullPath
	resizeImage(fullPath, "res/drawable-mdpi/" + fname, 1.5)
	resizeImage(fullPath, "res/drawable-ldpi/" + fname, 2)
