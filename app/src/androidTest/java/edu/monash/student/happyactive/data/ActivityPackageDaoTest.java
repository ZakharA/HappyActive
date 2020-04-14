package edu.monash.student.happyactive.data;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ActivityPackageDaoTest {
    private ActivityPackageDao activityPackageDao;
    private ActivityPackageDatabase activityPackageDatabase;
    private ActivityPackage newPackage;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        activityPackageDatabase = Room.inMemoryDatabaseBuilder(context, ActivityPackageDatabase.class).build();
        activityPackageDao = activityPackageDatabase.activityPackageDao();
        newPackage = new ActivityPackage("Cooking a meal", "Cook something awsome together with your grandparent");
    }

    @After
    public void closeDb() throws IOException {
        activityPackageDatabase.close();
    }

    @Test
    public void ActivityPackageTableExistsTest() throws Exception {
        LiveData<List<ActivityPackage>> activityPackages = activityPackageDao.getAllActivityPackages();
        List<ActivityPackage> unPack = LiveDataTestUtil.getValue(activityPackages);
        assertThat(unPack.size(), equalTo(0));
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

    @Test
    public void addAndCheckActivityWithTasksTest() throws Exception {
        ActivityPackage firstActivity = new ActivityPackage("Cooking", "Cook something amazing together");
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Groceries", "Buy ingredients"));
        tasks.add(new Task("Cook", "Start cooking"));
        tasks.add(new Task("Photo", "Take a photo of the dish"));

        activityPackageDao.insertNew(firstActivity, tasks);
        LiveData<List<ActivityPackageWithTasks>> activityPackageWithTasks = activityPackageDao.getAll();
        List<ActivityPackageWithTasks> unPack = LiveDataTestUtil.getValue(activityPackageWithTasks);
        assertThat(unPack.size(), greaterThan(0));
        assertThat(unPack.get(0).tasksList.size(), equalTo(3));

    }
}
