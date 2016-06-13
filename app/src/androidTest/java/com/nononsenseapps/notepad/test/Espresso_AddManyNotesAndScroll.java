package com.nononsenseapps.notepad.test;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_AddManyNotesAndScroll {

    private String[] noteNameList =
            {"prepare food", "take dogs out", "water plants", "sleep",
            "go for a jog", "do some work", "play with the dog",
            "work out", "do weird stuff", "read a book", "drink water",
            "write a book", "proofread the book", "publish the book",
            "ponder life", "build a house", "repair the house", "call contractor",
            "write another book", "scrap the book project", "start a blog",
            "  ", "     "
            };

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Test
    public void addBigNumberOfNotesScrollDownAndDeleteOne(){

        Helper.closeDrawer();

        Helper.createNotes(noteNameList);

        onView(withText(noteNameList[0])).perform(click());

        //assert that the new fragment was launched
        onView(withId(R.id.taskText)).check(matches(isDisplayed()));

//        onView(withId(android.R.id.list))

                //onView(withText(noteName1)).check(matches(withText(noteName1)));
        /*
        private void clickCheckBoxAt(int position) {
        onView(withId(android.R.id.list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(position, Helper.MyViewAction.clickChildViewWithId(
                        R.id.checkbox
                ))
        );
    }
         */
    }

}
