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
import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ActivitySessionDaoTest {

    private ActivitySessionDao activitySessionDao;
    private ActivityPackageDatabase activityPackageDatabase;
    private ActivityPackage newPackage;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        activityPackageDatabase = Room.inMemoryDatabaseBuilder(context, ActivityPackageDatabase.class).build();
        activitySessionDao = activityPackageDatabase.activitySessionDao();
        newPackage = new ActivityPackage("Cooking a meal", "Cook something awsome together with your grandparent");
    }

    @After
    public void closeDb() throws IOException {
        activityPackageDatabase.close();
    }

    @Test
    public void addNewSessionTest() throws Exception {
        ActivitySession newSession = new ActivitySession(100, 12, ActivityPackageStatus.STARTED);
        activitySessionDao.insertSession(newSession);
        List<ActivitySession> unPack = LiveDataTestUtil.getValue(activitySessionDao.getAllSessionsRecords());
        assertThat(unPack.size(), greaterThan(0));
    }
}
