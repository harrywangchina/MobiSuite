package onward.mobisuite.setting;

import onward.mobisuite.R;
import onward.mobisuite.utils.ListRadioAdapter;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

public class SettingsActivity extends Activity {
	private Button titleback=null;
	private RelativeLayout layoutsmartsensor;
	private RelativeLayout layoutbackupreminder;
	private RelativeLayout layoutscreenkeypad;
	private RelativeLayout layoutsettingspassword;
	private RelativeLayout layoutcallmode;
	private RelativeLayout layoutscanapplications;
	private RelativeLayout layoutscanfiles;
	private RelativeLayout layoutscanonmount;
	private RelativeLayout layoutshowicon;
	private RelativeLayout layoutstartautomatically;
	private CheckBox checkboxrejectsensor;
	private CheckBox checkboxbackupreminder;
	private CheckBox checkboxscreenkeypad;
	private CheckBox checkboxscanapplications;
	private CheckBox checkboxscanfiles;
	private CheckBox checkboxscanonmount;
	private CheckBox checkboxshowicon;
	private CheckBox checkboxstartautomatically;
	private SharedPreferences sharedpreferences = null;
	private ListRadioAdapter listRadioAdapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
		titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		
		checkboxrejectsensor=(CheckBox)findViewById(R.id.checkbox_reject_sensor);
		checkboxbackupreminder=(CheckBox)findViewById(R.id.checkbox_backup_reminder);
		checkboxscreenkeypad=(CheckBox)findViewById(R.id.checkbox_screen_keypad);
		checkboxscanapplications=(CheckBox)findViewById(R.id.checkbox_scan_applications);
		checkboxscanfiles=(CheckBox)findViewById(R.id.checkbox_scan_files);
		checkboxscanonmount=(CheckBox)findViewById(R.id.checkbox_scan_on_mount);
		checkboxshowicon=(CheckBox)findViewById(R.id.checkbox_show_icon);
		checkboxstartautomatically=(CheckBox)findViewById(R.id.checkbox_start_automatically);
		
		 layoutsmartsensor = (RelativeLayout)findViewById(R.id.layout_smart_sensor);
		 layoutbackupreminder = (RelativeLayout)findViewById(R.id.layout_backup_reminder);
		 layoutscreenkeypad = (RelativeLayout)findViewById(R.id.layout_screen_keypad);
		 layoutsettingspassword = (RelativeLayout)findViewById(R.id.layout_settings_password);
		 layoutcallmode = (RelativeLayout)findViewById(R.id.layout_call_mode);
		 layoutscanapplications = (RelativeLayout)findViewById(R.id.layout_scan_applications);
		 layoutscanfiles = (RelativeLayout)findViewById(R.id.layout_scan_files);
		 layoutscanonmount = (RelativeLayout)findViewById(R.id.layout_scan_on_mount);
		 layoutshowicon = (RelativeLayout)findViewById(R.id.layout_show_icon);
		 layoutstartautomatically = (RelativeLayout)findViewById(R.id.layout_start_automatically);
		 
		 
		 checkboxrejectsensor.setChecked( sharedpreferences.getBoolean("smart_sensor_able", true));
		 checkboxbackupreminder.setChecked( sharedpreferences.getBoolean("ContactNotifyReminder", false));
		 checkboxscreenkeypad.setChecked( sharedpreferences.getBoolean("screen_keypad_able", true));
		 checkboxscanapplications.setChecked( sharedpreferences.getBoolean("prefScanApp", true));
		 checkboxscanfiles.setChecked( sharedpreferences.getBoolean("prefScanFiles", true));
		 checkboxscanonmount.setChecked( sharedpreferences.getBoolean("prefScanMount", false));
		 checkboxshowicon.setChecked( sharedpreferences.getBoolean("prefOthersIcon", true));
		 checkboxstartautomatically.setChecked( sharedpreferences.getBoolean("prefBootStart", false));
		 
