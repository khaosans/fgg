package edu.pdx.cse.mobilehealthapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import java.net.UnknownHostException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    public void testFoodItem() {
        FoodItem foodItem = new FoodItem("Name",132,112002);
        assertNotNull(foodItem);
        assertEquals(foodItem.getName(),"Name");
        assertEquals(foodItem.getCalories(),132);
        assertEquals(foodItem.getId(), 112002);
    }

}