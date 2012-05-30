package onward.mobisuite.utils;

import onward.mobisuite.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListTextAdapter extends BaseAdapter{
	private LayoutInflater layoutInflater=null;
	private String[] items=null;
	  public ListTextAdapter(Context context,int item){
	    	layoutInflater=LayoutInflater.from(context);
	    	this.items=context.getResources().getStringArray(item);
	    }
	  public ListTextAdapter(Context context,String[] item){
	    	layoutInflater=LayoutInflater.from(context);
	    	this.items=item;
	    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null) {
			convertView = layoutInflater.inflate(R.layout.list_text_item, null);
		}
		((TextView)convertView.findViewById(R.id.list_text)).setText(items[position]);
		return convertView;
	}

}
