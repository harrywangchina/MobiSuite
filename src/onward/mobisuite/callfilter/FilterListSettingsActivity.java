package onward.mobisuite.callfilter;

import java.util.ArrayList;
import java.util.HashMap;
import onward.mobisuite.R;
import onward.mobisuite.utils.DatabaseManager;
import onward.mobisuite.utils.DefultValues;
import onward.mobisuite.utils.DialogActivity;
import onward.mobisuite.utils.ListRadioAdapter;
import onward.mobisuite.utils.MobiSuiteDB;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FilterListSettingsActivity extends Activity{
	private Button titleback=null;
	private RelativeLayout layoutincomingcontrol=null;
	private RelativeLayout layoutactivelist=null;
	private ListRadioAdapter callcontrolAdapter=null;
	private SharedPreferences sharedpreferences = null;
	private AlertDialog callcontrolDialog=null;
	private AlertDialog calllistDialog=null;
	private TextView incomingcontrolsummary=null;
	private TextView activelisttextsummary=null;
	private DatabaseManager databaseManager=null;
	private ArrayList<HashMap<String, String>> listArrayList=null;
	private int lastincomingcallcontrol;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.filter_list_settings);
    	databaseManager=new DatabaseManager(this);
    	sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	lastincomingcallcontrol=sharedpreferences.getInt("incoming_call_control", DefultValues.INCOMING_CALL_MODE);
    	titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		layoutincomingcontrol=(RelativeLayout)findViewById(R.id.layout_incoming_control);
		incomingcontrolsummary=(TextView)findViewById(R.id.incoming_control_text_summary);
		layoutactivelist=(RelativeLayout)findViewById(R.id.layout_active_list);
		activelisttextsummary=(TextView)findViewById(R.id.active_list_text_summary);
		layoutincomingcontrol.setOnClickListener(new LayoutIncomingControlOnClickListener());
		layoutactivelist.setOnClickListener(new LayoutActiveListOnClickListener());
		 SetIncomingCallControlSummary(lastincomingcallcontrol);
		 invalifilterlistdata();	 
    }
     private void invalifilterlistdata(){
    	 String listid=sharedpreferences.getString("incoming_call_list","");
    	 if (listid.equals("")) {
    		 activelisttextsummary.setText(R.string.choose_the_list);
		}else {
			Cursor cursor=databaseManager.QueryCallFilterList(MobiSuiteDB.KEY_ID+"=?", new String[]{listid});
			if (cursor.moveToFirst()) {
				activelisttextsummary.setText(cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTNAME)));
			}else {
				 activelisttextsummary.setText(R.string.choose_the_list);
			}
			cursor.close();
		}
    	 
     }
     private class TitleBackBtnOnClickListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		}
     private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				 sharedpreferences.edit().putInt("incoming_call_control", msg.arg1).commit();
				 callcontrolDialog.dismiss();
				 SetIncomingCallControlSummary(sharedpreferences.getInt("incoming_call_control", DefultValues.INCOMING_CALL_MODE));
				break;
			case 2:
				SetIncomingCallListSummary(msg.arg1);
				calllistDialog.dismiss();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
    	 
     };
     private void SetIncomingCallControlSummary(int what){
    	 switch (what) {
		case 0:
			incomingcontrolsummary.setText(R.string.off);
			layoutactivelist.setClickable(false);
			break;
		case 1:
			incomingcontrolsummary.setText(R.string.whitelist_mode);
			layoutactivelist.setClickable(true);
			break;
		case 2:
			incomingcontrolsummary.setText(R.string.blacklist_mode);
			layoutactivelist.setClickable(true);
			break;
		default:
			break;
		}
    	 if (lastincomingcallcontrol!=what) {
    		 sharedpreferences.edit().putString("incoming_call_list","").commit();
        	 activelisttextsummary.setText(R.string.choose_the_list);
		}
     }
     private class LayoutIncomingControlOnClickListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder builder = new Builder(FilterListSettingsActivity.this);
			    int callcontrol = R.array.call_control;
			    callcontrolAdapter=new ListRadioAdapter(FilterListSettingsActivity.this, callcontrol,handler,1);
			    callcontrolAdapter.setItemSelect(sharedpreferences.getInt("incoming_call_control", DefultValues.INCOMING_CALL_MODE));
				builder.setSingleChoiceItems(callcontrolAdapter, 0, new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
					     sharedpreferences.edit().putInt("incoming_call_control", which).commit();
					     dialog.dismiss();
					     SetIncomingCallControlSummary(sharedpreferences.getInt("incoming_call_control", DefultValues.INCOMING_CALL_MODE));
					}
				});
				callcontrolDialog= builder.create();
				callcontrolDialog.show();
			}
		}
     private class LayoutActiveListOnClickListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFilterlist();
			}
		}
     private void showFilterlist(){
    	 String listtype="white";
    	if(sharedpreferences.getInt("incoming_call_control", DefultValues.INCOMING_CALL_MODE)==2){
    		listtype="black";
    	}
    	Cursor cursor=databaseManager.QueryCallFilterList(MobiSuiteDB.KEY_LISTTYPE+"=?", new String[]{listtype});
    	if (cursor.getCount()>0) {
    		listArrayList=new ArrayList<HashMap<String, String>>();
    		String[] listitem=new String[cursor.getCount()];
    		int i=0;
			while (cursor.moveToNext()) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ItemID", cursor.getString(cursor
						.getColumnIndex(MobiSuiteDB.KEY_ID)));
				listitem[i]=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTNAME));
				map.put("ItemName",listitem[i]);
				listArrayList.add(map);
				i++;
			}
			ListRadioAdapter fliterlistAdapter = new ListRadioAdapter(this, listitem,handler,2);
			Builder builder = new Builder(this);
			builder.setSingleChoiceItems(fliterlistAdapter, 0, new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						SetIncomingCallListSummary(which);
					     dialog.dismiss();
					     
					}
				});
			calllistDialog= builder.create();
			calllistDialog.show();
    	}else {
			Intent intent1=new Intent(this,DialogActivity.class);
			intent1.putExtra("titletext", getText(R.string.call_filter).toString());
			intent1.putExtra("messagecontent", getText(R.string.have_no)+" "+incomingcontrolsummary.getText()+", "+getText(R.string.you_can_add_filter_list));
			intent1.putExtra("buttonshow", 1);
			startActivity(intent1);
		}
    	cursor.close();
     }
     private void SetIncomingCallListSummary(int which){
    	 sharedpreferences.edit().putString("incoming_call_list",listArrayList.get(which).get("ItemID")).commit();
    	 activelisttextsummary.setText(listArrayList.get(which).get("ItemName"));
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
