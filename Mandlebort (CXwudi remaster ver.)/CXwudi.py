"""
				追忆美好の过去---Mandelbort set python ver
					
			this program is a memorial program for legend CXwudi 
			who has done the same project in Java in high school in 2015.
			
			the program is announced and started on 17th October 2016.
			part one done at 23:25 18th October 2016.
			part two done at 13:37 25th October 2016.
			
			
				原作: CX无敌の最高智慧结晶
				The origin Mandelbrot Graph program was designed in Java
"""
from SimpleGraphics import*
from math import *
import ctypes #Thank you Mr. Robert Collier, for helping me get the uesr's screen resolution.

#all the variables
screensizeX, screensizeY = ctypes.windll.user32.GetSystemMetrics(0), ctypes.windll.user32.GetSystemMetrics(1)#Thank you, Mr. Robert Collier
length = int(screensizeX * 0.65)
high = int(screensizeY * 0.8)
manHL = -2.0
manHH = 2.0
manXL = manHL * length / high
manXR = manHH * length / high
Xtrans = -0.7499713900591012#-0.16340885343081948
Ytrans = -0.010014291972323218#1.0350113264711276
zoompow = 15
zoom = pow(2,zoompow)
speedFactor = 2000


setWindowTitle('Mandelbort set Python ver by CXwudi highest Technologe center.')
setAutoUpdate(False)
resize(length,high)
update()

img = createImage(length,high)
#create a class and object of complex number in order do complex number calculation
# __init__ is the function similar to creating elements of an object in Java
#__variable is similar to private variable in Java
class complexNumber:
	def __init__(self, X, Y):
		self.__complexX = float(X)
		self.__complexY = float(Y)
		
	def getX(self):
		return self.__complexX
		
	def getX(self):
		return self.__complexX	
		
	def add(self,complexNumber_obj):
		x = self.__complexX + complexNumber_obj.__complexX
		y = self.__complexY + complexNumber_obj.__complexY
		return complexNumber(x,y)
		
	def mutilply(self,complexNumber_obj):
		x = self.__complexX * complexNumber_obj.__complexX - self.__complexY * complexNumber_obj.__complexY
		y = complexNumber_obj.__complexY * self.__complexX + self.__complexY * complexNumber_obj.__complexX
		return complexNumber(x,y)
		
	#be careful, this return a float, not a complex number object!
	def modSquare(self):
		return pow(self.__complexX,2) + pow(self.__complexY,2)
		
	def printCP(self):
		print(self.__complexX,'+ ' + str(self.__complexY) + 'i')

#I need an alianware laptop to be able to do these calculation fast.
def isEscape3 (complexNumber_obj):
	c = complexNumber(0,0)
	for i in range (1,speedFactor + 1,1): #speedFactor means how many times does it calculate, the more it calculate, the more fancy the mandlebort is
		c = (c.mutilply(c)).add(complexNumber_obj)
		if c.modSquare() > 4:
			return i
	return 0

#Four honor functions that tranfer between mandelbort coordinate and python coordinate
def mandelbortToPythonX(mandelbrotX):
	return int(round( float(mandelbrotX) * length/2/manXR + length/2, 0))
def mandelbortToPythonY(mandelbrotY):
	return int(round( -(float(mandelbrotY) * high/2/manHH + high/2) + high, 0))
def pythonToMandelbetCoordX(pythonX):
	return 2.0*manXR*float(pythonX)/length - manXR
def pythonToMandelbetCoordY(pythonY):
	return -2.0*manHH*float(pythonY)/high + manHH

def getMandlebortPos():
	x = pythonToMandelbetCoordX(mouseX())/zoom + Xtrans
	y = pythonToMandelbetCoordY(mouseY())/zoom + Ytrans
	return x, y

