package com.nononsenseapps.notepad.test.robotium_faulty_tests;

import android.content.Context;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.nononsenseapps.notepad.test.robotium_tests.Robotium_Helper;
import com.robotium.solo.Solo;

public class Robotium_Faulty_Tests extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String noteName1 = "prepare food";
    private String createNewText;

    public Robotium_Faulty_Tests(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
        createNewText = getActivity().getString(R.string.menu_createnew);
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

    public void estAddNewNoteSearchForFaultyNoteName(){

        Robotium_Helper.closeDrawer(solo);

        Robotium_Helper.createNoteWithName(solo, noteName1);
        Robotium_Helper.navigateUp(solo);

        solo.clickOnView(solo.getText(noteName1 + "asdf"));
        assertFalse("should have failed before this", true);
    }

    public void estSearchForElementWithTextShouldFailOnView(){

        Robotium_Helper.closeDrawer(solo);
        solo.clickOnText(createNewText);
        assertFalse("should have failed before this", true);
    }

    public void estSearchForElementWithIDShouldFailOnView(){
        Robotium_Helper.closeDrawer(solo);

        solo.clickOnView(solo.getView(R.id.fab));
        solo.clickOnView(solo.getView(R.id.fab));

        assertFalse("should have failed before this", true);
    }

    public void estSearchForElementWithFaultyID(){
//        solo.clickOnView(solo.getView(R.id.faulty_id));
    }

    public void estSearchForElementWithAmbiguousIdentifier(){
        Robotium_Helper.closeDrawer(solo);

        Robotium_Helper.createNoteWithName(solo, noteName1);
        Robotium_Helper.navigateUp(solo);
        Robotium_Helper.createNoteWithName(solo, noteName1);
        Robotium_Helper.navigateUp(solo);

        solo.clickOnText(noteName1);
        assertFalse("should have failed before this", true);
    }
}
