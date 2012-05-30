package onward.mobisuite.smsscheduler;

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
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class BulkSMSActivity extends Activity{
	private Button titleback=null;
	private EditText smscontentedittext=null;
	private static int INSERTSMSTEMPLATE=100;
	private DatabaseManager databaseManager=null;
	private String[] templates=null;
	private ImageView addcontact=null;
	private EditText contactsnameedittext=null;
	private EditText contactsnumberedittext=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bulk_sms);
		databaseManager=new DatabaseManager(this);
		titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		smscontentedittext=(EditText)findViewById(R.id.sms_content_edittext);
		 addcontact=(ImageView)findViewById(R.id.add_contact);
		 contactsnameedittext=(EditText)findViewById(R.id.contacts_name_edittext);
		 contactsnumberedittext=(EditText)findViewById(R.id.contacts_number_edittext);
		smscontentedittext.setOnCreateContextMenuListener(new SMSContentOnCreateContextMenuListener());
		addcontact.setOnClickListener(new AddContactOnClickListener());
	}
	 private class TitleBackBtnOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			finish();
	 		}
	 		
	 	}
	 private class AddContactOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			Intent intent= new Intent(BulkSMSActivity.this,ChooseContactsActivity.class);
	 			startActivityForResult(intent, 1);
	 		}
	 		
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
