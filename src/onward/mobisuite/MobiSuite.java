package onward.mobisuite;

import onward.mobisuite.antimalware.AntiMalwareActivity;
import onward.mobisuite.antitheft.AntiTheftActivity;
import onward.mobisuite.antivirus.AntiVirusActivity;
import onward.mobisuite.callfilter.CallFilterActivity;
import onward.mobisuite.contactsbackup.ContactsBackupActivity;
import onward.mobisuite.privacy.PrivacyActivity;
import onward.mobisuite.setting.SettingsActivity;
import onward.mobisuite.smsscheduler.SMSSchedulerActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MobiSuite extends Activity {
	private GridView functionGridView;
    private int[] item={
	    		R.string.anti_virus,
	    		R.string.anti_malware,
	    		R.string.anti_theft,
	    		R.string.contacts_backup,
	    		R.string.privacy,
	    		R.string.call_filter,
	    		R.string.sms_scheduler,
	    		R.string.settings,
	    		R.string.help,
	    		R.string.about_us };
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		setInterface();
		/*functionGridView=(GridView) findViewById(R.id.function_grid);
		functionGridView.setAdapter(new FunctionAdapter(this,item));
		functionGridView.setOnItemClickListener(new FunGridViewOnItemClickListener());*/
	}
   private class FunGridViewOnItemClickListener implements OnItemClickListener{

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch (item[arg2]) {
		case R.string.anti_virus:
			Intent AntiVirusintent=new Intent(MobiSuite.this,AntiVirusActivity.class);
			MobiSuite.this.startActivity(AntiVirusintent);
			break;
		case R.string.anti_malware:
			Intent AntiMalwareintent=new Intent(MobiSuite.this,AntiMalwareActivity.class);
			MobiSuite.this.startActivity(AntiMalwareintent);
			break;
		case R.string.anti_theft:
			Intent AntiTheftintent=new Intent(MobiSuite.this,AntiTheftActivity.class);
			MobiSuite.this.startActivity(AntiTheftintent);
			break;
		case R.string.contacts_backup:
			Intent ContactsBackupintent=new Intent(MobiSuite.this,ContactsBackupActivity.class);
			MobiSuite.this.startActivity(ContactsBackupintent);
			break;
		case R.string.privacy:
			Intent Privacyintent=new Intent(MobiSuite.this,PrivacyActivity.class);
			MobiSuite.this.startActivity(Privacyintent);
			break;
		case R.string.call_filter:
			Intent CallFilterintent=new Intent(MobiSuite.this,CallFilterActivity.class);
			MobiSuite.this.startActivity(CallFilterintent);
			break;
		case R.string.sms_scheduler:
			Intent SMSSchedulerintent=new Intent(MobiSuite.this,SMSSchedulerActivity.class);
			MobiSuite.this.startActivity(SMSSchedulerintent);
			break;
		case R.string.settings:
			Intent Settingsintent=new Intent(MobiSuite.this,SettingsActivity.class);
			MobiSuite.this.startActivity(Settingsintent);
			break;
		case R.string.help:
			Intent Helpintent=new Intent(MobiSuite.this,HelpActivity.class);
			MobiSuite.this.startActivity(Helpintent);
			break;
		case R.string.about_us:
			Intent AboutUsintent=new Intent(MobiSuite.this,AboutUsActivity.class);
			MobiSuite.this.startActivity(AboutUsintent);
			break;
		default:
			break;
		}
	}
	   
   }
   
   void setInterface(){
	   MobiSuiteGlobal.initialize(this);
	   MobiSuiteGlobal.Registered="0";
	   MobiSuiteGlobal.savePrefereces(this);
   	if ("1".equals(MobiSuiteGlobal.Registered))
   	{
   		setContentView(R.layout.main);
   		functionGridView=(GridView) findViewById(R.id.function_grid);
		functionGridView.setAdapter(new FunctionAdapter(this,item));
		functionGridView.setOnItemClickListener(new FunGridViewOnItemClickListener());
   		new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					/*Intent intent=new Intent(MobiAnti.this, ScanService.class);
					MobiAnti.this.startService(intent);
		            
		            intent=new Intent(MobiAnti.this, MonitorAppService.class);
		            MobiAnti.this.startService(intent);*/
				}
			}).start();
   		
           
   		/*InitAfterRegisterMenu();
   		ListView lv=(ListView)findViewById(R.id.listmenu_afterregister);
   		lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int pos, long arg3) {
					// TODO Auto-generated method stub
					switch(pos)
					{
					case 0:
						StartScanVirusScreen();
						break;
					case 1:
						StartAntiMalwareScreen();
						break;
					case 2:
						StartPreferenceScreen();
						break;
					case 3:
						StartWhiteListScreen();
						break;
					case 4:
						StartHelpScreen();
						break;
					case 5:
						StartAboutScreen();
						break;
					default:
						break;
					}
				}
			});
   		lv.setAdapter(new SimpleAdapter(this, mMenuItem, R.layout.listmenu_row, LIST_COL_NAME, LIST_COL_VALUE));*/
   	}
   	else
   	{
   		//setContentView(R.layout.register_screen);
   		Intent intent = new Intent(MobiSuite.this, RegistActivity.class);
   		startActivity(intent);
   		/*InitBeforeRegisterMenu();
   		ListView lv=(ListView)findViewById(R.id.listmenu_beforeregister);
   		lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					switch(arg2)
					{
					case 0:
						StartRegisterScreen();
						break;
					case 1:
						StartHelpScreen();
						break;
					case 2:
						StartAboutScreen();
						break;
					default:
						break;
					}
					
				}
			});
   		
   		lv.setAdapter(new SimpleAdapter(this, mMenuItem, R.layout.listmenu_row, LIST_COL_NAME, LIST_COL_VALUE));*/
   	}
   }
}