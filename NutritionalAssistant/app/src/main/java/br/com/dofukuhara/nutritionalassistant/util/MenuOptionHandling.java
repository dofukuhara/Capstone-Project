package br.com.dofukuhara.nutritionalassistant.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.ui.AllIngredientsListActivity;
import br.com.dofukuhara.nutritionalassistant.ui.CategoryListActivity;
import br.com.dofukuhara.nutritionalassistant.ui.ConfigurationActivity;

/**
 * Created by dofukuhara on 17/02/2018.
 */

public class MenuOptionHandling {

    public static void performAction(int selectedItem, Context context) {
        Intent intent;

        switch (selectedItem) {
            case R.id.menu_by_category:
                if(!context.getClass().getName().equals(CategoryListActivity.class.getName())) {
                    intent = new Intent(context, CategoryListActivity.class);
                    context.startActivity(intent);
                } else {
                    // If the class that is calling this is the one that the user is trying to launch
                    // there is no need to do anything new

                    // TODO: Check if a reload operation would be suitable in this case!
                }

                break;

            case R.id.menu_all_ingred:
                if (!context.getClass().getName().equals(AllIngredientsListActivity.class.getName())) {
                    intent = new Intent(context, AllIngredientsListActivity.class);
                    context.startActivity(intent);

                } else {
                    // If the class that is calling this is the one that the user is trying to launch
                    // there is no need to do anything new

                    // TODO: Check if a reload operation would be suitable in this case!
                }
                break;

            case R.id.menu_fav_list:

                // TODO: Implement Menu for Favorites List option
                Toast.makeText(context, "This will launch " +
                        context.getString(R.string.menu_fav_list_label), Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_recipes_list:

                // TODO: Implement Menu for Recipes List option
                Toast.makeText(context, "This will launch " +
                        context.getString(R.string.menu_recipes_list_label), Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_config:
                // Launch Configuration Activity
                intent = new Intent(context, ConfigurationActivity.class);
                context.startActivity(intent);

                break;

            default:
                // Default case should never happen... but in case so, let's give this info the user
                Toast.makeText(context, context.getString(R.string.invalid_option),
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}
