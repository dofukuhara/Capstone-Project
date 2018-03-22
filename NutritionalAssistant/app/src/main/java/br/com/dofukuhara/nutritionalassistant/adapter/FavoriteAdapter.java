package br.com.dofukuhara.nutritionalassistant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.model.Favorite;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dofukuhara on 18/03/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteListViewHolder>{

    private ArrayList<Favorite> mFavoriteList;
    private FavoriteItemClickListener mFavoriteItemClickListener;

    public FavoriteAdapter(FavoriteItemClickListener listener) {
        mFavoriteList = new ArrayList<>();
        mFavoriteItemClickListener = listener;
    }

    public void setFavoriteList(ArrayList<Favorite> favList) {
        mFavoriteList = favList;
    }

    @Override
    public FavoriteListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutIdForListItem = R.layout.favorite_list_item;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new FavoriteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mFavoriteList == null ? 0 : mFavoriteList.size();
    }

    public interface FavoriteItemClickListener {
        void onFavoriteItemClick(boolean addToFav, Favorite favorite);
    }

    class FavoriteListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_favorite_item)
        TextView tvFavoriteItem;

        @BindView(R.id.ib_delete_favorite)
        ImageButton ibDeleteFavorite;

        public FavoriteListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            ibDeleteFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFavoriteItemClickListener.onFavoriteItemClick(
                            false,
                            mFavoriteList.get(getAdapterPosition())
                    );
                }
            });
        }

        void bind(int ingredientId) {
            tvFavoriteItem.setText(mFavoriteList.get(ingredientId).getIngredientName());
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            mFavoriteItemClickListener.onFavoriteItemClick(
                    true,
                    mFavoriteList.get(itemPosition)
            );
        }

    }
}
