package com.nononsenseapps.notepad.test.robotium_tests;

import android.content.Context;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.robotium.solo.Solo;


public class Robotium_TestAddNewNoteShouldShowNameInNotesScreen extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String noteName1 = "prepare food";

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_TestAddNewNoteShouldShowNameInNotesScreen(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        Context context = solo.getCurrentActivity().getApplicationContext();

        //clear app data
        PreferenceManager.
                getDefaultSharedPreferences(
                        context).edit().clear().commit();
        DatabaseHandler.resetDatabase(context);

        solo.finishOpenedActivities();
        super.tearDown();
    }


    public void testAddNewNoteShouldShowNameInNotesScreen() throws Exception{

        solo.waitForActivity("ActivityList", 1500);

        Robotium_Helper.closeDrawer(solo);
        Robotium_Helper.createNoteWithName(solo, noteName1);
        Robotium_Helper.navigateUp(solo);

        boolean noteFound = solo.searchText(noteName1);
        assertTrue("Note is found", noteFound);

    }

}
