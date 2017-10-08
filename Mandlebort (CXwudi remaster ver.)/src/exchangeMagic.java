
public class exchangeMagic {

	static int length = 700;
	static int high = 700;
	static int mandelLength = 2;
	static int mandelHigh = 2;
	
	static int mandelbrotToJavaCoordX (double mandelbrotX){
		int a = (int) Math.round((mandelbrotX) * length/4 + length / 2);
		return a;
	}
	
	static int mandelbrotToJavaCoordY (double mandelbrotY){
		int a = -(int) Math.round((mandelbrotY) * high/4 + high / 2) + high;
		return a;
	}
	
	static double javaToMandelbetCoordX (double JavaX){
		double a = 4.0*(double)JavaX/high - 2.0;
		return a;
	}
	
	static double javaToMandelbetCoordY (double JavaY){
		double a = -4.0*(double)JavaY/high + 2.0;
		return a;
	}
	
	public static void main(String[] args) {

	}

}
