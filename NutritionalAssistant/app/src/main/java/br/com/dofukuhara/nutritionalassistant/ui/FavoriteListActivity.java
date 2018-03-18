package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.adapter.FavoriteAdapter;
import br.com.dofukuhara.nutritionalassistant.data.FavoriteContract;
import br.com.dofukuhara.nutritionalassistant.model.Favorite;
import br.com.dofukuhara.nutritionalassistant.util.AdMobManager;
import br.com.dofukuhara.nutritionalassistant.util.FavoritesNameComparator;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteListActivity extends AppCompatActivity implements FavoriteAdapter.FavoriteItemClickListener {

    private ArrayList<Favorite> mFavoriteList;
    private FavoriteAdapter mFavoriteAdapter;

    @BindView(R.id.pb_favorite_list)
    ProgressBar mPgFavoriteList;

    @BindView(R.id.rv_favorite_list)
    RecyclerView mRvFavoriteList;

    @BindView(R.id.adViewFavoriteList)
    AdView mAdView;

    // TODO: Implement Ingredient Removal from Favorite List

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mFavoriteList != null) {
            outState.putParcelableArrayList(Utils.CONST_BUNDLE_FAVORITE_LIST_PARCELABLE,
                    mFavoriteList);
        }
        super.onSaveInstanceState(outState);
    }

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

        if(savedInstanceState != null &&
                savedInstanceState.containsKey(Utils.CONST_BUNDLE_FAVORITE_LIST_PARCELABLE)) {
            mFavoriteList = savedInstanceState.getParcelableArrayList(
                    Utils.CONST_BUNDLE_FAVORITE_LIST_PARCELABLE);

        } else {
            loadContentFromProvider();
        }

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
        
        mFavoriteAdapter = new FavoriteAdapter(this);
        mFavoriteAdapter.setFavoriteList(mFavoriteList);

        mRvFavoriteList.setAdapter(mFavoriteAdapter);
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
    public void onFavoriteItemClick(Favorite favorite) {
        // TODO: Implement Favorite Item Click Listener
    }
}