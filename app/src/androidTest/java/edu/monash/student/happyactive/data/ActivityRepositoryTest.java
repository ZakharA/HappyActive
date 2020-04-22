package edu.monash.student.happyactive.data;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityPackageRepository;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@RunWith(AndroidJUnit4.class)
public class ActivityRepositoryTest {
    private ActivityPackageRepository activityPackageRepository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private ActivityPackage activityPackage;
    private List<Task> tasks;
    private int activitiesInTotal;
    @Before
    public void createRepository() throws Exception{
        Application application = ApplicationProvider.getApplicationContext();
        activityPackageRepository = new ActivityPackageRepository(application);
        activityPackage = new ActivityPackage("title", "description");
        tasks = new ArrayList<>();
        tasks.add(new Task("task1", "task1 description"));
        List<ActivityPackage> packages = LiveDataTestUtil.getValue(activityPackageRepository.getAllActivityPackages());
        activitiesInTotal = packages.size();
    }

    @Test
    public void insertNewActivityPackageTest() throws Exception{
        long id = activityPackageRepository.insertNewActivityPackage(activityPackage);
        assertThat(id, greaterThanOrEqualTo(0l));
    }

    @Test
    public void insertNewActivityPackageWithTaskTest() throws Exception{
        long activityId = activityPackageRepository.insertNewActivityWithTasks(activityPackage, tasks);
        LiveData<ActivityPackageWithTasks> result = activityPackageRepository.getActivityWithTasks(activityId);
        ActivityPackageWithTasks packageWithTasks = LiveDataTestUtil.getValue(result);
        assertThat(packageWithTasks.activityPackage.id, equalTo(activityId));
        assertThat(packageWithTasks.tasksList.size(), equalTo(1));
    }

    @Test
    public void getAllActivityPackagesTest() throws Exception{
        LiveData<List<ActivityPackage>> result = activityPackageRepository.getAllActivityPackages();
        List<ActivityPackage> list = LiveDataTestUtil.getValue(result);
        assertThat(list.size(), greaterThanOrEqualTo(activitiesInTotal));
    }

    @Test
    public void getActivityPackageWithTasksByIdTest() throws Exception{
        LiveData<ActivityPackageWithTasks> liveResult = activityPackageRepository.getActivityWithTasks(1L);
        ActivityPackageWithTasks result = LiveDataTestUtil.getValue(liveResult);
        assertThat(result.activityPackage.id, equalTo(1l));
    }
}
