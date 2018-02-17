package br.com.dofukuhara.nutritionalassistant.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.ui.ConfigurationActivity;

/**
 * Created by dofukuhara on 17/02/2018.
 */

public class MenuOptionHandling {

    public static void performAction(int selectedItem, Context context) {
        Intent intent;

        switch (selectedItem) {
            case R.id.menu_by_category:

                // TODO: Implement Menu By Category option
                Toast.makeText(context, "This will launch " +
                        context.getString(R.string.menu_by_category_label), Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_all_ingred:

                // TODO: Implement Menu for All Ingredients option
                Toast.makeText(context, "This will launch " +
                        context.getString(R.string.menu_all_ingred_list_label), Toast.LENGTH_SHORT).show();

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
