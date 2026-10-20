package com.example.databaseproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class FeedReaderDbHelperTest {

    private FeedReaderDbHelper dbHelper;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // A null name gives an in-memory database, so each test starts empty
        dbHelper = new FeedReaderDbHelper(context, null);
    }

    @After
    public void closeDb() {
        dbHelper.close();
    }

    @Test
    public void insertAndReadEntry() {
        long id = dbHelper.insertEntry("My Title", "My Subtitle");
        assertTrue(id > 0);

        List<Long> ids = dbHelper.findEntryIdsByTitle("My Title");
        assertEquals(1, ids.size());
        assertEquals(id, (long) ids.get(0));
    }

    @Test
    public void updateEntryTitle() {
        dbHelper.insertEntry("MyOldTitle", "Subtitle");

        assertEquals(1, dbHelper.updateTitle("MyOldTitle", "MyNewTitle"));
        assertTrue(dbHelper.findEntryIdsByTitle("MyOldTitle").isEmpty());
        assertEquals(1, dbHelper.findEntryIdsByTitle("MyNewTitle").size());
    }

    @Test
    public void deleteEntry() {
        dbHelper.insertEntry("MyTitle", "A");
        dbHelper.insertEntry("MyTitle", "B");

        assertEquals(2, dbHelper.deleteEntriesByTitle("MyTitle"));
        assertTrue(dbHelper.findEntryIdsByTitle("MyTitle").isEmpty());
    }
}
