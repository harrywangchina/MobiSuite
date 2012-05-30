package onward.mobisuite.callfilter;

import onward.mobisuite.R;
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

public class CallFilterActivity extends Activity{
	private Button titleback=null;
	private SharedPreferences sharedpreferences = null;
	private CheckBox checkboxrejectsensor;
	private RelativeLayout layoutsmartsensor;
	private RelativeLayout layoutfilterlist=null;
	private RelativeLayout layoutsettings=null;
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.call_filter_main);
		titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		
		checkboxrejectsensor=(CheckBox)findViewById(R.id.checkbox_reject_sensor);
		layoutsmartsensor = (RelativeLayout)findViewById(R.id.layout_smart_sensor);
		layoutfilterlist=(RelativeLayout)findViewById(R.id.layout_filter_list);
		layoutsettings=(RelativeLayout)findViewById(R.id.layout_settings);
		 layoutsmartsensor.setOnClickListener(new LayoutSmartSensorOnClickListener());
		 layoutfilterlist.setOnClickListener(new LayoutFilterListOnClickListener());
		 layoutsettings.setOnClickListener(new LayoutSettingsOnClickListener());
	    checkboxrejectsensor.setChecked( sharedpreferences.getBoolean("smart_sensor_able", true));
	}
	  private class TitleBackBtnOnClickListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		}
	  private class LayoutSmartSensorOnClickListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					checkboxrejectsensor.setChecked(!checkboxrejectsensor.isChecked());
			}
			
		}
	  private class LayoutFilterListOnClickListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CallFilterActivity.this,FilterListActivity.class);
				startActivity(intent);
			}
			
		}
	  private class LayoutSettingsOnClickListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CallFilterActivity.this,FilterListSettingsActivity.class);
				startActivity(intent);
			}
			
		}
	  protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			sharedpreferences.edit().putBoolean("smart_sensor_able", checkboxrejectsensor.isChecked())
			.commit();
		}
}
