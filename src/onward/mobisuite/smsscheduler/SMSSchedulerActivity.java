package onward.mobisuite.smsscheduler;

import onward.mobisuite.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SMSSchedulerActivity extends Activity{
	private Button titleback=null;
	private RelativeLayout layoutschedulesms=null;
	private RelativeLayout layoutscheduledlist=null;
	private RelativeLayout layoutbulksms=null;
	private RelativeLayout layoutsmstemplates=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_scheduler_main);
		titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		layoutschedulesms=(RelativeLayout)findViewById(R.id.layout_schedule_sms);
		layoutscheduledlist=(RelativeLayout)findViewById(R.id.layout_scheduled_list);
		layoutbulksms=(RelativeLayout)findViewById(R.id.layout_bulk_sms);
		layoutsmstemplates=(RelativeLayout)findViewById(R.id.layout_sms_templates);
		layoutschedulesms.setOnClickListener(new LayoutScheduleSMSOnClickListener());
		layoutscheduledlist.setOnClickListener(new LayoutScheduleListOnClickListener());
		layoutbulksms.setOnClickListener(new LayoutBulkSMSOnClickListener());
		layoutsmstemplates.setOnClickListener(new LayoutSMSTemplatesOnClickListener());
	}
	 private class TitleBackBtnOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			finish();
	 		}
	 		
	 	}
	 private class LayoutScheduleSMSOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			Intent intent=new Intent(SMSSchedulerActivity.this,ScheduleSMSActivity.class);
	 			startActivity(intent);
	 		}
	 		
	 	}
	 private class LayoutScheduleListOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			Intent intent=new Intent(SMSSchedulerActivity.this,ScheduledListActivity.class);
	 			startActivity(intent);
	 		}
	 		
	 	}
	 private class LayoutBulkSMSOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			Intent intent=new Intent(SMSSchedulerActivity.this,BulkSMSActivity.class);
	 			startActivity(intent);
	 		}
	 		
	 	}
	 private class LayoutSMSTemplatesOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			Intent intent=new Intent(SMSSchedulerActivity.this,SMSTemplatesActivity.class);
	 			startActivity(intent);
	 		}
	 		
	 	}
}
