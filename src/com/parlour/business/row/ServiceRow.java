package com.parlour.business.row;

import com.parlour.business.R;
import com.parlour.business.model.Service;
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
public class ServiceRow implements Row{
	
	private final ResultItem resultItem;
	private final LayoutInflater inflater;
	
	public ServiceRow(LayoutInflater inflater, ResultItem resultItem){
		this.inflater = inflater;
		this.resultItem = resultItem;
	}

	@Override
	public View getView(View convertView) {
		ViewHolder holder;
        View view;
        //we have a don't have a converView so we'll have to create a new one
        if (convertView == null) {
            ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.servicelist_row, null);

            //The view holder pattern to save of already looked up sub views
            holder = new ViewHolder((ImageView)viewGroup.findViewById(R.id.serviceIcon),
                    (TextView)viewGroup.findViewById(R.id.serviceName),
                    (TextView)viewGroup.findViewById(R.id.serviceNotes),
                    (TextView)viewGroup.findViewById(R.id.servicePrice));
            viewGroup.setTag(holder);

            view = viewGroup;
        } else {
            //get the holder back out
            holder = (ViewHolder)convertView.getTag();

            view = convertView;
        }

        //setup the view now
        Service service = (Service)resultItem.getItem();
        if(service.getIconId() != -1){
        	holder.imageView.setImageResource(service.getIconId());
        }
        
        holder.titleView.setText(service.getServiceName());
        holder.serviceNotesView.setText(service.getServiceNote());
        holder.priceView.setText(Double.toString(service.getServicePrice()));

        return view;
	}

	@Override
	public int getViewType() {
		return RowType.SERVICE_ROW.ordinal();
	}
	
	private static class ViewHolder {
        final ImageView imageView;
        final TextView titleView;
        final TextView serviceNotesView;
        final TextView priceView;

        private ViewHolder(ImageView imageView, TextView titleView, TextView serviceNotesView, TextView priceView) {
            this.imageView = imageView;
            this.titleView = titleView;
            this.serviceNotesView = serviceNotesView;
            this.priceView = priceView;
        }
    }

}
