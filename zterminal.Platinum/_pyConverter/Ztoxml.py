#CONFIG_FILENAME = "Zconf_deu.txt"
#CONFIG_FILENAME = "Zconf_fra.txt"
#CONFIG_FILENAME = "Zconf_jap.txt"
CONFIG_FILENAME = "Zconf_ita.txt"

import sys, re, os, shutil
import ply.lex as lex
from PlatLexer import PlatLexerClass
from pyxmlTest import Py2XML
import PlatUtils

#globals
struct_header = {}

# lexer
l = PlatLexerClass()
l.build()
lexer = l.getLexer()

# YACC stuff

import ply.yacc as yacc
tokens = PlatLexerClass.tokens
def p_whole_fucking_file(p):
	'whole_fucking_file : header entry_list'
	#p[0] = 'matched the whole bloody thing: ' + p[1] + "::::::::>>>\n========\n" + p[2]
	p[0] = {'lesson' : { 'header' : struct_header, 'lines' : p[2] }}

#------------header
def p_header_dialog(p):
	'header : cmdDLG EQUALS JUMBLE'
	struct_header['dlgName'] = p[3].replace('"', "&quot;")
def p_header_dic(p):
	'header : cmdDIC EQUALS JUMBLE'
	struct_header['dic'] = p[3].replace('"', "&quot;")
def p_header_author(p):
	'header : cmdAUTHOR EQUALS JUMBLE'
	struct_header['author'] = p[3].replace('"', "&quot;")
def p_header_icon(p):
	'header : cmdICON EQUALS JUMBLE'
def p_header_ppp(p):
	'header : cmdP'
def p_header_edlg(p):
	'header : cmdE EQUALS JUMBLE'
	struct_header['dlgFile'] = p[3].replace('"', "&quot;")
def p_header_SlideDialog(p):
	'header : SLIDE_DIALOG'
def p_header_wav(p):
	'header : WAV_FILENAME'
	struct_header['wav'] = p[1].replace('"', "&quot;")
def p_header(p):
	'header : header header'
	#do nothing?
#-----------entries
def p_entry_list(p):
	'entry_list : cmdSTAR entry cmdSTAR'
	p[0] = [p[2]]
def p_entry_list_rec(p):
	'entry_list : cmdSTAR entry entry_list'
	p[0] = [p[2]] + p[3]
def p_entry(p):
	'entry : entrySE entryT entryV entryB entryG'
	#p[0] = p[1] + ' .. ' + p[2] + ' .. ' + p[3] + ' .. ' + p[4] + ' .. ' + p[5]
	p[0] = {
		'audioTime' : p[1],
		'lang1' : p[2].replace('"', "&quot;"),
		'lang2' : p[3].replace('"', "&quot;"),
		'image' : p[4],
		'someNumber' : p[5]}
	
def p_entrySE(p):
	'''entrySE : cmdS JUMBLE cmdE JUMBLE
			   | empty'''
	if p[1] == None:
		return
	p[0] = {
		'in' : p[2].strip(),
		'out' : p[4].strip()}
def p_entryT(p):
	'''entryT : cmdT JUMBLE
			   | empty'''
	if p[1] == None:
		return
	p[0] = p[2]
def p_entryV(p):
	'''entryV : cmdV JUMBLE
			   | empty'''
	if p[1] == None:
		return
	p[0] = p[2]
def p_entryB(p):
	'''entryB : cmdB JUMBLE
			   | empty'''
	if p[1] == None:
		return
	p[0] = p[2]
def p_entryG(p):
	'''entryG : cmdG JUMBLE
			  | empty'''
	if p[1] == None:
		return
	p[0] = p[2]

	
# system rules
def p_empty(p):
	'empty :'
	pass
def p_error(p):
	print "Syntax error in input!", p
	
parser = yacc.yacc()




def processFile(pathS, pathD):
	# read the file 
	data = PlatUtils.loadUnicodeFileAsUtf8(pathS)
	data = l.preprocessData(data)
	
	python_object = parser.parse(data)
	
	serializer = Py2XML()
	xml_string = serializer.parse( python_object )
	out = open(pathD, 'w')
	out.write(xml_string)
	out.close()
	#print xxx

#processFile("./d_001_test.txt")

###########======================================

from configobj import ConfigObj
config = ConfigObj(CONFIG_FILENAME)
SRC_ROOT = config["SRC_ROOT"]
DST_ROOT = config["DEST_ROOT"]

dirList = os.listdir(SRC_ROOT)
for fname in dirList:
	fullPathS = SRC_ROOT + "/" + fname
	fullPathD = DST_ROOT + "/" + fname
	fullPathD = fullPathD.replace(".txt", '.xml')
	fullPathD = fullPathD.replace(".TXT", '.xml')
	if os.path.isdir(fullPathS):
		continue
	print "processing", fullPathS, " -----> ", fullPathD
	processFile(fullPathS, fullPathD)
	#tmp!!!!!!!!!!!
	#break
