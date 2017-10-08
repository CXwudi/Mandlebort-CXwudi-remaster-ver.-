package Remaster_Ver;

import java.math.*;

public class SuperComplexNumber {// which will use BigDecimal instead of int, to break the limitation of Bitmodel!!

	BigDecimal x, y; // x & y coordinate.

	public SuperComplexNumber(int scale) {
		this.x = BigDecimal.ZERO;
		this.y = BigDecimal.ZERO;
	}

	public SuperComplexNumber(String x, String y, int scale) {
		try {
			this.x = new BigDecimal(x).setScale(scale, RoundingMode.HALF_UP);
			this.y = new BigDecimal(y).setScale(scale, RoundingMode.HALF_UP);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("invalid Super Complex Number, a 0+0i is created instead");
			this.x = BigDecimal.ZERO;
			this.y = BigDecimal.ZERO;
		}
	}

	public SuperComplexNumber(BigDecimal x, BigDecimal y) {
		int scale = Math.max(x.scale(), y.scale());
		this.x = x.setScale(scale, RoundingMode.HALF_UP);
		this.y = y.setScale(scale, RoundingMode.HALF_UP);
	}

	public SuperComplexNumber add(SuperComplexNumber augent) {
		MathContext mc = new MathContext(Math.min(this.x.scale(), augent.x.scale()));
		BigDecimal xBigDecimal = this.x.add(augent.x,mc), 
				yBigDecimal = this.y.add(augent.y,mc);
		return new SuperComplexNumber(xBigDecimal, yBigDecimal);
	}

	public SuperComplexNumber mult(SuperComplexNumber multiplicand) {
		MathContext mc = new MathContext(Math.min(this.x.scale(), multiplicand.x.scale()));
		BigDecimal xBigDecimal = this.x.multiply(multiplicand.x, mc).subtract(this.y.multiply(multiplicand.y, mc),mc),
				yBigDecimal = this.x.multiply(multiplicand.y, mc).add(this.y.multiply(multiplicand.x, mc),mc);
		return new SuperComplexNumber(xBigDecimal, yBigDecimal);
	}
	
	public BigDecimal modSquare() {
		MathContext mc = new MathContext(this.x.scale());
		return this.x.pow(2, mc).add(this.y.pow(2, mc),mc);
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SuperComplexNumber aComplexNumber = new SuperComplexNumber("3", "4",500),
				bComplexNumber = new SuperComplexNumber("1", "2",500);
		System.out.println(aComplexNumber.add(bComplexNumber));
		System.out.println(aComplexNumber.mult(bComplexNumber));	
		
	}

	/**
	 * @return the scale of x coordinate (is as same as y coordinate)
	 */
	public int getScale() {
		return this.x.scale();
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(int scale) {
		x = x.setScale(scale);
		y = y.setScale(scale);
	}

	@Override
	public String toString() {
		return this.x.toString() + " + " + this.y.toString() + "i";
	}

}
