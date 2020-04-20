package edu.monash.student.happyactive.data;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import edu.monash.student.happyactive.data.entities.ActivityJournal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class ActivityJournalDaoTest {
    private ActivityJournalDao activityJournalDao;
    private ActivityPackageDatabase activityPackageDatabase;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private ActivityJournal journal;

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        activityPackageDatabase = Room.inMemoryDatabaseBuilder(context, ActivityPackageDatabase.class).build();
        activityJournalDao = activityPackageDatabase.activityJournalDao();
        journal = new ActivityJournal(0l, "My feelings");
    }

    @After
    public void closeDb() throws IOException {
        activityPackageDatabase.close();
    }

    @Test
    public void insertNewJournalEntryTest(){
        long id = activityJournalDao.insertNewEntry(journal);
        assertThat(id, equalTo(1l));
    }
}
