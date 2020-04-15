package edu.monash.student.happyactive;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.PackageSessionManager;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.Task;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class PackageSessionManagerTest {

    private ActivityPackage firstActivity;
    private List<Task> tasks;
    private PackageSessionManager sessionManager;

    @Before
    public void initiateActivityWithTasks(){
        firstActivity = new ActivityPackage("Cooking", "Cook something amazing together");
        tasks = new ArrayList<>();
        tasks.add(new Task("Groceries", "Buy ingredients"));
        tasks.add(new Task("Cook", "Start cooking"));
        tasks.add(new Task("Photo", "Take a photo of the dish"));
        sessionManager = new PackageSessionManager(firstActivity.id, tasks);
    }

    @Test
    public void getFirstTaskTest(){
        Task task = sessionManager.getCurrentTaskOnDisplay();
        assertThat(task, equalTo(tasks.get(0)));
    }

    @Test
    public void getNextTaskTest(){
        Task nextTask = sessionManager.completeTaskInProgress();
        assertThat(nextTask, equalTo(tasks.get(1)));
        assertThat(sessionManager.getTaskInProgress(), equalTo(tasks.get(1)));
        assertThat(sessionManager.getCurrentTaskOnDisplay(), equalTo(tasks.get(1)));
    }

    @Test
    public void taskInProgressRemainsAfterPreviousTaskIsOnDisplay(){
        sessionManager.completeTaskInProgress();
        sessionManager.completeTaskInProgress();
        Task onDisplay = sessionManager.getPreviousTask();
        assertThat(onDisplay, equalTo(tasks.get(1)));
        assertThat(sessionManager.getTaskInProgress(), equalTo(tasks.get(2)));
        assertThat(sessionManager.getCurrentTaskOnDisplay(), equalTo(tasks.get(1)));
    }

    @Test
    public void getNextTaskAfterReturnTaskInProgress(){
        Task task = sessionManager.getCurrentTaskOnDisplay();
        Task nextTask = sessionManager.getTaskAfter(task);
        assertThat(task, equalTo(nextTask));
        assertThat(sessionManager.getTaskInProgress(), equalTo(tasks.get(0)));
        assertThat(sessionManager.getCurrentTaskOnDisplay(), equalTo(tasks.get(0)));
    }

}
