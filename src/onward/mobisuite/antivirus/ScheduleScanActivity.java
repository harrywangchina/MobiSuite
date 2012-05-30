package onward.mobisuite.antivirus;

import onward.mobisuite.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScheduleScanActivity extends Activity{
	private Button titleback=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.schedule_scan);
    	titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
    }
    private class TitleBackBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		finish();
		}
		
	}
}
