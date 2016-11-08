package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class uiautomator_TestAddTaskListCheckItIsAddedToDrawer extends BaseTestClass{

    private String taskListName = "a random task list";

    @Test
    public void testAddTaskListCheckItIsAddedToDrawer() throws Exception{

        uiautomator_helper.createTaskList(device, taskListName);

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")), LAUNCH_TIMEOUT);

        uiautomator_helper.openDrawer(device);

        UiObject2 object2 = device.findObject(By.text(taskListName));
        assertNotNull("Note not found", object2);
    }
}
