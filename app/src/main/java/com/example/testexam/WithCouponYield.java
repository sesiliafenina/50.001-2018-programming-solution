package com.example.testexam;

/* ATTENTION ------------------------
please put in your own package statement to get it to work in your android studio project
Easy way is to just copy-and-paste the code below to the android-generated file
do not submit the code with the package statement to vocareum
ATTENTION --------
*/

import javax.xml.datatype.Duration;

public class WithCouponYield implements  YieldCalculation{

    private double sellingPrice;
    private double faceValue;
    private double interestPayment;
    private double duration;

    @Override
    public double yieldToMaturity(Bond bond) {
        sellingPrice = bond.getSellingPrice();
        faceValue = bond.getFaceValue();
        interestPayment = bond.getInterestPayment();
        duration = bond.getDuration();

        double rup = 1;
        double rdown = Math.pow(10, -10);
        double delta = rup - rdown;
        while (delta - Math.pow(10, -5) > 0.0000001 ){
            double rmiddle = (rup + rdown)/2;
            double fmiddle = f(rmiddle);
            double fup = f(rup);
            double fdown = f(rdown);
            if (fmiddle > 0 && fup > 0){
                rup = rmiddle;
            }
            if (fmiddle < 0 && fup < 0){
                rup = rmiddle;
            }
            if (fmiddle > 0 && fdown > 0){
                rdown = rmiddle;
            }
            if (fmiddle < 0 && fdown < 0){
                rdown = rmiddle;
            }
            delta = rup - rdown;
        }
        return (rup + rdown)/2;
    }

    private double f(double r){
        return sellingPrice - (interestPayment * (1 - Math.pow(1/(1+r), duration))/r) - (faceValue/Math.pow(1+r, duration));
    }
}

