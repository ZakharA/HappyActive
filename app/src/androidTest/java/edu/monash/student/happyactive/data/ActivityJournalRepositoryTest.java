package edu.monash.student.happyactive.data;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityJournalRepository;
import edu.monash.student.happyactive.data.entities.ActivityJournal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class ActivityJournalRepositoryTest {

    private ActivityJournalRepository ActivityJournalRepository;
    private ActivityJournal journal;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createRepository() throws Exception{
        Application application = ApplicationProvider.getApplicationContext();
        ActivityJournalRepository = new ActivityJournalRepository(application);
        journal = new ActivityJournal(0l, "My feelings");
    }

    @Test
    public void insertNewJournalEntryTest() throws ExecutionException, InterruptedException {
        long id = ActivityJournalRepository.insertNewEntry(journal);
        assertThat(id, greaterThan(0l));
    }

    @Test
    public void findJournalByIdTest() throws ExecutionException, InterruptedException {
        long id = ActivityJournalRepository.insertNewEntry(journal);
        ActivityJournal result = ActivityJournalRepository.findById(id);
        assertThat(result.id , equalTo(id));
    }
}
