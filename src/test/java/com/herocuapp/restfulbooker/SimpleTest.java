package com.herocuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleTest {

   // @Test(groups = {"Booking", "Smoke"})
    public void createBookingWithPOJOTest() {
        System.out.println("Test createBookingWithPOJOTest executed");
        Assert.assertTrue(true);
    }

   // @Test(groups = {"Regression"})
    public void regressionTest() {
        System.out.println("Test regressionTest executed");
        Assert.assertTrue(true);
    }
}

