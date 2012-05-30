package onward.mobisuite.antivirus;

import onward.mobisuite.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class AntiVirusActivity extends Activity {
	private Button titleback=null;
	private RelativeLayout layout_schedule_san=null;
	private RelativeLayout layout_user_scan=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anti_virus_main);
		titleback=(Button)findViewById(R.id.btn_title_back);
		layout_user_scan=(RelativeLayout)findViewById(R.id.layout_user_scan);
		layout_schedule_san=(RelativeLayout)findViewById(R.id.layout_schedule_scan);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		layout_user_scan.setOnClickListener(new UserSanOnClickListener());
		layout_schedule_san.setOnClickListener(new ScheduleSanOnClickListener());
	}
	private class TitleBackBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AntiVirusActivity.this.finish();
		}
		
	}
	private class UserSanOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(AntiVirusActivity.this,UserScanActivity.class);
			startActivity(intent);
		}
		
	}
	private class ScheduleSanOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(AntiVirusActivity.this,ScheduleScanActivity.class);
			startActivity(intent);
		}
		
	}
}
