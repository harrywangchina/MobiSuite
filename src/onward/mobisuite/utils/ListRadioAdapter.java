package onward.mobisuite.utils;

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
import android.widget.RadioButton;
import android.widget.TextView;

public class ListRadioAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater=null;
	private String[] items=null;
	private HashMap<Integer, Boolean> select=null;
	private Handler handler=null;
	private int adapterid;
	
    public ListRadioAdapter(Context context,int item,Handler handler,int adapterid){
    	layoutInflater=LayoutInflater.from(context);
    	this.items=context.getResources().getStringArray(item);
    	this.handler=handler;
    	this.adapterid=adapterid;
    	select=new HashMap<Integer, Boolean>();
    	for (int i = 0; i < items.length; i++) {
    		select.put(i, false);
		}
    }
    public ListRadioAdapter(Context context,String[] item,Handler handler,int adapterid){
    	layoutInflater=LayoutInflater.from(context);
    	this.items=item;
    	this.handler=handler;
    	this.adapterid=adapterid;
    	select=new HashMap<Integer, Boolean>();
    	for (int i = 0; i < items.length; i++) {
    		select.put(i, false);
		}
    }
    public void setItemSelect(int i){
    	if (i<items.length) {
    		setAllValue(false);
    		select.put(i, true);
		}
    }
    public void setAllValue(boolean value){
    	for (int i = 0; i < items.length; i++) {
    		select.put(i, value);
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
		ListRadioItem listRadioItem=null;
		if (convertView==null) {
			listRadioItem=new ListRadioItem();
			convertView = layoutInflater.inflate(R.layout.list_radio_item, null);
			listRadioItem.listradiotext=(TextView)convertView.findViewById(R.id.list_radio_text);
			listRadioItem.listradiobtn=(RadioButton)convertView.findViewById(R.id.list_radio_btn);
			convertView.setTag(listRadioItem);
		}else {
			listRadioItem=(ListRadioItem) convertView.getTag();
		}
		listRadioItem.listradiotext.setText(items[position]);
		listRadioItem.listradiobtn.setChecked(select.get(position));
		listRadioItem.listradiobtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setAllValue(false);
				select.put(position, true);
				notifyDataSetChanged();
				Message msg=handler.obtainMessage();
				msg.what=adapterid;
				msg.arg1=position;
				msg.sendToTarget();
			}
		});
		return convertView;
	}
  private class ListRadioItem{
	  public  TextView listradiotext;
	  public RadioButton listradiobtn;
  }
}
