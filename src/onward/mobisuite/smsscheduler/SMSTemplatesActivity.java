package onward.mobisuite.smsscheduler;

import java.util.ArrayList;
import java.util.HashMap;

import onward.mobisuite.R;
import onward.mobisuite.utils.DatabaseManager;
import onward.mobisuite.utils.DialogActivity;
import onward.mobisuite.utils.DialogInputActivity;
import onward.mobisuite.utils.ListDeleteAdapter;
import onward.mobisuite.utils.MobiSuiteDB;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SMSTemplatesActivity extends Activity{
	private Button titleback=null;
	private ArrayList<HashMap<String, String>> smsItem=null;
	private DatabaseManager databaseManager=null;
	private ListAdapter listItemAdapter=null;
	private ListView templateslist=null;
	private Button addnewtemplatesbtn=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		databaseManager=new DatabaseManager(this);
		setContentView(R.layout.sms_templates);
		titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
	    templateslist = (ListView)findViewById(R.id.templates_list);
	    addnewtemplatesbtn=(Button)findViewById(R.id.add_new_templates_btn);
	    addnewtemplatesbtn.setOnClickListener(new AddNewTemplatesOnClickListener());
	    ShowSMSTemplates();
	}
	 private class TitleBackBtnOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			finish();
	 		}
	 		
	 	}
	 private class AddNewTemplatesOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			Intent intent=new Intent(SMSTemplatesActivity.this,DialogInputActivity.class);
	 			intent.putExtra("titletext", getText(R.string.input_new_template).toString());
	 			startActivityForResult(intent, 2);
	 		}
	 		
	 	}
	 private void ShowSMSTemplates(){
		   smsItem=new ArrayList<HashMap<String, String>>();
			Cursor cursor =databaseManager.QuerySMSTemplates(null, null);
			if (cursor!=null) {
				while (cursor.moveToNext()) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("ItemID", cursor.getString(cursor
							.getColumnIndex(MobiSuiteDB.KEY_ID)));
					map.put("ItemName",cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_SMSCONTENT)));
					smsItem.add(map);
				}
				cursor.close();
			}
			if (smsItem.isEmpty()) {
				
				listItemAdapter=new ArrayAdapter<String>(this,
		                R.layout.simple_list_item, new String[]{this.getText(R.string.have_on_sms_templates).toString()});				
			}else {
				listItemAdapter=new ListDeleteAdapter(smsItem, this,handler);	
			}
			templateslist.setAdapter(listItemAdapter);
		}  
	 private Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 1:
					Intent intent1=new Intent(SMSTemplatesActivity.this,DialogActivity.class);
					intent1.putExtra("titletext", getText(R.string.delete).toString());
					intent1.putExtra("messagecontent", getText(R.string.delete_the_selected_template).toString());
					intent1.putExtra("data", smsItem.get(msg.arg1).get("ItemID"));
					startActivityForResult(intent1, 1);					
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
			};
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				// TODO Auto-generated method stub
				super.onActivityResult(requestCode, resultCode, data);
				if (resultCode==Activity.RESULT_OK) {
					switch (requestCode) {
					case 1:
						databaseManager.DeleteSMSTemplates(data.getStringExtra("data"));
						ShowSMSTemplates();
						break;
					case 2:
						databaseManager.InsertSMSTemplates(data.getStringExtra("inputcontent"));
						ShowSMSTemplates();
						break;
					}
				}
				
			}
			public void finish() {
				// TODO Auto-generated method stub
				if (databaseManager != null) {
					databaseManager.close();
					databaseManager = null;
				}
				super.finish();
			}
}
