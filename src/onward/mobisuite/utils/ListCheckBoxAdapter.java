package onward.mobisuite.utils;

import java.util.HashMap;
import onward.mobisuite.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListCheckBoxAdapter extends BaseAdapter{
	private String[] items=null;
    private LayoutInflater layoutInflater=null;
    public HashMap<Integer, Boolean> isSelect=null;
    public ListCheckBoxAdapter(String[] items,Context context) {
		// TODO Auto-generated constructor stub
    	this.items=items;
    	layoutInflater=LayoutInflater.from(context);
    	isSelect=new HashMap<Integer, Boolean>();
    	for (int i = 0; i < items.length; i++) {
    		isSelect.put(i, false);
		}
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListCheckboxItem listCheckboxItem=null;
		if (convertView==null) {
			listCheckboxItem=new ListCheckboxItem();
			convertView = layoutInflater.inflate(R.layout.list_checkbox_item, null);
			listCheckboxItem.list_check=(CheckBox) convertView.findViewById(R.id.list_check);
			listCheckboxItem.list_name=(TextView)convertView.findViewById(R.id.list_name);
			convertView.setTag(listCheckboxItem);
		}else {
			listCheckboxItem=(ListCheckboxItem) convertView.getTag();
		}
		listCheckboxItem.list_name.setText(items[position]);
		listCheckboxItem.list_check.setChecked(isSelect.get(position));
		listCheckboxItem.list_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isSelect.get(position)) {
					isSelect.put(position, false);
				}else {
					isSelect.put(position, true);
				}
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	public class ListCheckboxItem {
		public TextView list_name;
		public CheckBox list_check;		
	}
}
