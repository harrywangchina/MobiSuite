package onward.mobisuite.smsscheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import onward.mobisuite.R;
import onward.mobisuite.utils.DatabaseManager;
import onward.mobisuite.utils.DialogActivity;
import onward.mobisuite.utils.ListTextAdapter;
import onward.mobisuite.utils.MobiSuiteDB;
import onward.mobisuite.utils.PhoneContact;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class ScheduleSMSActivity extends Activity {
	private Button titleback=null;
	private EditText smscontentedittext=null;
	private static int INSERTSMSTEMPLATE=100;
	private DatabaseManager databaseManager=null;
	private String[] templates=null;
	private int mDay;
	private int mHour;
	private int mMinute;
	private int mMonth;
	private int mYear;
	private final int DIALOG_DATEPICK = 1;
	private final int DIALOG_TIMEPICK = 2;
	private TextView scheduletime=null;
	private RelativeLayout layoutscheduledate=null;
	private RelativeLayout layoutscheduletime=null;
	private RelativeLayout layoutschedule=null;
	private CheckBox checkboxschedule=null;
	private ImageView addcontact=null;
	private EditText contactsnameedittext=null;
	private EditText contactsnumberedittext=null;
	private Button buttonsave=null;
	private Button buttoncancel=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_sms);
		databaseManager=new DatabaseManager(this);
		titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		smscontentedittext=(EditText)findViewById(R.id.sms_content_edittext);
		 scheduletime = (TextView)findViewById(R.id.schedule_time);
		 layoutscheduledate = (RelativeLayout)findViewById(R.id.layout_schedule_date);
		 layoutscheduletime = (RelativeLayout)findViewById(R.id.layout_schedule_time);
		 layoutschedule = (RelativeLayout)findViewById(R.id.layout_schedule);
		 checkboxschedule = (CheckBox)findViewById(R.id.checkbox_schedule);
		 addcontact=(ImageView)findViewById(R.id.add_contact);
		 contactsnameedittext=(EditText)findViewById(R.id.contacts_name_edittext);
		 contactsnumberedittext=(EditText)findViewById(R.id.contacts_number_edittext);
		 buttonsave = (Button)findViewById(R.id.button_save);
		 buttoncancel = (Button)findViewById(R.id.button_cancel);
		smscontentedittext.setOnCreateContextMenuListener(new SMSContentOnCreateContextMenuListener());
		layoutscheduledate.setOnClickListener(new ScheduleDateButtonListener());
		layoutscheduletime.setOnClickListener(new ScheduleTimeButtonListener());
		layoutschedule.setOnClickListener(new LayoutScheduleOnClickListener());
		addcontact.setOnClickListener(new AddContactOnClickListener());
		buttonsave.setOnClickListener(new SaveOnClickListener());
		buttoncancel.setOnClickListener(new TitleBackBtnOnClickListener());
		invalidata();
		setScheduleTime();
	}
	private void invalidata(){
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		mYear =1900 +date.getYear();
		mMonth = date.getMonth();
		mDay = date.getDate();
		mHour = date.getHours();
		mMinute = date.getMinutes();
	}
	 private class TitleBackBtnOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			finish();
	 		}
	 		
	 	}
	 private class SaveOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			if (contactsnumberedittext.getText().toString().equals("")) {
	 				Intent intent1=new Intent(ScheduleSMSActivity.this,DialogActivity.class);
					intent1.putExtra("titletext", getText(R.string.schedule_sms).toString());
					intent1.putExtra("messagecontent", getText(R.string.input_the_number).toString());
					intent1.putExtra("buttonshow", 1);
					startActivity(intent1);
				}else if (smscontentedittext.getText().toString().equals("")) {
					Intent intent1=new Intent(ScheduleSMSActivity.this,DialogActivity.class);
					intent1.putExtra("titletext", getText(R.string.schedule_sms).toString());
					intent1.putExtra("messagecontent", getText(R.string.input_the_sms_content).toString());
					intent1.putExtra("buttonshow", 1);
					startActivity(intent1);
				}else if (checkboxschedule.isChecked()) {
					 Date date= new Date(mYear - 1900, mMonth, mDay,mHour, mMinute);
					 long l = date.getTime();
					if (l-System.currentTimeMillis() < 1L) {
						Intent intent1=new Intent(ScheduleSMSActivity.this,DialogActivity.class);
						intent1.putExtra("titletext", getText(R.string.schedule_sms).toString());
						intent1.putExtra("messagecontent", getText(R.string.schedule_time_later).toString());
						intent1.putExtra("buttonshow", 1);
						startActivity(intent1);
					}else {
						Save();	
					}
				}else{
					Save();	
				}
	 		}
	 		
	 	}
	private void Save(){
		
	}
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==Activity.RESULT_OK) {
			switch (requestCode) {
			case 1:
			 @SuppressWarnings("unchecked")
			HashMap<String, PhoneContact> selectcontacts=(HashMap<String, PhoneContact>) data.getSerializableExtra("selectContacts");
			 Iterator<Entry<String, PhoneContact>> contactIterator = selectcontacts.entrySet().iterator();
			 String names="";
			 String numbers="";
			 while (contactIterator.hasNext()) {
				 Entry<String, PhoneContact> contact = contactIterator.next();
				 PhoneContact phoneContact=contact.getValue();
				 for (int i = 0; i < phoneContact.getPhoneArrayList().size(); i++) {
					 names=names+phoneContact.getName()+",";
					 numbers=numbers+ phoneContact.getPhoneArrayList().get(i)+",";
				}
			}
			 String contactsname=contactsnameedittext.getText().toString();
			 String contactsnumber=contactsnumberedittext.getText().toString();
			 if (contactsname.length()>0) {
	 				if (!contactsname.endsWith(",")) {
	 					contactsname=contactsname+",";
	 				}
				}
	 			if (contactsnumber.length()>0) {
	 				if (!contactsnumber.endsWith(",")) {
	 					contactsnumber=contactsnumber+",";
	 				}
				}	
			 	contactsname=contactsname+names;
			 	contactsnumber=contactsnumber+numbers;
			 	contactsnameedittext.setText(contactsname);
			 	contactsnumberedittext.setText(contactsnumber);
				break;
			}
			}
	}
	 private class AddContactOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			Intent intent= new Intent(ScheduleSMSActivity.this,ChooseContactsActivity.class);
	 			startActivityForResult(intent, 1);
	 		}
	 		
	 	}
	 private class LayoutScheduleOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			checkboxschedule.setChecked(!checkboxschedule.isChecked());
	 		}
	 		
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
		TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				mHour=hourOfDay;
				mMinute=minute;
				setScheduleTime();
			}
		};
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case DIALOG_DATEPICK:
				return new DatePickerDialog(this, onDateSetListener, mYear,mMonth, mDay);
			case DIALOG_TIMEPICK:
				return new TimePickerDialog(this, onTimeSetListener,mHour, mMinute, true);
			}
			return null;
		}
	 private class ScheduleDateButtonListener implements OnClickListener {
			public void onClick(View v) {
				showDialog(DIALOG_DATEPICK);
			}
		}

		private class ScheduleTimeButtonListener implements OnClickListener {
			public void onClick(View v) {
				showDialog(DIALOG_TIMEPICK);
			}
		}
	public void setScheduleTime(){
		     Date date= new Date(mYear - 1900, mMonth, mDay,mHour, mMinute);
			 String s = DateFormat.format("yyyy/MM/dd kk:mm", date).toString();
			 scheduletime.setText(s);
		}
	 private class SMSContentOnCreateContextMenuListener implements OnCreateContextMenuListener{

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.add(0, INSERTSMSTEMPLATE, 0, R.string.insert_sms_templates);
			}
			 
		 }
		 public boolean onContextItemSelected(MenuItem item) {
		    	// TODO Auto-generated method stub
		    	if (item.getItemId()==INSERTSMSTEMPLATE) {
		    		Builder builder = new Builder(this);
		    		Cursor cursor=databaseManager.QuerySMSTemplates(null, null);
		    		if (cursor.getCount()>0) {
		    			templates=new String[cursor.getCount()];
		    			int i=0;
		    			while (cursor.moveToNext()) {
		    				templates[i]=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_SMSCONTENT));
		    				i++;
						}
		    			builder.setSingleChoiceItems(new ListTextAdapter(this, templates), 0, new DialogInterface.OnClickListener() {					
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								smscontentedittext.setText(smscontentedittext.getText()+templates[which]);
							     dialog.dismiss(); 
							}
						});
					builder.create().show();
					}else {
						Intent intent1=new Intent(this,DialogActivity.class);
						intent1.putExtra("titletext", getText(R.string.sms_templates).toString());
						intent1.putExtra("messagecontent", getText(R.string.have_on_exit_template).toString());
						intent1.putExtra("buttonshow", 1);
						startActivity(intent1);
					}
		    		cursor.close();
				} 
		    	return super.onContextItemSelected(item);
		    }
		 public void finish() {
				// TODO Auto-generated method stub
				if (databaseManager!=null) {
					databaseManager.close();
					databaseManager=null;
				}
				super.finish();
			}
}
