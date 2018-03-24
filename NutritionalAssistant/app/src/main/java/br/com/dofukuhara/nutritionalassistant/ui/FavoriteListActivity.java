package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.adapter.FavoriteListAdapter;
import br.com.dofukuhara.nutritionalassistant.data.FavoriteContract;
import br.com.dofukuhara.nutritionalassistant.model.Favorite;
import br.com.dofukuhara.nutritionalassistant.util.AdMobManager;
import br.com.dofukuhara.nutritionalassistant.util.FavoritesNameComparator;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteListActivity extends AppCompatActivity implements FavoriteListAdapter.FavoriteItemClickListener {

    private ArrayList<Favorite> mFavoriteList;
    private FavoriteListAdapter mFavoriteAdapter;

    @BindView(R.id.pb_favorite_list)
    ProgressBar mPgFavoriteList;

    @BindView(R.id.rv_favorite_list)
    RecyclerView mRvFavoriteList;

    @BindView(R.id.adViewFavoriteList)
    AdView mAdView;

    // TODO: Implement Ingredient Removal from Favorite List

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle(R.string.activity_favorite_list);

        ButterKnife.bind(this);

        mRvFavoriteList.setHasFixedSize(true);
        mRvFavoriteList.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
        ));

        mAdView.loadAd(AdMobManager.getAdRequest());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // We need to load it from onResume because when the user cames back from Ingredient Details,
        // this activity content needs to be fetched again, as the Ingredient that was being viewed
        // could be removed from the Fav list
        loadContentFromProvider();
        setLayoutParams();
    }

    private void loadContentFromProvider() {
        ContentResolver cr = getContentResolver();
        Cursor favCursor = cr.query(FavoriteContract.FavoriteEntry.CONTENT_URI,
                null, null, null, null);
        mFavoriteList = Utils.cursorToFavoriteList(favCursor);
    }

    private void setLayoutParams() {
        Collections.sort(mFavoriteList, new FavoritesNameComparator());

        mFavoriteAdapter = new FavoriteListAdapter(this);
        mFavoriteAdapter.setFavoriteList(mFavoriteList);

        mRvFavoriteList.setAdapter(mFavoriteAdapter);

        // Adding a divider between lines for a better visualization
        mRvFavoriteList.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFavoriteItemClick(boolean addToFav, Favorite favorite) {
        if (addToFav) {
            Intent intent = new Intent(this, IngredientDetailsActivity.class);
            intent.putExtra(Utils.CONST_INTENT_INGREDIENT_ID, favorite.getIngredientId());

            startActivity(intent);
        } else {
            deleteIngFromFav(favorite);
        }
    }

    private void deleteIngFromFav(final Favorite favorite) {
        final ContentResolver cr = getContentResolver();
        Uri uri = ContentUris.withAppendedId(
                FavoriteContract.FavoriteEntry.CONTENT_URI,
                favorite.getIngredientId()
        );

        int numberDeletedItems = cr.delete(uri, null, null);

        Snackbar mySnackbar;
        if (numberDeletedItems <= 0) {
            mySnackbar = Snackbar.make(findViewById(R.id.favorite_constraint_layout),
                    R.string.ing_removed_from_fav_fail, Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        } else {
            mySnackbar = Snackbar.make(findViewById(R.id.favorite_constraint_layout),
                    R.string.ing_removed_from_fav_success, Snackbar.LENGTH_LONG);
            mySnackbar.setAction(R.string.undo,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ContentValues cv = Utils.favToCv(favorite);
                            Uri redo = cr.insert(FavoriteContract.FavoriteEntry.CONTENT_URI, cv);
                            if (redo == null) {
                                Snackbar.make(findViewById(R.id.favorite_constraint_layout),
                                        R.string.save_to_fav_fail, Snackbar.LENGTH_LONG).show();
                            } else {
                                mFavoriteList.add(favorite);
                                Collections.sort(mFavoriteList, new FavoritesNameComparator());
                                mFavoriteAdapter.setFavoriteList(mFavoriteList);
                                mRvFavoriteList.setAdapter(mFavoriteAdapter);
                            }

                        }
                    });
            mySnackbar.show();

            mFavoriteList.remove(favorite);
            mFavoriteAdapter.setFavoriteList(mFavoriteList);
            mRvFavoriteList.setAdapter(mFavoriteAdapter);
        }
    }
}
