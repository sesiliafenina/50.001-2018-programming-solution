package com.example.testexam;

/* ATTENTION ------------------------
please put in your own package statement to get it to work in your android studio project
Easy way is to just copy-and-paste the code below to the android-generated file
do not submit the code with the package statement to vocareum
ATTENTION --------
*/

public class TestBond {

    public static void main(String[] args){

        Bond.BondBuilder bondBuilder = new Bond.BondBuilder();
        Bond bond1 = bondBuilder.createBond();
        bond1.setYieldCalculator(new WithCouponYield());
        System.out.println("" + bond1.calculateYTM());

        Bond bond2 = bondBuilder
                .setDuration(1)
                .setFaceValue(1000)
                .setSellingPrice(900)
                .setInterestPayment(0).createBond();
        bond2.setYieldCalculator(new ZeroCouponYield());
        System.out.println("" + bond2.calculateYTM());
    }

}

