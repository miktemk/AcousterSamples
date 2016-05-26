import sys, re, os, shutil
import ply.lex as lex

class PlatLexerClass:
	# List of token names. This is always required
	tokens = (
	   'cmdDLG',
	   'cmdDIC',
	   'cmdAUTHOR',
	   'cmdS',
	   'cmdE',
	   'cmdT',
	   'cmdV',
	   'cmdB',
	   'cmdG',
	   'cmdP',
	   'cmdSTAR',
	   'cmdICON',
	   'EQUALS',
	   #'NUMBER',
	   #'IMG_FILENAME',
	   #'DLG_FILENAME',
	   'WAV_FILENAME',
	   'SLIDE_DIALOG',
	   'JUMBLE',
	)
	SLIDE_DIALOG_ALT = '@slideDialogAlt'

	# my definitions
	t_cmdDLG	= r'@dlg'
	t_cmdDIC	= r'@dic'
	t_cmdAUTHOR	= r'@author'
	t_cmdS	= r'@s'
	t_cmdE	= r'@e'
	t_cmdT	= r'@t'
	t_cmdV	= r'@v'
	t_cmdB	= r'@b'
	t_cmdG	= r'@g'
	t_cmdP	= r'@p\d+'
	t_cmdSTAR	= r'@\*'
	t_cmdICON	= r'@icon'
	t_EQUALS = r'='
	#t_NUMBER = r'\d+'
	#t_IMG_FILENAME = r'dg\\\d+_\d+.gif'
	#t_DLG_FILENAME = r'exr\\dg\d+.txt'
	def t_WAV_FILENAME(self,t):
		r'.+\.\#\#\#wav'
		t.value = t.value.replace('###wav', 'wav')
		return t
	def t_SLIDE_DIALOG(self,t):
		r'@slideDialogAlt'# + SLIDE_DIALOG_ALT
		t.value = "Slide Dialog"
		return t
	t_JUMBLE = r'[^@=\n\r]+'

	# system defaults
	t_ignore  = ' \t\n\r'
	def t_error(self,t):
		print "======>>> Illegal character '%s'" % t.value[0]
		t.lexer.skip(1)

	def build(self,**kwargs):
		# Build the lexer
		self.lexer = lex.lex(module=self, **kwargs)
	
	def feedme(self,data):
		# Give the lexer some input
		self.lexer.input(data)
	
	def preprocessData(self, data):
		data = data.replace("Slide Dialog", self.SLIDE_DIALOG_ALT)
		data = data.replace(".wav", '.###wav')
		return data
		
	def getLexer(self):
		return self.lexer
