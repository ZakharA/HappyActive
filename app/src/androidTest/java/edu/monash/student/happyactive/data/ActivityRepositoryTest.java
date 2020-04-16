package edu.monash.student.happyactive.data;

import android.app.Application;
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
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityPackageRepository;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class ActivityRepositoryTest {
    private ActivityPackageRepository activityPackageRepository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createRepository(){
        Application application = ApplicationProvider.getApplicationContext();
        activityPackageRepository = new ActivityPackageRepository(application);
    }

    @Test
    public void getAllActivityPackagesTest() throws Exception{
        LiveData<List<ActivityPackage>> result = activityPackageRepository.getAllActivityPackages();
        List<ActivityPackage> list = LiveDataTestUtil.getValue(result);
        assertThat(list.size(), equalTo(3));
    }

    @Test
    public void getActivityPackageByIdTest() throws Exception{
        LiveData<ActivityPackageWithTasks> liveResult = activityPackageRepository.getActivityWithTasks(1L);
        ActivityPackageWithTasks result = LiveDataTestUtil.getValue(liveResult);
        assertThat(result.activityPackage.id, equalTo(1L));
        assertThat(result.tasksList.size(), equalTo(4));
        assertThat(result.tasksList.get(0).activityId, equalTo(1L));
        assertThat(result.tasksList.get(1).activityId, equalTo(1L));
        assertThat(result.tasksList.get(2).activityId, equalTo(1L));
        assertThat(result.tasksList.get(3).activityId, equalTo(1L));
    }
}
