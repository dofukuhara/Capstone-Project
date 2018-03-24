package br.com.dofukuhara.nutritionalassistant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.model.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dofukuhara on 24/03/2018.
 */

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailsViewHolder> {

    private ArrayList<Ingredient> mIngredientList;
    private IngredientItemClickListener mIngredClickListener;

    public RecipeDetailsAdapter(IngredientItemClickListener listener) {
        mIngredientList = new ArrayList<>();
        mIngredClickListener = listener;
    }

    public void setIngredientList(ArrayList<Ingredient> list) {
        this.mIngredientList = list;
    }

    public interface IngredientItemClickListener {
        void onIngredientItemClick(boolean toDelete, Ingredient ingredient);
    }

    @Override
    public RecipeDetailsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutId = R.layout.ingredients_list_item;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutId, viewGroup, shouldAttachToParentImmediately);

        return new RecipeDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeDetailsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mIngredientList == null ? 1 : mIngredientList.size() + 1;
    }

    class RecipeDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_ingredient_item)
        TextView mTvIngredItem;

        @BindView(R.id.ib_delete_ingredient)
        ImageButton mIbDeleteIngredient;

        public RecipeDetailsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(final int ingredientId) {
            if (getAdapterPosition() == getItemCount() -1) {
                mTvIngredItem.setText(R.string.add_new_ingredient);
                mIbDeleteIngredient.setVisibility(View.GONE);
            } else {
                mTvIngredItem.setText(mIngredientList.get(ingredientId).getDescription());
                mIbDeleteIngredient.setVisibility(View.VISIBLE);

                mIbDeleteIngredient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mIngredClickListener.onIngredientItemClick(true,
                                mIngredientList.get(ingredientId));
                    }
                });
            }
        }

        @Override
        public void onClick(View view) {
            int itemId = getAdapterPosition();

            if (itemId != getItemCount() -1) {
                mIngredClickListener.onIngredientItemClick(false, mIngredientList.get(itemId));
            } else {
                mIngredClickListener.onIngredientItemClick(false, null);
            }
        }
    }
}
