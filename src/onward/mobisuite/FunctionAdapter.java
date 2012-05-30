package onward.mobisuite;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FunctionAdapter extends BaseAdapter{
	 private Context context=null;
    private int[] item=null;
    public FunctionAdapter(Context context,int[] item){
    	this.context=context;
    	this.item=item;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return item.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1==null) {
			arg1 = RelativeLayout.inflate(context,R.layout.function_item, null);
		}
		
		TextView textview=(TextView)arg1.findViewById(R.id.function_text);
		textview.setText(item[arg0]);
		return arg1;
	}

}
