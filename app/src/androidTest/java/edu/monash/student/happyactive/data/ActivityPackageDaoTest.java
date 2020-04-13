package edu.monash.student.happyactive.data;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;


import edu.monash.student.happyactive.data.entities.ActivityPackage;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ActivityPackageDaoTest {
    private ActivityPackageDao activityPackageDao;
    private AppDatabase appDatabase;

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        activityPackageDao = appDatabase.activityPackageDao();
    }

    @After
    public void closeDb() throws IOException {
        appDatabase.close();
    }

    @Test
    public void ActivityPackageTableExistsTest() throws Exception {
        List<ActivityPackage> activityPackages = activityPackageDao.getAllActivityPackages();
        assertThat(activityPackages.size(), equalTo(0));
    }
}
