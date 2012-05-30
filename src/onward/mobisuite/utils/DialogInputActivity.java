package onward.mobisuite.utils;

import onward.mobisuite.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogInputActivity extends Activity{
	private TextView dialogtitletxt = null;
	private Button buttonok = null;
	private Button buttoncancel = null;
	private String dataString=null;
	private EditText contentedittext=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_input);
		Intent intent = getIntent();
		dialogtitletxt = (TextView) findViewById(R.id.dialog_title_txt);
		contentedittext = (EditText) findViewById(R.id.content_edittext);
		buttonok = (Button) findViewById(R.id.button_ok);
		buttoncancel = (Button) findViewById(R.id.button_cancel);
		dialogtitletxt.setText(intent.getStringExtra("titletext"));
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
			 intent.putExtra("inputcontent", contentedittext.getText().toString());
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
