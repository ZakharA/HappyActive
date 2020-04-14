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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ActivityPackageDaoTest {
    private ActivityPackageDao activityPackageDao;
    private AppDatabase appDatabase;
    private ActivityPackage newPackage;

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        activityPackageDao = appDatabase.activityPackageDao();
        newPackage = new ActivityPackage("Cooking a meal", "Cook something awsome together with your grandparent");
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

    @Test
    public void addAndCheckActivityPackageWithoutTasksTest() throws Exception {
        long id = activityPackageDao.insertActivity(newPackage);
        ActivityPackage activityPackageFound = activityPackageDao.findById(id);
        assertThat(id, equalTo(activityPackageFound.id));
    }

    @Test
    public void checkActivityDeletedTask() throws Exception {
        long id = activityPackageDao.insertActivity(newPackage);
        activityPackageDao.deletePackage(id);
        ActivityPackage activityPackageFound = activityPackageDao.findById(id);
        assertThat(activityPackageFound, equalTo(null));
    }
}
