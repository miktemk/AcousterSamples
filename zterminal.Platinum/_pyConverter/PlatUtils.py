import sys, re, os, shutil

def loadUnicodeFileAsUtf8(filename):
	f = open(filename, 'rb')
	sss = f.read()
	f.close()
	if sss[:2] == '\xff\xfe':
		sss = sss.decode('utf16').encode('utf8')
	return sss