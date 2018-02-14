package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.util.Utils;

public class ConfigurationActivity extends AppCompatActivity {

    private Context mContext;
    private boolean isFirstTimeOpen = false;

    // Layout variables
    private CheckBox mCbIsMobileDataAllowed;
    private TextView mTvAccountName;
    private Button mButtonSaveConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        mContext = this;

        layoutInitialization();

        readSavedValuesFromPref();
    }

    private void readSavedValuesFromPref() {
        SharedPreferences sharedPref = getSharedPreferences(
                Utils.SHARED_PREF_KEY, Context.MODE_PRIVATE);

        // If the Is Mobile Data Allowed info is persisted in Shared Pref, lets read from it!
        if(sharedPref.contains(Utils.CONST_SHARED_PREF_IS_MOBILE_DATA_ALLOWED)) {
            mCbIsMobileDataAllowed.setChecked(
                    sharedPref.getBoolean(Utils.CONST_SHARED_PREF_IS_MOBILE_DATA_ALLOWED,
                            false));
        }

        // If the Account Name is persisted in Shared Pref, lets read from it!
        if(sharedPref.contains(Utils.CONST_SHARED_PREF_ACCOUNT_NAME_INFO)) {
            String accountName = sharedPref.getString(Utils.CONST_SHARED_PREF_ACCOUNT_NAME_INFO, "");
            if(!TextUtils.isEmpty(accountName)) {
                mTvAccountName.setText(accountName);
            }
        }

    }

    private void layoutInitialization() {
        setTitle(R.string.activity_configuration);

        /*
         Check from where this activity was opened:
          - Automatically by first time detection
          - Manually by the user
        */
        Intent intent = getIntent();
        if (intent.hasExtra(Utils.CONST_CONFIG_SETUP_WIZARD) &&
                intent.getBooleanExtra(Utils.CONST_CONFIG_SETUP_WIZARD, false)) {
            isFirstTimeOpen = true;
        } else {

            // TODO: Return to previous activity

            // Show the Up button in the action bar.
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        mCbIsMobileDataAllowed = findViewById(R.id.cb_allow_mobile_data_connection);
        mTvAccountName = findViewById(R.id.tv_account_config);
        mButtonSaveConfig = findViewById(R.id.btn_save_config);

        setListeners();
    }

    private void setListeners() {
        mButtonSaveConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences(
                        Utils.SHARED_PREF_KEY, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                // Save in Shared Pref if the user allowed or not the usage of Mobile Data
                boolean isChecked = mCbIsMobileDataAllowed.isChecked();
                editor.putBoolean(Utils.CONST_SHARED_PREF_IS_MOBILE_DATA_ALLOWED, isChecked);

                // Only save a 'valid' Account Name in Shared Pref
                String accountName = mTvAccountName.getText().toString() != getString(R.string.none)
                        ? mTvAccountName.getText().toString() : "";
                editor.putString(Utils.CONST_SHARED_PREF_ACCOUNT_NAME_INFO, accountName);

                /*
                   In case that this is the first time that the user opened this app,
                   only when the user hits the SAVE button that we will save this info in the
                   shared pref, so that the Main Activity can be launched instead
                */
                if (isFirstTimeOpen) {
                    editor.putBoolean(Utils.CONST_SHARED_PREF_ALREADY_OPENED_APP, true);
                }

                editor.commit();

                /*
                  If this is the first time, we need to send the user to the Main Activity,
                  otherwise, only close this to send to user to the previous screen.
                */
                if (isFirstTimeOpen) {
                    Intent intent = new Intent(mContext, CategoryListActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        });
    }
}
