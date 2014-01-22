package com.parlour.business.adapter;

import java.util.ArrayList;
import java.util.List;
import com.parlour.business.presentation.wrapper.ResultItem;
import com.parlour.business.row.CategoryRow;
import com.parlour.business.row.Row;
import com.parlour.business.row.RowType;
import com.parlour.business.row.ServiceRow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class ListAdapter extends BaseAdapter {
	List<ResultItem> itemList;
	List<Row> rows;
	
	public ListAdapter(Context context, List<ResultItem> itemList){
		this.itemList = itemList;
		rows = new ArrayList<Row>();//member variable
		
		for (ResultItem item : itemList) {
            //if service type, use ServiceRow
            if (item.getItemType() == ResultItem.CATEGORY_TYPE || 
            		item.getItemType() == ResultItem.SUBCATEGORY_TYPE) {
            	rows.add(new CategoryRow((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), item));
            	
            } else {//otherwise use CategoryRow
            	rows.add(new ServiceRow((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), item));
            }
        }
	}
	@Override
    public int getViewTypeCount() {
		return RowType.values().length;
    }
	
	@Override
    public int getItemViewType(int position) {
        return rows.get(position).getViewType();
    }

	public int getCount() {
		return rows.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		return rows.get(position).getView(convertView);
	}

}
