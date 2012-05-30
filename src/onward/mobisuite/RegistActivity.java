package onward.mobisuite;

import java.util.Date;

import onward.mobisuite.utils.ListRadioAdapter;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegistActivity extends Activity {
	static private String Gatewaya[];
	static private String educationa[];
	static private String occupationa[];
	static private String preferencea[];
	static final private String GPRS_Gate = "GPRS";
	static final private String Pri_Gate = "Primary SMS";
	static final private String Sec_Gate = "Secondary SMS";

	static final public String Employed = "Employed";
	static final public String Business = "Business";
	static final public String Doctor = "Doctor";
	static final public String IT = "IT";
	static final public String Education = "Education";
	static final public String Law = "Law";
	static final public String Other = "Other";

	static final public String Graduate = "Graduate";
	static final public String Post_Graduate = "Post Graduate";
	static final public String Engineer = "Engineer";
	static final public String MBA = "MBA";

	static final public String PreA = "A";
	static final public String PreB = "B";
	static final public String PreC = "C";

	static {
		Gatewaya = new String[3];
		Gatewaya[0] = GPRS_Gate;
		Gatewaya[1] = Pri_Gate;
		Gatewaya[2] = Sec_Gate;

		occupationa = new String[7];
		occupationa[0] = Employed;
		occupationa[1] = Business;
		occupationa[2] = Doctor;
		occupationa[3] = IT;
		occupationa[4] = Education;
		occupationa[5] = Law;
		occupationa[6] = Other;

		educationa = new String[5];
		educationa[0] = Graduate;
		educationa[1] = Post_Graduate;
		educationa[2] = Engineer;
		educationa[3] = MBA;
		educationa[4] = Other;

		preferencea = new String[3];
		preferencea[0] = PreA;
		preferencea[1] = PreB;
		preferencea[2] = PreC;
	}

	String Registered = "0";
	String Name = "";
	String Scratchcode = "";
	String Gateway = GPRS_Gate;
	String Month = "1";
	String Year = "2000";
	String Day = "1";
	String Randomnumber = "";
	String Imei = "";
	String Password = "1234";
	String ConfirmPassword = "";
	String MobileNumber = "";
	String Firsttime = "0";
	String SubId = "";
	String reminder = "";

	String preference = RegistActivity.PreA;
	String occupation = RegistActivity.Employed;
	String education = RegistActivity.Graduate;
	String emailid = "";
	String gender = "M";
	String location = "";
	String brand = "";

	private final int DIALOG_DATEPICK = 1;
	
	private int mDay;
	private int mHour;
	private int mMinute;
	private int mMonth;
	private int mYear;

	private RelativeLayout layoutpreference;
	private TextView preferenceDefaultText;
	private RelativeLayout layoutoccupation;
	private TextView occupationDefaultText;
	private RelativeLayout layouteducation;
	private TextView educationDefaultText;
	private RelativeLayout layoutgateway;
	private TextView gatewayDefaultText;
	private RelativeLayout layoutdate;
	private TextView dateDefaultText;
	private ListRadioAdapter preferenceAdapter;
	private ListRadioAdapter occupationAdapter;
	private ListRadioAdapter educationAdapter;
	private ListRadioAdapter gatewayAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_screen);

		layoutpreference = (RelativeLayout) findViewById(R.id.layout_preference);
		preferenceDefaultText = (TextView) findViewById(R.id.preference_default_text);
		layoutoccupation = (RelativeLayout) findViewById(R.id.layout_occupation);
		occupationDefaultText = (TextView) findViewById(R.id.occupation_default_text);
		layouteducation = (RelativeLayout) findViewById(R.id.layout_education);
		educationDefaultText = (TextView) findViewById(R.id.education_default_text);
		layoutgateway = (RelativeLayout) findViewById(R.id.layout_gateway);
		gatewayDefaultText = (TextView) findViewById(R.id.gateway_default_text);
		layoutdate = (RelativeLayout) findViewById(R.id.layout_date);
		dateDefaultText = (TextView) findViewById(R.id.date_default_text);

		layoutpreference
				.setOnClickListener(new LayoutPreferenceOnClickListener());
		layoutoccupation
				.setOnClickListener(new LayoutOccupationOnClickListener());
		layouteducation
				.setOnClickListener(new LayoutEducationOnClickListener());
		layoutgateway.setOnClickListener(new LayoutGatewayOnClickListener());
		layoutdate.setOnClickListener(new LayoutDateOnClickListener());
	}

	private class LayoutPreferenceOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Builder builder = new Builder(RegistActivity.this);
			int preferenceItems = R.array.preference;
			preferenceAdapter = new ListRadioAdapter(RegistActivity.this,
					preferenceItems, new Handler(), 1);
			builder.setSingleChoiceItems(preferenceAdapter, 0,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							preferenceAdapter.setItemSelect(which);
							preferenceAdapter.notifyDataSetChanged();
							preferenceDefaultText.setText(preferencea[which]);
							dialog.dismiss();
						}
					});
			builder.create().show();
		}
	}

	private class LayoutOccupationOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Builder builder = new Builder(RegistActivity.this);
			int occupationItems = R.array.occupation;
			occupationAdapter = new ListRadioAdapter(RegistActivity.this,
					occupationItems, new Handler(), 1);
			builder.setSingleChoiceItems(occupationAdapter, 0,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							occupationAdapter.setItemSelect(which);
							occupationAdapter.notifyDataSetChanged();
							occupationDefaultText.setText(occupationa[which]);
							dialog.dismiss();
						}
					});
			builder.create().show();
		}

	}

	private class LayoutEducationOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Builder builder = new Builder(RegistActivity.this);
			int educationItems = R.array.education;
			educationAdapter = new ListRadioAdapter(RegistActivity.this,
					educationItems, new Handler(), 1);
			builder.setSingleChoiceItems(educationAdapter, 0,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							educationAdapter.setItemSelect(which);
							educationAdapter.notifyDataSetChanged();
							educationDefaultText.setText(educationa[which]);
							dialog.dismiss();
						}
					});
			builder.create().show();
		}

	}

	private class LayoutGatewayOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Builder builder = new Builder(RegistActivity.this);
			int gatewayItems = R.array.Select_Gateway;
			gatewayAdapter = new ListRadioAdapter(RegistActivity.this,
					gatewayItems, new Handler(), 1);
			builder.setSingleChoiceItems(gatewayAdapter, 0,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							gatewayAdapter.setItemSelect(which);
							gatewayAdapter.notifyDataSetChanged();
							gatewayDefaultText.setText(Gatewaya[which]);
							dialog.dismiss();
						}
					});
			builder.create().show();
		}

	}

	private class LayoutDateOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			showDialog(DIALOG_DATEPICK);
		}

	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DATEPICK:
			return new DatePickerDialog(this, onDateSetListener, mYear,mMonth, mDay);
		}
		return null;
	}
	
	DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear=year;
			mMonth=monthOfYear;
			mDay=dayOfMonth;
			setScheduleTime();
		}
	};
	
	public void setScheduleTime(){
	     Date date= new Date(mYear - 1900, mMonth, mDay);
		 String s = DateFormat.format("yyyy/MM/dd", date).toString();
		 dateDefaultText.setText(s);
	}
}
