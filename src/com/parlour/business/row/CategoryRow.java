package com.parlour.business.row;

import com.parlour.business.R;
import com.parlour.business.model.Category;
import com.parlour.business.presentation.wrapper.ResultItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author DEB
 *
 */
public class CategoryRow implements Row{
	private final ResultItem resultItem;
	private final LayoutInflater inflater;
	
	public CategoryRow(LayoutInflater inflater, ResultItem resultItem){
		this.inflater = inflater;
		this.resultItem = resultItem;
	}

	@Override
	public View getView(View convertView) {
		ViewHolder holder;
        View view;
        //we have a don't have a converView so we'll have to create a new one
        if (convertView == null) {
            ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.catlist_row, null);

            //The view holder pattern to save of already looked up sub views
            holder = new ViewHolder((ImageView)viewGroup.findViewById(R.id.categoryIcon),
                    (TextView)viewGroup.findViewById(R.id.catTitle));
            viewGroup.setTag(holder);

            view = viewGroup;
        } else {
            //get the holder back out
        	view = convertView;
            holder = (ViewHolder)convertView.getTag();
        }

        //setup the view now
        Category category = (Category)resultItem.getItem();
        if(category.getIconId() != -1){
        	holder.imageView.setImageResource(category.getIconId());
        }
        else {
        	if(resultItem.getItemType() == ResultItem.CATEGORY_TYPE){
        		holder.imageView.setImageResource(R.drawable.category_icon);
        	}
        	else if(resultItem.getItemType() == ResultItem.SUBCATEGORY_TYPE){
        		holder.imageView.setImageResource(R.drawable.subcat_icon);
        	}
        }
        holder.titleView.setText(category.getCategoryName());
        return view;
	}

	@Override
	public int getViewType() {
		return RowType.CATEGORY_ROW.ordinal();
	}
	
	private static class ViewHolder {
        final ImageView imageView;
        final TextView titleView;

        private ViewHolder(ImageView imageView, TextView titleView) {
            this.imageView = imageView;
            this.titleView = titleView;
        }
    }

}
