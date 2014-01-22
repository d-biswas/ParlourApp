package com.parlour.business.row;

import android.view.View;

/**
 * 
 * @author DEB
 *
 */
public interface Row {
	public View getView(View convertView);
    public int getViewType();
}
