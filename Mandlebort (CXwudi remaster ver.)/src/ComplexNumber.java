
public class ComplexNumber {
	
	double real;
	double imaginary;
	ComplexNumber() {
		double real;
		double imaginary;
	}

	ComplexNumber(double r, double i) {
		real = r;
		imaginary = i;
	}
	void addTo	(ComplexNumber c){
		this.real += c.real;
		this.imaginary += c.imaginary;
	}
	
	void subFrom (ComplexNumber	c){
		this.real -= c.real;
		this.imaginary -= c.imaginary;
	}
	
	void multBy (ComplexNumber	c){
		double aa = this.real;
		this.real = this.real*c.real - this.imaginary*c.imaginary;
		this.imaginary = c.imaginary*aa + this.imaginary*c.real;
	}
	void squareThisCN (){
		this.multBy(this);
	}
	//it's changed
	ComplexNumber add (ComplexNumber c) {
		ComplexNumber ci = new ComplexNumber (this.real,this.imaginary);
		//ci.real += c.real;
		//ci.imaginary += c.imaginary;
		ci.addTo(c);
		return ci;
	}
	ComplexNumber sub (ComplexNumber c){
		ComplexNumber ci = new ComplexNumber (this.real,this.imaginary);
		ci.subFrom(c);
		return ci;
	}
	ComplexNumber mult (ComplexNumber c){
		ComplexNumber ci = new ComplexNumber (this.real,this.imaginary);
		//ci.real = this.real*c.real - this.imaginary*c.imaginary;
		//ci.imaginary = this.imaginary*c.real + c.imaginary*this.real;
		ci.multBy(c);
		return ci;
	}
	ComplexNumber square (){
		ComplexNumber ci = new ComplexNumber (this.real,this.imaginary);
		ci.mult(ci);
		return ci;
	}
	boolean equals	(ComplexNumber	c){
		if (this.real == c.real){
			if (this.imaginary == c.imaginary ){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	double modulus (){
		return Math.sqrt( Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
	}
	double modSquare (){
		return Math.pow(this.real, 2) + Math.pow(this.imaginary, 2);
	}
	void display (){
		System.out.println(this.real + " + " + this.imaginary + "i");
	}
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(0.5,0.5);
		ComplexNumber c2 = new ComplexNumber(0,0);
		ComplexNumber c3 = new ComplexNumber(4, 2);
		ComplexNumber c4 = new ComplexNumber(6,-12);
		ComplexNumber c5 = new ComplexNumber(-5,3);
		/*c1.add(c2).display();
		c1.display();
		c2.display();
		c1.add(c2).display();
		c1.display();
		*/
		for (int n = 1; n<=5; n++){
			c2 = (c2.mult(c2)).add(c1);
		}
		c2.display();
		
		
	}


}
