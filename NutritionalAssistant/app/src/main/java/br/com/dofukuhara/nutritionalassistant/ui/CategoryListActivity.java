package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.util.Utils;

public class CategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        SharedPreferences sharedPref = getSharedPreferences(
                Utils.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        if (!sharedPref.contains(Utils.CONST_SHARED_PREF_ALREADY_OPENED_APP)) {
            Intent intent = new Intent(this, ConfigurationActivity.class);
            intent.putExtra(Utils.CONST_CONFIG_SETUP_WIZARD, true);
            startActivity(intent);

            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_list_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();

        Intent intent;

        switch (selectedItem) {
            case R.id.menu_by_category:

                // TODO: Implement Menu By Category option
                Toast.makeText(this, "This will launch " +
                        getString(R.string.menu_by_category_label), Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_all_ingred:

                // TODO: Implement Menu for All Ingredients option
                Toast.makeText(this, "This will launch " +
                        getString(R.string.menu_all_ingred_list_label), Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_fav_list:

                // TODO: Implement Menu for Favorites List option
                Toast.makeText(this, "This will launch " +
                        getString(R.string.menu_fav_list_label), Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_recipes_list:

                // TODO: Implement Menu for Recipes List option
                Toast.makeText(this, "This will launch " +
                        getString(R.string.menu_recipes_list_label), Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_config:
                // Launch Configuration Activity
                intent = new Intent(this, ConfigurationActivity.class);
                startActivity(intent);

                break;

            default:
                // Default case should never happen... but in case so, let's give this info the user
                Toast.makeText(this, getString(R.string.invalid_option),
                        Toast.LENGTH_LONG).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
