package com.example.santiago.bakingapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.santiago.bakingapp.Fragments.RecipesListFragment;
import com.example.santiago.bakingapp.Model.Recipe;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements RecipesListFragment.OnRecipeClickListener, RecipesListFragment.AddShorcut {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipesListFragment recipesFragment = new RecipesListFragment();
            fragmentManager.beginTransaction().add(R.id.recipe_list, recipesFragment).commit();
        }
        //new async().execute();

    }

    @Override
    public void onRecipeClick(Recipe recipeClicked) {
        Toast.makeText(this, recipeClicked.getRecipeName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailsRecipeActivity.class);
        intent.putExtra("id", recipeClicked.getId());
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void addShortcut(Recipe recipeAdded) {
        setset();
    }

    public void setset() {
        ShortcutManager mShortcutManager = getSystemService(ShortcutManager.class);
        if (mShortcutManager.isRequestPinShortcutSupported()) {
            // Assumes there's already a shortcut with the ID "my-shortcut".
            // The shortcut must be enabled.
            ShortcutInfo pinShortcutInfo =
                    new ShortcutInfo.Builder(this, "my-shortcut").setShortLabel("asdas").
                            setLongLabel("Open the web site")
                            .setIcon(Icon.createWithResource(this, R.drawable.add_logo))
                            .setIntent(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.mysite.example.com/"))).build();

            // Create the PendingIntent object only if your app needs to be notified
            // that the user allowed the shortcut to be pinned. Note that, if the
            // pinning operation fails, your app isn't notified. We assume here that the
            // app has implemented a method called createShortcutResultIntent() that
            // returns a broadcast intent.
            Intent pinnedShortcutCallbackIntent =
                    mShortcutManager.createShortcutResultIntent(pinShortcutInfo);

            // Configure the intent so that your app's broadcast receiver gets
            // the callback successfully.
            PendingIntent successCallback = PendingIntent.getBroadcast(this, 0,
                    pinnedShortcutCallbackIntent, 0);

            mShortcutManager.requestPinShortcut(pinShortcutInfo,
                    successCallback.getIntentSender());
        }
    }
}
