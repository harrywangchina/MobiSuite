package onward.mobisuite.contactsbackup;

import onward.mobisuite.R;
import onward.mobisuite.utils.DialogActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

public class ContactsBackupSettingsActivity extends Activity {
	private Button titleback=null;
	private CheckBox checkboxbackupreminder=null;
	private SharedPreferences sharedpreferences = null;
	private RelativeLayout layoutbackupreminder=null;
	private RelativeLayout layoutresetstate=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.contacts_backup_settings);
		titleback=(Button)findViewById(R.id.btn_title_back);
		checkboxbackupreminder=(CheckBox)findViewById(R.id.checkbox_backup_reminder);
		layoutbackupreminder = (RelativeLayout)findViewById(R.id.layout_backup_reminder);
		layoutresetstate=(RelativeLayout)findViewById(R.id.layout_reset_state);
		checkboxbackupreminder.setChecked( sharedpreferences.getBoolean("ContactNotifyReminder", false));
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		layoutbackupreminder.setOnClickListener(new LayoutBackupReminderOnClickListener());
		layoutresetstate.setOnClickListener(new LayoutResetStateOnClickListener());
	}
	private class TitleBackBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
		
	}
	private class LayoutBackupReminderOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			checkboxbackupreminder.setChecked(!checkboxbackupreminder.isChecked());
		}
		
	}
	private class LayoutResetStateOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(ContactsBackupSettingsActivity.this,DialogActivity.class);
			intent.putExtra("titletext", getText(R.string.app_name).toString());
			intent.putExtra("messagecontent", getText(R.string.mobisuite_state_reset).toString());
			intent.putExtra("buttonshow", 1);
			startActivity(intent);
		}
		
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sharedpreferences.edit()
		.putBoolean("ContactNotifyReminder", checkboxbackupreminder.isChecked())
		.commit();
	}
}
