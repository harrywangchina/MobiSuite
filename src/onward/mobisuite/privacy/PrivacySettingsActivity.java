package onward.mobisuite.privacy;

import onward.mobisuite.R;
import onward.mobisuite.utils.ListRadioAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PrivacySettingsActivity extends Activity{
	private Button titleback=null;
	private ListRadioAdapter listRadioAdapter=null;
	private RelativeLayout layoutselectsmsshide=null;
	private SharedPreferences sharedpreferences = null;
	private AlertDialog smsAlertDialog=null;
	private TextView selectsmsshidesummary=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.privacy_settings);
		titleback=(Button)findViewById(R.id.btn_title_back);
		layoutselectsmsshide=(RelativeLayout)findViewById(R.id.layout_select_smss_hide);
		selectsmsshidesummary=(TextView)findViewById(R.id.select_smss_hide_summary);
		SetSelectSMSsHideSummary(sharedpreferences.getInt("privacy_sms_hide_type", 1));
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		layoutselectsmsshide.setOnClickListener(new LayoutSelectSMSsHideOnClickListener());
	}
	 private class TitleBackBtnOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			finish();
	 		}
	 		
	 	}
	 private class LayoutSelectSMSsHideOnClickListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder builder = new Builder(PrivacySettingsActivity.this);
			    int callcontrol = R.array.sms_hide_type;
			    listRadioAdapter=new ListRadioAdapter(PrivacySettingsActivity.this, callcontrol,handler,1);
			    listRadioAdapter.setItemSelect(sharedpreferences.getInt("privacy_sms_hide_type", 1));
				builder.setSingleChoiceItems(listRadioAdapter, 0, new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					    // System.out.println(which);
					     sharedpreferences.edit().putInt("privacy_sms_hide_type", which).commit();
					     dialog.dismiss();
					     //listRadioAdapter.setItemSelect(which);
					    // listRadioAdapter.notifyDataSetChanged();	
					     SetSelectSMSsHideSummary(sharedpreferences.getInt("privacy_sms_hide_type", 1));     
					}
				});
				smsAlertDialog= builder.create();
				smsAlertDialog.show();
			}
		}
	 private void SetSelectSMSsHideSummary(int which){
		 switch (which) {
		case 0:
			selectsmsshidesummary.setText(R.string.disabled);
			break;
		case 1:
			selectsmsshidesummary.setText(R.string.selected_contacts);
			break;
		case 2:
			selectsmsshidesummary.setText(R.string.all_contacts);
			break;
		default:
			break;
		}
	 }
	 private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				 sharedpreferences.edit().putInt("privacy_sms_hide_type", msg.arg1).commit();
				 smsAlertDialog.dismiss();
				 SetSelectSMSsHideSummary(sharedpreferences.getInt("privacy_sms_hide_type", 1));
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		 
	 };
}
