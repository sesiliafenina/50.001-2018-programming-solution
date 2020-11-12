package com.example.testexam;


/* ATTENTION ------------------------
please put in your own package statement to get it to work in your android studio project
Easy way is to just copy-and-paste the code below to the android-generated file
do not submit the code with the package statement to vocareum
ATTENTION --------
*/

import android.util.Log;

public class Bond {

    private double sellingPrice;
    private double faceValue;
    private double interestPayment;
    private double duration;

    private YieldCalculation yieldCalculation;

    private Bond(double sellingPrice, double faceValue, double interestPayment, double duration){
        if (sellingPrice <=0 || faceValue <= 0 || duration <= 0){
            throw new IllegalArgumentException("abort");
        }
        if (interestPayment < 0){
            throw new IllegalArgumentException("abort");
        }
        this.sellingPrice = sellingPrice;
        this.faceValue = faceValue;
        this.interestPayment = interestPayment;
        this.duration = duration;
    }

    static class BondBuilder{
        private double sellingPrice = 1000;
        private double faceValue = 1000;
        private double interestPayment = 10;
        private double duration = 1;

        public BondBuilder setSellingPrice(double sellingPrice) {
            this.sellingPrice = sellingPrice;
            return this;
        }

        public BondBuilder setFaceValue(double faceValue) {
            this.faceValue = faceValue;
            return this;
        }

        public BondBuilder setInterestPayment(double interestPayment) {
            this.interestPayment = interestPayment;
            return this;
        }

        public BondBuilder setDuration(double duration) {
            this.duration = duration;
            return this;
        }

        public Bond createBond() {
            return new Bond(sellingPrice, faceValue, interestPayment, duration);
        }
    }

    public void setYieldCalculator(YieldCalculation yieldCalculation) {
        this.yieldCalculation = yieldCalculation;
    }

    public double calculateYTM(){
        return yieldCalculation.yieldToMaturity(this);
    }

    public double getDuration() {
        return duration;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public double getInterestPayment() {
        return interestPayment;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }
}
