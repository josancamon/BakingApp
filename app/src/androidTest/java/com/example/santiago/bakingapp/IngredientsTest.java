package com.example.santiago.bakingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import static android.app.Instrumentation.ActivityResult;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.santiago.bakingapp.Adapters.RecyclerIngredientsAdapter;
import com.example.santiago.bakingapp.Adapters.RecyclerRecipesAdapter;

import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import android.support.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
public class IngredientsTest {

    @Rule public IntentsTestRule<DetailsRecipeActivity> mActivityRule =
            new IntentsTestRule<>(DetailsRecipeActivity.class);

    @Before
    public void Stub_Intent(){
        intending(not(isInternal())).
              respondWith(new ActivityResult(Activity.RESULT_OK,null));
    }
    @Test
    public void check_ingredients(){
        onView(withId(R.id.recycler_recipes)).perform(RecyclerViewActions.scrollToPosition(0)).perform(click());
        //intended(allOf(hasExtra(Intent.EXTRA_TEXT,"1")));
        //onView(withId(R.id.ingrediets_recycler)).perform(RecyclerViewActions.scrollToHolder(withIngredientsRecyclerView("salt")));
    }

    public static org.hamcrest.Matcher< RecyclerView . ViewHolder > withIngredientsRecyclerView(final String text) {
        return new BoundedMatcher<RecyclerView.ViewHolder, RecyclerIngredientsAdapter.IngredientsViewHolder>(RecyclerIngredientsAdapter.IngredientsViewHolder.class) {


            @Override
            public void describeTo(Description description) {
                description.appendText("No se encontró "+text);
            }

            @Override
            protected boolean matchesSafely(RecyclerIngredientsAdapter.IngredientsViewHolder item) {
                TextView textView = item.itemView.findViewById(R.id.ingredient_name);
                if (textView==null){
                    return false;
                }
                return textView.getText().toString().contains(text);
            }
        };
    }}
