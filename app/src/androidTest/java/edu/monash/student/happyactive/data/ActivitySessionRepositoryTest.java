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

import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivitySessionRepository;
import edu.monash.student.happyactive.data.entities.ActivitySession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(AndroidJUnit4.class)
public class ActivitySessionRepositoryTest {

    private ActivitySessionRepository activitySessionRepository;
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createRepository(){
        Application application = ApplicationProvider.getApplicationContext();
        activitySessionRepository = new ActivitySessionRepository(application);
    }

    @Test
    public void getAllActivityPackagesTest() throws Exception{
        LiveData<List<ActivitySession>> result = activitySessionRepository.getAllSessions();
        List<ActivitySession> list = LiveDataTestUtil.getValue(result);
        assertThat(list.size(), greaterThan(0));
    }

    @Test
    public void insertNewActivitySession() throws Exception{
        ActivitySession session = new ActivitySession(1L, 1L, ActivityPackageStatus.STARTED);
        long sessionId = activitySessionRepository.insertNewSession(session);
        assertThat(sessionId, greaterThan(0l));
    }

    @Test
    public void cancelSessionTest() throws Exception{
        ActivitySession session = new ActivitySession(1L, 1L, ActivityPackageStatus.STARTED);
        long sessionId = activitySessionRepository.insertNewSession(session);
        ActivitySession sessionResult = activitySessionRepository.findSessionByActivityId(1L, sessionId);
        assertThat(sessionResult.status, equalTo(ActivityPackageStatus.STARTED));

        activitySessionRepository.cancelSession(sessionResult);
        ActivitySession cancelledSession = activitySessionRepository.findSessionByActivityId(1L, sessionId);
        assertThat(cancelledSession.status, equalTo(ActivityPackageStatus.CANCELED));
    }

}
