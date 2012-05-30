package onward.mobisuite.smsscheduler;

import java.util.ArrayList;
import java.util.HashMap;
import onward.mobisuite.R;
import onward.mobisuite.utils.Constants;
import onward.mobisuite.utils.DialogListActivity;
import onward.mobisuite.utils.PhoneContact;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseContactsActivity extends Activity {
	private TextView dialogtitletxt = null;
	private Button buttonok = null;
	private Button buttoncancel = null;
	private ListView dialog_list = null;
	public Cursor cursor = null;
	public HashMap<String, PhoneContact> selectcontacts = new HashMap<String, PhoneContact>();
	public int i;
	private int idColumn;
	private int nameColumn;
	final int PICK_CONTACT4 = 234236;
	private ContactsCheckListAdapter contactsCheckListAdapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_list);
		dialogtitletxt = (TextView) findViewById(R.id.dialog_title_txt);
		dialog_list = (ListView) findViewById(R.id.dialog_list);
		buttonok = (Button) findViewById(R.id.button_ok);
		buttoncancel = (Button) findViewById(R.id.button_cancel);
		dialogtitletxt.setText(R.string.selecte_contact);
		ContentResolver resolver = getContentResolver();
		if (Float.valueOf(Build.VERSION.RELEASE.substring(0, 3)) < 2.0) {
			i = 0;
		} else {
			i = 1;
		}
		if (Float.valueOf(Build.VERSION.RELEASE.substring(0, 3)) > 2.1) {
			cursor = resolver.query(Constants.GetCONTACT_CONTENT_URI(i), null,
					null, null, " sort_key asc"); // 返回所有联系人信息
		} else {
			cursor = resolver.query(Constants.GetCONTACT_CONTENT_URI(i), null,
					null, null, Constants.GetPEOPLE_NAME(i) + " asc"); // 返回所有联系人信息
		}
		idColumn = cursor.getColumnIndex(Constants.GetPEOPLE_ID(i));
		nameColumn = cursor.getColumnIndex(Constants.GetPEOPLE_NAME(i));
		contactsCheckListAdapter=new ContactsCheckListAdapter(this, cursor);
		dialog_list.setAdapter(contactsCheckListAdapter);
		dialog_list.setOnItemClickListener(new ListOnItemClickListener());
		buttonok.setOnClickListener(new OKOnClickListener());
		buttoncancel.setOnClickListener(new CancelOnClickListener());
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		if (cursor != null) {
			cursor.close();
		}
		super.finish();
	}
	private class ListOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(contactsCheckListAdapter.isSelect.get(arg2)){
				contactsCheckListAdapter.isSelect.put(arg2, false);
			}else {
				contactsCheckListAdapter.isSelect.put(arg2, true);
			}
			addSelectContact(cursor,arg2,contactsCheckListAdapter.isSelect.get(arg2));
			contactsCheckListAdapter.notifyDataSetChanged();
		}
		
	}
	private class OKOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		    Intent intent=new Intent();
		    intent.putExtra("selectContacts", selectcontacts);
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
	private class ContactsCheckListAdapter extends BaseAdapter {
		private Cursor cursor = null;
		private LayoutInflater layoutInflater = null;
		public HashMap<Integer, Boolean> isSelect = null;

		public ContactsCheckListAdapter(Context context, Cursor cursor) {
			this.cursor = cursor;
			layoutInflater = LayoutInflater.from(context);
			isSelect = new HashMap<Integer, Boolean>();
			for (int i = 0; i < cursor.getCount(); i++) {
				isSelect.put(i, false);
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cursor.getCount();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ListCheckboxItem listCheckboxItem = null;
			if (convertView == null) {
				listCheckboxItem = new ListCheckboxItem();
				convertView = layoutInflater.inflate(
						R.layout.list_checkbox_item, null);
				listCheckboxItem.list_check = (CheckBox) convertView
						.findViewById(R.id.list_check);
				listCheckboxItem.list_name = (TextView) convertView
						.findViewById(R.id.list_name);
				convertView.setTag(listCheckboxItem);
			} else {
				listCheckboxItem = (ListCheckboxItem) convertView.getTag();
			}
			cursor.moveToPosition(position);
			listCheckboxItem.list_name.setText(cursor.getString(nameColumn));
			listCheckboxItem.list_check.setChecked(isSelect.get(position));
			listCheckboxItem.list_check
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							if (isSelect.get(position)) {
								isSelect.put(position, false);
							} else {
								isSelect.put(position, true);
							}
							addSelectContact(cursor,position,isSelect.get(position));
							notifyDataSetChanged();
						}
					});
			return convertView;
		}

	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode==Activity.RESULT_OK) {
			switch (requestCode) {
			 case PICK_CONTACT4:
	        	 @SuppressWarnings("unchecked")
				HashMap<Integer, Boolean> isSelect=(HashMap<Integer, Boolean>) data.getSerializableExtra("isSelect");
	        	 String[] numbers=data.getStringArrayExtra("listitems");
	        	 ArrayList<String> phoneslist=new ArrayList<String>();
	        	 for (int k = 0; k < isSelect.size(); k++) {
					if (isSelect.get(k)) {
						phoneslist.add(numbers[k]);
					}
				}
	        	 if (!phoneslist.isEmpty()) {
	        		PhoneContact phoneContact=new PhoneContact();
	        		String[] infos=data.getStringExtra("data").split(",");
					phoneContact.setName(infos[1]);
					phoneContact.setPhoneArrayList(phoneslist);
					selectcontacts.put(infos[0], phoneContact);
				}
	        	 break;
			}
		}
    }
	private void addSelectContact(Cursor cursor, int position, boolean selected) {
		cursor.moveToPosition(position);
		String contactId=cursor.getString(idColumn);
		String contactname=cursor.getString(nameColumn);
		if (selected) {
			if (cursor.getInt(cursor.getColumnIndex(Constants
					.GetPEOPLE_NUMBER(i))) > 0) {
					 Cursor phones = getContentResolver().query(  
	                		 Constants.GetPHONE_CONTENT_URI(i) ,  
	                        null,  
	                        Constants.GetPHONES_PERSON_ID(i)  + "=" + contactId, null, null);  
					 if (phones.getCount()>1) {
						 String[] numbers=new String[phones.getCount()];
						 int j=0;
						 while (phones.moveToNext()) {
							 numbers[j] = phones.getString(phones.getColumnIndex(Constants.GetPHONES_NUMBER(i))).replace("-", "");  	
	 	                	 j++;
	 	                 }  
						 Intent intent= new Intent(this,DialogListActivity.class);
							intent.putExtra("titletext", getText(R.string.select_number)+":"+contactname);
							intent.putExtra("listitems", numbers);
							intent.putExtra("data",contactId+","+contactname);
							startActivityForResult(intent, PICK_CONTACT4);
					}else {
						PhoneContact phoneContact=new PhoneContact();
						phoneContact.setName(contactname);
						ArrayList<String> phoneslist=new ArrayList<String>();
						while (phones.moveToNext()) {
	 	                	 String phoneNumber = phones.getString(phones.getColumnIndex(Constants.GetPHONES_NUMBER(i))).replace("-", "");  	
	 	                	 phoneslist.add(phoneNumber);
	 	                 }  
	 	                 phoneContact.setPhoneArrayList(phoneslist);
	 	                selectcontacts.put(contactId, phoneContact);
					}
 	                phones.close();
			}
		} else {

			selectcontacts.remove(contactId);
		}
	}

	public class ListCheckboxItem {
		public TextView list_name;
		public CheckBox list_check;
	}
}
