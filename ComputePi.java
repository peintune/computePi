package com.study.pi;

import java.math.BigDecimal;

/**
 * Created by hekun< http://snappydata.top >on 2017/3/14.
 */
public class ComputePi {

    /**
     * 较著名的表示π的级数有莱布尼茨级数 　　π/4=1-1/3+1/5-1/7+1/9……
     * 以及威廉姆斯无穷乘积式 　　π/2=2*2/3*4/3*4/5*6/5*6/7*8/7*8/9……
     */


    /** constants used in pi computation */
    private static final BigDecimal FOUR = BigDecimal.valueOf(4);


    /** rounding mode to use during pi computation */
    private static final int roundingMode = BigDecimal.ROUND_HALF_EVEN;


    /**
     * Compute the value of pi to the specified number of
     * digits after the decimal point.  The value is
     * computed using Machin's formula:
     *
     *          pi/4 = 4*arctan(1/5) - arctan(1/239)
     *
     * and a power series expansion of arctan(x) to
     * sufficient precision.
     */

    public static void main(String[] args){

        int digits = 100000;
        String pi = computePi(digits).toString();
        System.out.println("length: " + pi.length());
        System.out.println(pi);

    }

    public static void computePiByLBNZ(){

    }

    public static void computePiByWLMS(){

    }


    public static BigDecimal computePi(int digits) {
        int scale = digits + 5;
        BigDecimal arctan1_5 = arctan(5, scale);
        BigDecimal arctan1_239 = arctan(239, scale);
        BigDecimal pi = arctan1_5.multiply(FOUR).subtract(
                arctan1_239).multiply(FOUR);
        return pi.setScale(digits,
                BigDecimal.ROUND_HALF_UP);
    }


    /**
     * Compute the value, in radians, of the arctangent of
     * the inverse of the supplied integer to the specified
     * number of digits after the decimal point.  The value
     * is computed using the power series expansion for the
     * arc tangent:
     *
     * arctan(x) = x - (x^3)/3 + (x^5)/5 - (x^7)/7 +
     *     (x^9)/9 ...
     */
    public static BigDecimal arctan(int inverseX,
                                    int scale)
    {
        BigDecimal result, numer, term;
        BigDecimal invX = BigDecimal.valueOf(inverseX);
        BigDecimal invX2 =
                BigDecimal.valueOf(inverseX * inverseX);

        numer = BigDecimal.ONE.divide(invX,
                scale, roundingMode);

        result = numer;
        int i = 1;
        do {
            numer =
                    numer.divide(invX2, scale, roundingMode);
            int denom = 2 * i + 1;
            term =
                    numer.divide(BigDecimal.valueOf(denom),
                            scale, roundingMode);
            if ((i % 2) != 0) {
                result = result.subtract(term);
            } else {
                result = result.add(term);
            }
            i++;
        } while (term.compareTo(BigDecimal.ZERO) != 0);
        return result;
    }

}

