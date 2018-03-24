package br.com.dofukuhara.nutritionalassistant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dofukuhara on 19/03/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>{

    private ArrayList<Recipe> mRecipeList;
    private RecipeItemClickListener mRecipeClickListener;

    public RecipeListAdapter(RecipeItemClickListener listener) {
        mRecipeList = new ArrayList<>();
        mRecipeClickListener = listener;
    }

    public void setRecipeList(ArrayList<Recipe> list) {
        mRecipeList = list;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutId = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutId, viewGroup, shouldAttachToParentImmediately);

        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeList == null ? 0 : mRecipeList.size();
    }

    public interface RecipeItemClickListener {
        void onRecipeItemClick(Recipe recipe);
    }

    class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_recipe_item)
        TextView mRecipeNameItem;

        public RecipeListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(int recipeId) {
            mRecipeNameItem.setText(mRecipeList.get(recipeId).getRecipeName());
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            mRecipeClickListener.onRecipeItemClick(mRecipeList.get(itemPosition));
        }
    }
}
