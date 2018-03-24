package br.com.dofukuhara.nutritionalassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.model.Category;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dofukuhara on 15/02/2018.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {

    private ArrayList<Category> mCategoryList;
    private CategoryItemClickListener mCategoryItemClickListener;

    public CategoryListAdapter(CategoryItemClickListener listener) {
        mCategoryList = new ArrayList<>();
        mCategoryItemClickListener = listener;
    }

    public void setCategoryList(ArrayList<Category> list) {
        mCategoryList = list;
    }

    @Override
    public CategoryListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context mContext = viewGroup.getContext();

        int layoutIdForListItem = R.layout.category_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new CategoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryListViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mCategoryList == null ? 0 : mCategoryList.size();
    }

    public interface CategoryItemClickListener {
        void onCategoryItemClick(Category category);
    }

    class CategoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_category_item)
        TextView tvCategoryItem;

        public CategoryListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind (int categoryItemIndex) {
            tvCategoryItem.setText(mCategoryList.get(categoryItemIndex).getCategoryName());

        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            mCategoryItemClickListener.onCategoryItemClick(mCategoryList.get(itemPosition));
        }
    }
}
