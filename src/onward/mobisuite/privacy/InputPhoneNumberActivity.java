package onward.mobisuite.privacy;

import onward.mobisuite.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputPhoneNumberActivity extends Activity{
	    private EditText name_edittext=null;
	    private EditText number_edittext=null;
	    private Button ok_button=null;
	    private Button cancel_button=null;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.input_phone_number);
        	name_edittext = (EditText)findViewById(R.id.name_edittext);
        	number_edittext = (EditText)findViewById(R.id.number_edittext);
        	ok_button=(Button)findViewById(R.id.ok_button);
        	cancel_button=(Button)findViewById(R.id.cancel_button);
        	ok_button.setOnClickListener(new OKButtonOnClickListener());
        	cancel_button.setOnClickListener(new CancelButtonOnClickListener());
        }
     private class OKButtonOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String number=number_edittext.getText().toString();
			if (number.equals("")) {
				Toast.makeText(InputPhoneNumberActivity.this, R.string.input_the_number, Toast.LENGTH_LONG).show();
			}else {
				Intent intent=new Intent();
				intent.putExtra("name",name_edittext.getText().toString());
				intent.putExtra("number",number_edittext.getText().toString());
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		}
    	 
     }   
     private class CancelButtonOnClickListener implements OnClickListener{

 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			finish();
 		}
     	 
      }   
}
