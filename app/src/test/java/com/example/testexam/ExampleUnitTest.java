package com.example.testexam;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void dummyTest(){
    }

    @Test(expected = IllegalArgumentException.class)
    public void bondClassUnexpectedInput(){
        new Bond.BondBuilder().setFaceValue(-5.0).createBond();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}