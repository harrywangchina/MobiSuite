package onward.mobisuite.privacy;

import onward.mobisuite.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class PrivacyActivity extends Activity{
	private Button titleback=null;
	private RelativeLayout layoutprivatefolders=null;
	private RelativeLayout layoutprivatecontacts=null;
	private RelativeLayout layoutsettings=null;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.privacy_main);
    	titleback=(Button)findViewById(R.id.btn_title_back);
    	layoutprivatefolders=(RelativeLayout)findViewById(R.id.layout_private_folders);
    	layoutprivatecontacts=(RelativeLayout)findViewById(R.id.layout_private_contacts);
    	layoutsettings=(RelativeLayout)findViewById(R.id.layout_settings);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		layoutprivatefolders.setOnClickListener(new PrivateFoldersOnClickListener());
		layoutprivatecontacts.setOnClickListener(new PrivateContactsOnClickListener());
		layoutsettings.setOnClickListener(new SettingsOnClickListener());
    }
     private class TitleBackBtnOnClickListener implements OnClickListener{

 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			finish();
 		}
 		
 	}
     private class PrivateFoldersOnClickListener implements OnClickListener{

  		@Override
  		public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent intent=new Intent(PrivacyActivity.this,PrivateFoldersActivity.class);
  			startActivity(intent);
  		}
  		
  	}
     private class PrivateContactsOnClickListener implements OnClickListener{

   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   			Intent intent=new Intent(PrivacyActivity.this,PrivateContactsActivity.class);
   			startActivity(intent);
   		}
   		
   	}
     private class SettingsOnClickListener implements OnClickListener{

    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent intent=new Intent(PrivacyActivity.this,PrivacySettingsActivity.class);
    			startActivity(intent);
    		}
    		
    	}
}
