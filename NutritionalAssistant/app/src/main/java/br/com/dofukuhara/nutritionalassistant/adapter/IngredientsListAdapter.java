package br.com.dofukuhara.nutritionalassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.model.Ingredient;

/**
 * Created by dofukuhara on 17/02/2018.
 */

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsListViewHolder>{

    private ArrayList<Ingredient> mIngredientsList;
    private IngredientItemClickListener mIngredientItemClickListener;
    private Context mContext;

    public IngredientsListAdapter(IngredientItemClickListener listener) {
        mIngredientsList = new ArrayList<>();
        mIngredientItemClickListener = listener;
    }

    public void setIngredientsList(ArrayList<Ingredient> list) {
        mIngredientsList = list;
    }

    @Override
    public IngredientsListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();

        int layoutIdForListItem = R.layout.ingredients_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new IngredientsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mIngredientsList == null ? 0 : mIngredientsList.size();
    }

    public interface IngredientItemClickListener {
        void onIngredientItemClick(Ingredient ingredient);
    }

    class IngredientsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvIngredientName;
        public IngredientsListViewHolder(View itemView) {
            super(itemView);

            tvIngredientName = itemView.findViewById(R.id.tv_ingredient_item);

            itemView.setOnClickListener(this);
        }

        void bind(int ingredientItemIndex) {
            tvIngredientName.setText(mIngredientsList.get(ingredientItemIndex).getDescription());
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            mIngredientItemClickListener.onIngredientItemClick(mIngredientsList.get(itemPosition));
        }
    }
}
