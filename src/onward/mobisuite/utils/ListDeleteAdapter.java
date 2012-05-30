package onward.mobisuite.utils;

import java.util.ArrayList;
import java.util.HashMap;

import onward.mobisuite.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListDeleteAdapter extends BaseAdapter{
	private ArrayList<HashMap<String, String>> data=null;
    private LayoutInflater layoutInflater=null;
    private Handler handler=null;

    public ListDeleteAdapter(ArrayList<HashMap<String, String>> data,Context context,Handler handler) {
		// TODO Auto-generated constructor stub
    	this.data=data;
    	this.handler=handler;
    	layoutInflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DeleteListItem deleteListItem=null;
		if (convertView==null) {
			deleteListItem=new DeleteListItem();
			convertView = layoutInflater.inflate(R.layout.delete_list_item, null);
			deleteListItem.list_name=(TextView) convertView.findViewById(R.id.item_name);
			deleteListItem.list_summ=(TextView) convertView.findViewById(R.id.item_summary);
			deleteListItem.delete_image=(ImageView) convertView.findViewById(R.id.imageview_right);
			convertView.setTag(deleteListItem);
		}else {
			deleteListItem=(DeleteListItem) convertView.getTag();
		}
		deleteListItem.list_name.setText(data.get(position).get("ItemName"));
		String ItemSumm=data.get(position).get("ItemSumm");
		if (ItemSumm==null) {
			deleteListItem.list_summ.setVisibility(View.GONE);
		}else {
			deleteListItem.list_summ.setText(data.get(position).get("ItemSumm"));
		}
		deleteListItem.delete_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message msg=handler.obtainMessage();
				msg.arg1=position;
				msg.what=1;
				msg.sendToTarget();
			}
		});
		return convertView;
	}
	public class DeleteListItem{
		public TextView list_name;
		public TextView list_summ;
		public ImageView delete_image; 
	}
}
