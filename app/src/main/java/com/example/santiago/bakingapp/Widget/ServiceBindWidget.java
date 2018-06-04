package com.example.santiago.bakingapp.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.santiago.bakingapp.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ServiceBindWidget extends RemoteViewsService {
    private static final String TAG = "ServiceBindWidget";
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (intent != null) {
            WidgetListAdapter widgetListAdapter = new WidgetListAdapter(
                    IngredientsWidgetProvider.getIngredientList(this),this);
            return widgetListAdapter;
        }
        return null;
    }
    public List<Ingredient> fakeIngredientsLis(){
        List<Ingredient > ingredients = new ArrayList<>();
        Ingredient in = new Ingredient("350","cups","Tosh Cookies");
        ingredients.add(in);
        ingredients.add(in);
        ingredients.add(in);
        ingredients.add(in);
        ingredients.add(in);
        return ingredients;
    }
}
