package onward.mobisuite.utils;

import onward.mobisuite.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DialogActivity extends Activity {
	private TextView dialogtitletxt = null;
	private TextView messagetxt = null;
	private Button buttonok = null;
	private Button buttoncancel = null;
	private String dataString=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_activity);
		Intent intent = getIntent();
		dialogtitletxt = (TextView) findViewById(R.id.dialog_title_txt);
		messagetxt = (TextView) findViewById(R.id.message_txt);
		buttonok = (Button) findViewById(R.id.button_ok);
		buttoncancel = (Button) findViewById(R.id.button_cancel);
		dialogtitletxt.setText(intent.getStringExtra("titletext"));
		messagetxt.setText(intent.getStringExtra("messagecontent"));
		dataString=intent.getStringExtra("data");
		int buttoncount=intent.getIntExtra("buttonshow", 0);
		if (buttoncount==1) {
			buttoncancel.setVisibility(View.GONE);
		}else if (buttoncount==2) {
			buttonok.setVisibility(View.GONE);
		}
		buttonok.setOnClickListener(new OKOnClickListener());
		buttoncancel.setOnClickListener(new CancelOnClickListener());
	}

	private class OKOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 Intent intent=new Intent();
			 intent.putExtra("data", dataString);
             setResult(Activity.RESULT_OK,intent);
             finish();
		}
	}
	private class CancelOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
             setResult(Activity.RESULT_CANCELED);
             finish();
		}
	}
}
