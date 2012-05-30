package onward.mobisuite.callfilter;

import onward.mobisuite.R;
import onward.mobisuite.utils.DatabaseManager;
import onward.mobisuite.utils.DialogActivity;
import onward.mobisuite.utils.ListRadioAdapter;
import onward.mobisuite.utils.MobiSuiteDB;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddNewFilterListActivity extends Activity {
	private Button titleback=null;
	private TextView textviewtoptitle=null;
	private String listid=null;
	private Button submit=null;
	private EditText listnameedittext=null;
	private String listname="";
	private String listtype="";
	private String listaction="";
	private String smscontent="";
	private RelativeLayout layoutlistaction=null;
	private TextView listactiontext=null;
	private EditText smscontentedittext=null;
	private ListRadioAdapter filteractionAdapter=null;
	private AlertDialog filteractionDialog=null;
	private DatabaseManager databaseManager=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_filter_list);
		databaseManager=new DatabaseManager(this);
		titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		textviewtoptitle=(TextView)findViewById(R.id.textview_top_title);
		listnameedittext=(EditText)findViewById(R.id.list_name_edittext);
		layoutlistaction=(RelativeLayout)findViewById(R.id.layout_list_action);
		listactiontext=(TextView)findViewById(R.id.list_action_text);
		smscontentedittext=(EditText)findViewById(R.id.sms_content_edittext);
		submit=(Button)findViewById(R.id.submit_btn);
		submit.setOnClickListener(new SubmitBtnOnClickListener());
		layoutlistaction.setOnClickListener(new LayoutListActionOnClickListener());
		Intent intent=getIntent();
		listid=intent.getStringExtra("listid");
		if (listid==null) {
			listtype=intent.getStringExtra("listtype");
		}else {
			textviewtoptitle.setText(R.string.edit_filter_list);
			Cursor cursor=databaseManager.QueryCallFilterList(MobiSuiteDB.KEY_ID+"=?", new String[]{listid});
			if (cursor.moveToFirst()) {
				listname=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTNAME));
				listtype=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTTYPE));
				listaction=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTACTION));
				smscontent=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTSMS));
				invalidate();
			}
			cursor.close();
		}
	}
	
	private void invalidate(){
		listnameedittext.setText(listname);
		listactiontext.setText(listaction);
		smscontentedittext.setText(smscontent);
	}
	private class TitleBackBtnOnClickListener implements OnClickListener{

 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			finish();
 		}
 	}
	private class LayoutListActionOnClickListener implements OnClickListener{

 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			Builder builder = new Builder(AddNewFilterListActivity.this);
		    int filteraction = R.array.filter_action;
		    filteractionAdapter = new ListRadioAdapter(AddNewFilterListActivity.this, filteraction,handler,1);
		    if (listaction.equals("")||listaction.equals(getText(R.string.reject).toString())) {
			    filteractionAdapter.setItemSelect(0);
			}else {
				 filteractionAdapter.setItemSelect(1);
			}
			builder.setSingleChoiceItems(filteractionAdapter, 0, new DialogInterface.OnClickListener() {					
				public void onClick(DialogInterface dialog, int which) {
					setlistaction(which);
				     dialog.dismiss();
				}
			});
		    filteractionDialog = builder.create();
			filteractionDialog.show();
 		}
 	}
	private void setlistaction(int which){
		if (which==0) {
			listactiontext.setText(R.string.reject);
		}else {
			listactiontext.setText(R.string.reject_with_sms);
		}
		listaction=listactiontext.getText().toString();
	}
	 private Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 1:
					setlistaction(msg.arg1);
					filteractionDialog.dismiss(); 
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
	    	 
	     };
	private class SubmitBtnOnClickListener implements OnClickListener{

 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			checklist();
 		}
 		
 	}
	private void checklist(){
		listname=listnameedittext.getText().toString().trim();
		smscontent=smscontentedittext.getText().toString();
		listaction=listactiontext.getText().toString();
		if ("".equals(listname)) {
			Intent intent=new Intent(this,DialogActivity.class);
			intent.putExtra("titletext", getText(R.string.information).toString());
			intent.putExtra("messagecontent",getText(R.string.please_input_list_name).toString());
			intent.putExtra("buttonshow", 1);
			startActivity(intent);
		}else {
			Cursor cursor=databaseManager.QueryCallFilterList(MobiSuiteDB.KEY_LISTNAME+"=? and "+MobiSuiteDB.KEY_LISTTYPE+"=?", new String[]{listname,listtype});
		   if (cursor.moveToFirst()) {
			if (cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_ID)).equals(listid)) {
				ContentValues contentValues=new ContentValues();
				contentValues.put(MobiSuiteDB.KEY_LISTNAME, listname);
				contentValues.put(MobiSuiteDB.KEY_LISTACTION, listaction);
				contentValues.put(MobiSuiteDB.KEY_LISTSMS, smscontent);
				databaseManager.UpdateCallFilterList(contentValues, MobiSuiteDB.KEY_ID+"=?", new String[]{listid});
				cursor.close();
				setResult(2);
				finish();
			}else {
				Intent intent=new Intent(this,DialogActivity.class);
				intent.putExtra("titletext", getText(R.string.information).toString());
				intent.putExtra("messagecontent",getText(R.string.the_list_name_is_exit).toString());
				intent.putExtra("buttonshow", 1);
				cursor.close();
				startActivity(intent);
			}
		   }else {
			   if (listid==null) {
				   databaseManager.InsertCallFilterList(listname, listtype, listaction, smscontent);
			   }else {
				   ContentValues contentValues=new ContentValues();
					contentValues.put(MobiSuiteDB.KEY_LISTNAME, listname);
					contentValues.put(MobiSuiteDB.KEY_LISTACTION, listaction);
					contentValues.put(MobiSuiteDB.KEY_LISTSMS, smscontent);
					databaseManager.UpdateCallFilterList(contentValues, MobiSuiteDB.KEY_ID+"=?", new String[]{listid});
			   }
			   cursor.close(); 
			   setResult(1);
			   finish();
		   }
		   
		}
		
	}
	public void finish() {
		// TODO Auto-generated method stub
		if (databaseManager!=null) {
			databaseManager.close();
			databaseManager=null;
		}
		super.finish();
	}
}