def drawMandelbort():
	x = manXL
	n = x *0.01
	flagDraw = x - n 
	#stupid Python can not make a 'for' loop like (int = i; i<= 90; i++){}
	#then I have to translate java 'for' loop into python 'while' loop
	while(x <= manXR):
	
		y = manHL
		while(y <= manHH):
			xx = mandelbortToPythonX(x)
			yy = mandelbortToPythonY(y)
			#print(x,y, xx,yy)
			CP = complexNumber(x/zoom + Xtrans, y/zoom + Ytrans)
			checkCP = isEscape3(CP)
			if checkCP == 0:
				setColor('black')
				line(xx,yy,xx+1,yy+1)
			else:
				R = int(round(-127.5 * cos(checkCP*4/180*pi) + 127.5, 0))
				G = int(round(-127.5 * cos(checkCP*20/3/180*pi)+127.5, 0))
				B = int(round(127.5 * sin(checkCP*4/180*pi)+127.5, 0))
				setColor(R,G,B)
				'''
				#this allows you to export the image as a file, but the performance is SUPER SLOW, 呵呵
				putPixel(img,xx,yy,R,G,B)
				drawImage(img,0,0)
				'''
				line(xx,yy,xx+1,yy+1)
			
			y = y + (manHH - manHL)/high
			
		if (flagDraw < x):
			#I check the closed() function in the SimpleGraphic liberary,
			#it actually contains a update() fyunction 
			if closed():
				break
			flagDraw = flagDraw - n
		
		x = x + (manXR - manXL)/length
	update()
	
	
#main drawing function
def main():
	##these codes are all the tester that used to test the program##
	a = complexNumber(1,1)
	b = complexNumber(2,2)
	c = a.add(b)
	d = a.mutilply(b)
	check = isEscape3(complexNumber(-0.7499713900591012,-0.010014291972323218))
	R = int(round(-127.5 * cos(check*4/180*pi) + 127.5, 0))
	G = int(round(-127.5 * cos(check*20/3/180*pi)+127.5, 0))
	B = int(round(127.5 * sin(check*4/180*pi)+127.5, 0))
	c.printCP()
	d.printCP()
	a.printCP()
	b.printCP()
	print(b.modSquare())
	print(1/pow(2.0,46))
	print(length,high)
	print(screensizeX,screensizeY)
	print(manXL,manXR)
	print(mandelbortToPythonX(-1),mandelbortToPythonY(-1))
	print(pythonToMandelbetCoordX(2),pythonToMandelbetCoordY(2))
	print(R,G,B)
	#global zoom
	#global zoompow
	#print(zoom,zoompow)
	
	##thanks for all above testers.
	##the basic part of the program is done at 23:25 18th October 2016.
	
	flag = True
	#The way of changing the global variables is SO WEIRD!!!
	global Xtrans
	global Ytrans
	global zoom
	global zoompow  
	global speedFactor
	
	speedFactor = int(input('Tell me how many times does each pixle point be calculated? input integer!\n i3 cpu = less than 1000, i5 cpu = 1000-2000, i7 cpu = more than 2000\n my number: '))
	goDirect = input('Do you want to go to a specific coordinate point and zoom at whatever times? \n answer \'Y\' or ''N'' ').upper() == 'Y'
	
	if goDirect:
		Xtrans = float(input('input mandlebort x-coordinate of the point'))
		Ytrans = float(input('input mandlebort y-coordinate of the point'))
		zoompow = float(input('input X, which represent zoom in at 2 to the power X times'))
		zoom = pow(2,zoompow)
	
	#the part two--- zooming & shifting controlation is done at 13:37 25th October 2016.
	#thanks for textbook chapter 5, which tells me how to use global variables
	while not closed():
		if flag:
			drawMandelbort()
			flag = False
			
		if leftButtonPressed() or rightButtonPressed():
			X, Y = getMandlebortPos()
			Xtrans = X
			Ytrans = Y
			 
			if leftButtonPressed():
				zoom = zoom * 2
			if rightButtonPressed():
				zoom = zoom / 2
			
			zoompow= log(zoom, 2)
			
			if (zoompow <= 39):
				print("CXwudi and MIKU are travelling to the OSU Location:\n(" + str(X) + ", " + str(Y) + ") at 2 to the power " + str(zoompow) + " times zoom in. ")
			elif (zoompow <= 46):
				print("WARNING! Zombies are coming from the OSU Location:\n(" + str(X) + ", " + str(Y) + ") at 2 to the power " + str(zoompow) + " times zoom in.\nCXwudi and MIKU have to STOP their travels. Repeat! CXwudi and MIKU have to STOP their travels ")
			else:
				print(str(zoompow) + " ZOMBIEs are coming!!!!!")
				
			flag = True
			
		
	print('\nThank you for your participation.\n\n                                        ------CXwudi')
	##Enjoy!
	
	
#run it
main()
