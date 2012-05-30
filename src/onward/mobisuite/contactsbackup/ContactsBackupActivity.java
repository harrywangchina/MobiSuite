package onward.mobisuite.contactsbackup;

import onward.mobisuite.R;
import onward.mobisuite.utils.DialogActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ContactsBackupActivity extends Activity{
	private Button titleback=null;
	private RelativeLayout layout_backup=null;
	private RelativeLayout layout_restore=null;
	private RelativeLayout layout_settings=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_backup_main);
		titleback=(Button)findViewById(R.id.btn_title_back);
		layout_backup=(RelativeLayout)findViewById(R.id.layout_backup);
		layout_restore=(RelativeLayout)findViewById(R.id.layout_restore);
		layout_settings=(RelativeLayout)findViewById(R.id.layout_settings);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		layout_backup.setOnClickListener(new BackUpOnClickListener());
		layout_restore.setOnClickListener(new RestoreOnClickListener());
		layout_settings.setOnClickListener(new SettingsOnClickListener());
	}
	private class TitleBackBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
		
	}
	private class BackUpOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(ContactsBackupActivity.this,BackupContactActivity.class);
			startActivity(intent);
		}
		
	}
	private class RestoreOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(ContactsBackupActivity.this,DialogActivity.class);
			intent.putExtra("titletext", getText(R.string.restore_contacts).toString());
			intent.putExtra("messagecontent", getText(R.string.restoring_backed_up_contacts_confirm).toString());
			startActivityForResult(intent, 1);
		}
		
	}
	private class SettingsOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(ContactsBackupActivity.this,ContactsBackupSettingsActivity.class);
			startActivity(intent);
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1) {
			if (resultCode==Activity.RESULT_OK) {
				System.out.println("ok");
			}else if (resultCode==Activity.RESULT_CANCELED) {
				System.out.println("cancel");
			}
			
		}
	}
}
