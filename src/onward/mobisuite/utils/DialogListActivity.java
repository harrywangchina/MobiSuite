package onward.mobisuite.utils;

import onward.mobisuite.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DialogListActivity extends Activity {
	private TextView dialogtitletxt = null;
	private Button buttonok = null;
	private Button buttoncancel = null;
	private ListView dialog_list=null;
	private ListCheckBoxAdapter listCheckBoxAdapter=null;
	private String data=null;
	private String[] listitems=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_list);
		Intent intent = getIntent();
		dialogtitletxt = (TextView) findViewById(R.id.dialog_title_txt);
		dialog_list=(ListView)findViewById(R.id.dialog_list);
		buttonok = (Button) findViewById(R.id.button_ok);
		buttoncancel = (Button) findViewById(R.id.button_cancel);
		dialogtitletxt.setText(intent.getStringExtra("titletext"));
		data=intent.getStringExtra("data");
		listitems= intent.getStringArrayExtra("listitems");
	    listCheckBoxAdapter=new ListCheckBoxAdapter(listitems, this);
		dialog_list.setAdapter(listCheckBoxAdapter);
		dialog_list.setOnItemClickListener(new ListOnItemClickListener());
		buttonok.setOnClickListener(new OKOnClickListener());
		buttoncancel.setOnClickListener(new CancelOnClickListener());
	}
	private class ListOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(listCheckBoxAdapter.isSelect.get(arg2)){
				listCheckBoxAdapter.isSelect.put(arg2, false);
			}else {
				listCheckBoxAdapter.isSelect.put(arg2, true);
			}
			listCheckBoxAdapter.notifyDataSetChanged();
		}
		
	}
	private class OKOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		    Intent intent=new Intent();
		    intent.putExtra("isSelect", listCheckBoxAdapter.isSelect);
		    intent.putExtra("data", data);
		    intent.putExtra("listitems", listitems);
             setResult(Activity.RESULT_OK,intent);
             finish();
		}
	}
	private class CancelOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
             setResult(Activity.RESULT_CANCELED);
             finish();
		}
	}
}