		 layoutsmartsensor.setOnClickListener(new LayoutSmartSensorOnClickListener());
		 layoutbackupreminder.setOnClickListener(new LayoutBackupReminderOnClickListener());
		 layoutscreenkeypad.setOnClickListener(new LayoutScreenKeypadOnClickListener());
		 layoutcallmode.setOnClickListener(new LayoutCallModeOnClickListener());
		 layoutsettingspassword.setOnClickListener(new LayoutSettingsPasswordOnClickListener());
		 layoutscanapplications.setOnClickListener(new LayoutScanApplicationsOnClickListener());
		 layoutscanfiles.setOnClickListener(new LayoutScanFilesOnClickListener());
		 layoutscanonmount.setOnClickListener(new LayoutScanOnMountOnClickListener());
		 layoutshowicon.setOnClickListener(new LayoutShowIconOnClickListener());
		 layoutstartautomatically.setOnClickListener(new LayoutStartAutomaticallyOnClickListener());
	}
	private class TitleBackBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SettingsActivity.this.finish();
		}
		
	}
	private class LayoutSmartSensorOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				checkboxrejectsensor.setChecked(!checkboxrejectsensor.isChecked());
		}
		
	}
	private class LayoutBackupReminderOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			checkboxbackupreminder.setChecked(!checkboxbackupreminder.isChecked());
		}
		
	}
	private class LayoutScreenKeypadOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			checkboxscreenkeypad.setChecked(!checkboxscreenkeypad.isChecked());
		}
		
	}
	private class LayoutCallModeOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Builder builder = new Builder(SettingsActivity.this);
			//builder.setTitle("fdsfdsf");
			//builder.setIcon(android.R.drawable.ic_dialog_info);
		    int callcontrol = R.array.call_control;
		    listRadioAdapter=new ListRadioAdapter(SettingsActivity.this, callcontrol,new Handler(),1);
			builder.setSingleChoiceItems(listRadioAdapter, 0, new DialogInterface.OnClickListener() {					
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				     System.out.println(which);
				     listRadioAdapter.setItemSelect(which);
				     listRadioAdapter.notifyDataSetChanged();			
					// dialog.dismiss();
				}
			});
			/*builder.setAdapter(new ListRadioAdapter(SettingsActivity.this, callcontrol), new DialogInterface.OnClickListener() {					
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				     System.out.println(which);
								
					// dialog.dismiss();
				}
			});*/
			builder.create().show();
		}
		
	}
	private class LayoutSettingsPasswordOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(SettingsActivity.this,SettingsPasswordActivity.class);
			SettingsActivity.this.startActivity(intent);
		}
		
	}
	private class LayoutScanApplicationsOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			checkboxscanapplications.setChecked(!checkboxscanapplications.isChecked());
		}
		
	}
	private class LayoutScanFilesOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			checkboxscanfiles.setChecked(!checkboxscanfiles.isChecked());
		}
		
	}
	private class LayoutScanOnMountOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			checkboxscanonmount.setChecked(!checkboxscanonmount.isChecked());
		}
		
	}
	private class LayoutShowIconOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			checkboxshowicon.setChecked(!checkboxshowicon.isChecked());
		}
		
	}
	private class LayoutStartAutomaticallyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			checkboxstartautomatically.setChecked(!checkboxstartautomatically.isChecked());
		}
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sharedpreferences.edit().putBoolean("smart_sensor_able", checkboxrejectsensor.isChecked())
		.putBoolean("ContactNotifyReminder", checkboxbackupreminder.isChecked())
		.putBoolean("screen_keypad_able", checkboxscreenkeypad.isChecked())
		.putBoolean("prefScanApp", checkboxscanapplications.isChecked())
		.putBoolean("prefScanFiles", checkboxscanfiles.isChecked())
		.putBoolean("prefScanMount", checkboxscanonmount.isChecked())
		.putBoolean("prefOthersIcon", checkboxshowicon.isChecked())
		.putBoolean("prefBootStart", checkboxstartautomatically.isChecked())	
		.commit();
	}
}
