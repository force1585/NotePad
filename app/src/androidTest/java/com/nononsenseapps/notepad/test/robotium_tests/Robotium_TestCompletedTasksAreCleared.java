package com.nononsenseapps.notepad.test.robotium_tests;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.robotium.solo.Solo;

import java.util.List;

public class Robotium_TestCompletedTasksAreCleared extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    String[] noteNames = {"prepare food", "take dogs out", "water plants", "sleep"};
    private String OVERFLOW_MENU_CONTENT_DESCRIPTION;
    private String OVERFLOW_MENU_TEXT;
    private String POPUP_OK;
    private RecyclerView recyclerView;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_TestCompletedTasksAreCleared(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();

        //not in strings
        OVERFLOW_MENU_CONTENT_DESCRIPTION = "More options";
        POPUP_OK = "OK";

        OVERFLOW_MENU_TEXT =
                getActivity().getResources().getText(R.string.menu_clearcompleted).toString();

        recyclerView = (RecyclerView) getActivity().findViewById(android.R.id.list);
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

    public void testCompletedTasksAreCleared(){

        Robotium_Helper.closeDrawer(solo);

        Robotium_Helper.createNotes(solo, noteNames);

        //not the most compact way to do it, but maybe the sturdiest
        clickCheckBoxAt(1);
        clickCheckBoxAt(3);

        //get the overflow menu button by its' content description
        List<View> views = solo.getViews();
        for(int i = 0; i < views.size()-1; i++) {

            if(views.get(i).getContentDescription() != null &&
                    views.get(i).getContentDescription().equals(OVERFLOW_MENU_CONTENT_DESCRIPTION)) {
                solo.clickOnView(views.get(i));
            }
        }

        solo.clickOnText(OVERFLOW_MENU_TEXT);
        solo.clickOnText(POPUP_OK);

        boolean note2Found = solo.searchText(noteNames[0]);
        assertFalse("note 2 found" , note2Found);
        boolean note4Found = solo.searchText(noteNames[2]);
        assertFalse("note 4 found", note4Found);
    }

    private void clickCheckBoxAt(int position){
        View rowView = recyclerView.getChildAt(position);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);
        solo.clickOnView(checkBox);
    }
}
