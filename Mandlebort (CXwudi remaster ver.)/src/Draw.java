import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class Draw extends JPanel implements MouseListener, MouseMotionListener {
	static ComplexNumber CN = new ComplexNumber();
	static int length = 700;
	static int high = 700;
	static double manH = 2;
	static double manHL = -2;
	static double manL = -2;
	static double manR = 2;
	static double X;//new coordinate
	static double Y;//new coordinate
	static double X1;//new coordinate in drag
	static double Y1;//new coordinate in drag
	/**the numbers in Xtrans and Ytrans are exactly coordinate point*/ 
	// x= -0.734000000004965 , y = -0.195505000249857
	static double Xtrans = -0.7499713900591012;//positive mean GRAPH move left
	static double Ytrans =  -0.010014291972323218;//positive mean GRAPH move down
	static double zoom = Math.pow(2, 0);//positive zoom in
	static double zoomPow;
	static boolean isJuliet = false;
	static ComplexNumber Juliet = new ComplexNumber (0,0);
	
	//As CXwudi tested, we can see maximum  2^46 times zoomed in picture.
	
	/**
	 *             CX无敌の最高智慧结晶---Mandelbrot Graph
	 */

	public Draw(){
		setPreferredSize(new Dimension(length,high));
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		setLayout(null);
		
		JPanel juliet = new JPanel();
		juliet.setBounds(580, 0, 120, 79);
		add(juliet);
		juliet.setLayout(null);
		
		JPanel main = new JPanel();
		main.setBounds(0, 0, 700, 700);
		add(main);
		main.setLayout(null);
		/**
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(10, 44, 93, 23);
		main.add(btnReset);
		
		JButton btnJuliet = new JButton("Juliet");
		btnJuliet.setBounds(10, 10, 93, 23);
		main.add(btnJuliet);
		btnJuliet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isJuliet){
					isJuliet = false;
					Juliet = new ComplexNumber(0,0);
				} else {
					isJuliet = true;
				}
			}
		});
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zoom = 1;
				Xtrans = -0.75;
				Ytrans = 0;
				repaint();
			}
			
		});
		*/
	}
	
	static boolean escape(ComplexNumber c1) 
	{
		ComplexNumber c = new ComplexNumber(0,0);
		for (int n = 1; n<=100; n++){
			c = (c.mult(c)).add(c1);
		}
		if (c.modSquare() < 4)
			return false;
		else
			return true;
	}
	
	
	//c can be NaN + NaNi
	static int escape2(ComplexNumber c1) 
	{
		ComplexNumber c = new ComplexNumber(0,0);
		//for (int i = 1; i <=5; i++){
		for (int n = 1; n <= 250; n++){
			c = (c.mult(c)).add(c1);
		}
		if (c.modSquare() < 4){
			return (0);
		}
		c = new ComplexNumber(0,0);
		
		for (int n = 1; n <= 200; n++){
			c = (c.mult(c)).add(c1);
		}	
		if (c.modSquare() < 4){
			return (1);
		}
		c = new ComplexNumber(0,0);
		
		for (int n = 1; n <= 150; n++){
			c = (c.mult(c)).add(c1);
		}	
		if (c.modSquare() < 4){
			return (2);
		}
		c = new ComplexNumber(0,0);
		
		for (int n = 1; n <= 100; n++){
			c = (c.mult(c)).add(c1);
		}	
		if (c.modSquare() < 4){
			return (3);
		}
		c = new ComplexNumber(0,0);
		
		for (int n = 1; n <= 75; n++){
			c = (c.mult(c)).add(c1);
		}	
		if (c.modSquare() < 4){
			return (4);
		}
		c = new ComplexNumber(0,0);
		//}
		return 5;
	}
 
	static int escape3(ComplexNumber c1){
		ComplexNumber c = new ComplexNumber(Juliet.real,Juliet.imaginary);
		
		for (int n = 1; n <= 4000; n++){
			c = (c.mult(c)).add(c1);
			
				if (c.modSquare() > 4){
					return n;
				}
		}
		return 0;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Draw());
		frame.setSize(300, 300);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	
	public void paint (Graphics g){
		super.paintComponent(g);
		Graphics2D gg = (Graphics2D) g;
		int xx = 0, yy = 0;
		long start = System.nanoTime();
		for (double x = manL; x <= manR; x+= (manR - manL)/length)
		{
			for (double y = manHL; y <= manH; y+= (manH - manHL)/high)
			{
				xx = exchangeMagic.mandelbrotToJavaCoordX(x);
				yy = exchangeMagic.mandelbrotToJavaCoordY(y);
				ComplexNumber c1 = new ComplexNumber((x/zoom) + Xtrans,(y/zoom) + Ytrans);
				int aa = Draw.escape3(c1);
				
				if (aa == 0){
					gg.setColor(Color.BLACK);
					gg.drawLine(xx, yy, xx, yy);
					
				} else {
					//int R = (int)Math.round(100*Math.sin((int)(aa/25))+127.5);
					//int G = (int)Math.round(100*Math.cos((int)(aa/25))+127.5);
					//int B = (int)Math.round(-100*Math.cos((int)(aa/25))+127.5);
					
					//int R = (int)Math.round((117.5)*Math.sin( ((int)(aa/1)) *25 /180.0*Math.PI) +137.5);
					//int G = (int)Math.round((117.5)*Math.cos( ((int)(aa/1)) *25 /180.0*Math.PI) +137.5);
					int R = (int)Math.round((-127.5) *Math.cos( aa*4.0 /180.0*Math.PI) +127.5);
					int G = (int)Math.round((-127.5) *Math.cos( aa*20.0/3.0  /180.0*Math.PI) +127.5);
					int B = (int)Math.round((127.5) *Math.sin( aa*4.0 /180.0*Math.PI) +127.5);
					//System.out.println(aa + "=="+ "��Ӧ180���"+ (aa/2000.0) + "==" + B);
					Color c = new Color (R,G,B);
					gg.setColor(c);
					gg.drawLine(xx, yy, xx, yy);
	 			}
				
				/**
				int aa = Draw.escape2(c1);
				switch(aa)
				{
					case 0:
						gg.setColor(Color.black);
						gg.drawLine(xx, yy, xx, yy);
						break;
					case 1:
						gg.setColor(Color.red);
						gg.drawLine(xx, yy, xx, yy);
						break;
					case 2:
						gg.setColor(Color.yellow);
						gg.drawLine(xx, yy, xx, yy);
						break;
					case 3:
						gg.setColor(Color.pink);
						gg.drawLine(xx, yy, xx, yy);
						break;
					case 4:
						gg.setColor(Color.cyan);
						gg.drawLine(xx, yy, xx, yy);
						break;
					case 5:
						gg.setColor(Color.blue);
						gg.drawLine(xx, yy, xx, yy);
						break;
				}
				*/
				/*
				if (Draw.escape(c1)){
					gg.setColor(Color.blue);
					gg.drawLine(xx, yy, xx, yy);
				} else {
					gg.setColor(Color.green);
					gg.drawLine(xx, yy, xx, yy);
				}
				*/
				//System.out.println(aa);
				
			}

		}
		long stop = System.nanoTime();
		System.out.println("drawtime : " + 10e-9 * (stop-start));
	
	}
	 @Override
		public void mouseClicked(MouseEvent e) {

	    	if (e.getButton() == e.BUTTON1)
	    	{
	    		
	    		System.out.println("BUTTON 1!");
	    		X = exchangeMagic.javaToMandelbetCoordX(e.getX())/zoom + Xtrans;
				Y = exchangeMagic.javaToMandelbetCoordY(e.getY())/zoom + Ytrans;
				Xtrans = X;
				Ytrans = Y;
				zoom *= 2;
				zoomPow  = Math.log(zoom)/Math.log(2);
				//=========
				
				if (zoomPow <= 39){
					System.out.println("CXwudi and MIKU are travelling to the OSU Location:\n(" + X + ", " + Y + ") at 2 to the power " + zoomPow + " times zoom in. ");
				} else if (zoomPow <= 46){
					System.out.println("WARNING! Zombies are coming from the OSU Location:\n(" + X + ", " + Y + ") at 2 to the power " + zoomPow + " times zoom in.\nCXwudi and MIKU have to STOP their travels. Repeat! CXwudi and MIKU have to STOP their travels ");

				} else {
					System.out.println((int)zoomPow + " ZOMBIEs are coming!!!!!");
				}
				//===========
				repaint();
	    	}
	    	
	    	if (e.getButton() == e.BUTTON2)
	    	{
	    		System.out.println("BUTTON 2!");
	    		X = exchangeMagic.javaToMandelbetCoordX(e.getX())/zoom + Xtrans;
	 			Y = exchangeMagic.javaToMandelbetCoordY(e.getY())/zoom + Ytrans;
	 			if (isJuliet){
	 				Juliet = new ComplexNumber(X,Y);
	 			}
	    	}
	    	
	    	if (e.getButton() == e.BUTTON3)
	    	{
	    		System.out.println("BUTTON 3!");
	    		X = exchangeMagic.javaToMandelbetCoordX(e.getX())/zoom + Xtrans;
	 			Y = exchangeMagic.javaToMandelbetCoordY(e.getY())/zoom + Ytrans;
	 			Xtrans = X;
	 			Ytrans = Y;
	 			zoom /= 2;
	 			zoomPow  = Math.log(zoom)/Math.log(2);
	 			//=========
	 			
	 			if (zoomPow <= 39){
	 				System.out.println("CXwudi and MIKU are travelling to the OSU Location:\n(" + X + ", " + Y + ") at 2 to the power " + zoomPow + " times zoom in. ");
	 			} else if (zoomPow <= 46){
	 				System.out.println("WARNING! Zombies are coming from the OSU Location:\n(" + X + ", " + Y + ") at 2 to the power " + zoomPow + " times zoom in.\nCXwudi and MIKU have to STOP their travels. Repeat! CXwudi and MIKU have to STOP their travels ");

	 			} else {
	 				System.out.println((int)zoomPow + " ZOMBIEs are coming!!!!!");
	 			}
	 			//===========
	 			repaint();
	    	}
	    	
		   
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
		@Override
	    public void mouseMoved(MouseEvent evt){
    		
 			
	    }

		@Override
		public void mouseDragged(MouseEvent e) {
			
			
		}
}

