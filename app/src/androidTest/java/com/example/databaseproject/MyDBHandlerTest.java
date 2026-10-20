package com.example.databaseproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MyDBHandlerTest {

    private Context context;
    private static final String TEST_DB = "productDB-test.db";

    @Before
    public void createDb() {
        context = ApplicationProvider.getApplicationContext();
        context.deleteDatabase(TEST_DB);
    }

    @After
    public void deleteDb() {
        context.deleteDatabase(TEST_DB);
    }

    @Test
    public void addProductAndFindIt() {
        MyDBHandler dbHandler = new MyDBHandler(context, TEST_DB);
        dbHandler.addProduct(new Product("Keyboard", 1234));

        Product found = dbHandler.findProduct("Keyboard");
        assertNotNull(found);
        assertEquals("Keyboard", found.getProductName());
        assertEquals(1234, found.getSku());
        assertTrue(found.getId() > 0);
    }

    @Test
    public void findUnknownProductReturnsNull() {
        MyDBHandler dbHandler = new MyDBHandler(context, TEST_DB);
        assertNull(dbHandler.findProduct("Nothing"));
    }

    @Test
    public void removeProduct() {
        MyDBHandler dbHandler = new MyDBHandler(context, TEST_DB);
        dbHandler.addProduct(new Product("Mouse", 42));

        assertTrue(dbHandler.removeProduct("Mouse"));
        assertNull(dbHandler.findProduct("Mouse"));
        assertFalse(dbHandler.removeProduct("Mouse"));
    }
}
