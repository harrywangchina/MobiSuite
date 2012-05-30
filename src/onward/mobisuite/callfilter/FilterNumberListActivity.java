package onward.mobisuite.callfilter;

import java.util.ArrayList;
import java.util.HashMap;

import onward.mobisuite.R;
import onward.mobisuite.privacy.InputPhoneNumberActivity;
import onward.mobisuite.utils.Constants;
import onward.mobisuite.utils.DatabaseManager;
import onward.mobisuite.utils.DialogActivity;
import onward.mobisuite.utils.DialogListActivity;
import onward.mobisuite.utils.ListDeleteAdapter;
import onward.mobisuite.utils.MobiSuiteDB;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FilterNumberListActivity extends Activity {
	private Button titleback = null;
	private DatabaseManager databaseManager = null;
	private TextView textviewtoptitle=null;
	private String listid=null;
	private String listname="";
	private String listtype="";
	private String listaction="";
	private String smscontent="";
	private ArrayList<HashMap<String, String>> numberlist = null;
	private ListView phonenumberlist=null;
	private ListAdapter numberItemAdapter=null;
	private Button addphonebookbtn=null;
	private Button addmanuallybtn=null;
	final int PICK_CONTACT1 = 234233;
	final int PICK_CONTACT2 = 234234;
	final int PICK_CONTACT3 = 234235;
	final int PICK_CONTACT4 = 234236;
	final int PICK_CONTACT5 = 234237;
	private String phonename=null;
	private String phoneNumbes=null;
	private String[] numbers=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		databaseManager = new DatabaseManager(this);
		setContentView(R.layout.private_contacts);
		titleback = (Button) findViewById(R.id.btn_title_back);
		textviewtoptitle=(TextView)findViewById(R.id.textview_top_title);
		textviewtoptitle.setText(R.string.filter_number_list);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		phonenumberlist=(ListView)findViewById(R.id.contacts_list);
		addphonebookbtn=(Button)findViewById(R.id.add_phonebook_btn);
		addmanuallybtn=(Button)findViewById(R.id.add_manually_btn);
		addphonebookbtn.setOnClickListener(new AddPhonebookBtnOnClickListener());
		addmanuallybtn.setOnClickListener(new AddManuallyBtnOnClickListener());
		Intent intent=getIntent();
		listid=intent.getStringExtra("listid");
		invalidate();
		ShowNumberListView();
	}
	private void invalidate(){
		Cursor cursor=databaseManager.QueryCallFilterList(MobiSuiteDB.KEY_ID+"=?", new String[]{listid});
		if (cursor.moveToFirst()) {
			listname=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTNAME));
			listtype=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTTYPE));
			listaction=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTACTION));
			smscontent=cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTSMS));
		}
		cursor.close();
	}
	private void ShowNumberListView(){
		Cursor cursor=databaseManager.QueryCallFilterNumber(MobiSuiteDB.KEY_CALLFILTERLISTID+"=?", new String[]{listid});
		if (cursor.getCount()>0) {
			numberlist = new ArrayList<HashMap<String, String>>();
			while (cursor.moveToNext()) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ItemID", cursor.getString(cursor
						.getColumnIndex(MobiSuiteDB.KEY_ID)));
				map.put("ItemName", cursor.getString(cursor
						.getColumnIndex(MobiSuiteDB.KEY_PHONENAME)));
				map.put("ItemSumm", cursor.getString(cursor
						.getColumnIndex(MobiSuiteDB.KEY_PHONENUMBER)));
				map.put("ItemListID", cursor.getString(cursor
						.getColumnIndex(MobiSuiteDB.KEY_CALLFILTERLISTID)));
				numberlist.add(map);
			}
		    numberItemAdapter = new ListDeleteAdapter(numberlist, this, handler);	
		}else {
			numberItemAdapter = new ArrayAdapter<String>(this,
					R.layout.simple_list_item, new String[] { this.getText(
							R.string.have_on_phone_number).toString() });
		}
		cursor.close();
		phonenumberlist.setAdapter(numberItemAdapter);
	}
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				Intent intent1=new Intent(FilterNumberListActivity.this,DialogActivity.class);
				intent1.putExtra("titletext", getText(R.string.delete).toString());
				intent1.putExtra("messagecontent", getText(R.string.delete_the_selected_contact).toString());
				intent1.putExtra("data", numberlist.get(msg.arg1).get("ItemID"));
				startActivityForResult(intent1, 1);	
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	private class TitleBackBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}
	private class AddPhonebookBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (Float.valueOf(Build.VERSION.RELEASE.substring(0, 3)) >2.0) {
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT1);
			}else {
				Intent intent = new Intent(Intent.ACTION_PICK, People.CONTENT_URI); 	
				startActivityForResult(intent, PICK_CONTACT2);
			}
		}

	}
	private class AddManuallyBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 Intent intent=new Intent(FilterNumberListActivity.this, InputPhoneNumberActivity.class);
        	 startActivityForResult(intent, PICK_CONTACT3);
		}

	}
	private HashMap<String, String> getConstants(int i,Intent data){
		HashMap<String, String> constact=null;
		Cursor cursor = managedQuery(data.getData(), null, null, null, null);
		if (cursor!=null&&cursor.moveToFirst()) {
			 String contactId = cursor.getString(cursor.getColumnIndex(Constants.GetPEOPLE_ID(i)));  //�����ϵ�˵�ID��   
             String disPlayName = cursor.getString(cursor.getColumnIndex(Constants.GetPEOPLE_NAME(i)));  //�����ϵ������ 
           
             int phoneCount = cursor.getInt(cursor.getColumnIndex(Constants.GetPEOPLE_NUMBER(i)));   // �鿴����ϵ���ж��ٸ��绰���롣���û���ⷵ��ֵΪ0    
             if (phoneCount > 0) {  
            	 constact=new HashMap<String, String>();
            	 constact.put("phonename", disPlayName);
                 // �����ϵ�˵ĵ绰����   
                 Cursor phones = this.getContentResolver().query(  
                		 Constants.GetPHONE_CONTENT_URI(i) ,  
                        null,  
                        Constants.GetPHONES_PERSON_ID(i)  + "=" + contactId, null, null); 
                 String phoneNumbers="";
                 int c=0;
                 while (phones!=null&&phones.moveToNext()) {
                	 String phoneNumber = phones.getString(phones.getColumnIndex(Constants.GetPHONES_NUMBER(i))).replace("-", "");  	
                	if (c==0) {
                		phoneNumbers=phoneNumber;
					}else {
						phoneNumbers=phoneNumbers+","+phoneNumber;
					}
                	c++;
                 }  
                 constact.put("phonenumber", phoneNumbers);
                 phones.close();  
             }    
		}
		if (cursor!=null) {
			cursor.close();
		}
		return constact;
	}
	private void AddNewNumberFoContact(int i,Intent data){
		HashMap<String, String> constact = getConstants(i,data);
		if (constact==null||constact.get("phonenumber")==null) {
			Toast.makeText(this, R.string.constact_have_no_number, Toast.LENGTH_SHORT).show();
		}else {
			phonename=constact.get("phonename");
			phoneNumbes=constact.get("phonenumber");
			numbers=phoneNumbes.split(",");
			if (numbers.length>1) {
				Intent intent= new Intent(this,DialogListActivity.class);
				intent.putExtra("titletext", getText(R.string.select_number)+":"+constact.get("phonename"));
				intent.putExtra("listitems", numbers);
				startActivityForResult(intent, PICK_CONTACT4);
			}else {
				CheckNumber(phoneNumbes,phonename);
			}
		}
	}
	private void AddNewNumberFoManually(Intent data){
		String name=data.getStringExtra("name");
		String number=data.getStringExtra("number");
		if (name.equals("")) {
			name=getText(R.string.no_name).toString();
		}
		CheckNumber(number,name);
	}
	private void CheckNumber(String number,String name){
		String[] numbers=number.split(",");
		for (int i = 0; i < numbers.length; i++) {
			Cursor cursor=databaseManager.QueryCallFilterNumber(MobiSuiteDB.KEY_PHONENUMBER+"=? and "+MobiSuiteDB.KEY_CALLFILTERLISTID+"=?", new String[]{numbers[i],listid});
			   if (cursor.getCount()>0) {
					Intent intent=new Intent(this,DialogActivity.class);
					intent.putExtra("titletext", getText(R.string.number_info).toString());
					intent.putExtra("messagecontent",numbers[i]+" "+getText(R.string.is_exit));
					intent.putExtra("buttonshow", 1);
					startActivity(intent);
			   }else {
				   Cursor cursor1=databaseManager.QueryCallFilterNumber(MobiSuiteDB.KEY_PHONENUMBER+"=?", new String[]{numbers[i]});
				   boolean differenttype=false;
				   while (cursor1.moveToNext()) {
					   Cursor cursor2=databaseManager.QueryCallFilterList(MobiSuiteDB.KEY_ID+"=?", new String[]{cursor1.getString(cursor1.getColumnIndex(MobiSuiteDB.KEY_CALLFILTERLISTID))});
						if (cursor2.moveToFirst()) {							
							if (!listtype.equals(cursor2.getString(cursor2.getColumnIndex(MobiSuiteDB.KEY_LISTTYPE)))) {
								differenttype=true;
							}
						}
						cursor2.close();
						if (differenttype) {
							break;
						}
			    	}
				   cursor1.close();
				   if (differenttype) {
					   Intent intent=new Intent(this,DialogActivity.class);
						intent.putExtra("titletext", getText(R.string.number_info).toString());
						String cursorlisttype=getText(R.string.blacklist).toString();
						if (listtype.equals("black")) {
							cursorlisttype=getText(R.string.whitelist).toString();
						}
						intent.putExtra("messagecontent",numbers[i]+" "+getText(R.string.is_exit_in)+" "+cursorlisttype+getText(R.string.do_you_want));
						intent.putExtra("data", name+","+numbers[i]);
						startActivityForResult(intent, PICK_CONTACT5);
					  }else {
						  databaseManager.InsertCallFilterNumber(name, numbers[i], listid);
					}
			   }
			   cursor.close();
		}
		ShowNumberListView();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("requestCode:"+requestCode+",resultCode:"+resultCode);
		if (resultCode==Activity.RESULT_OK) {
			switch (requestCode) {
			case 1:
				databaseManager.DeleteCallFilterNumber(data.getStringExtra("data"));
				ShowNumberListView();
				break;
			case PICK_CONTACT1:
				AddNewNumberFoContact(1,data);
				break;
	         case PICK_CONTACT2:
	        	 AddNewNumberFoContact(0,data);
				break;
	         case PICK_CONTACT3:
	        	 AddNewNumberFoManually(data);
	        	 break;
	         case PICK_CONTACT4:
	        	 @SuppressWarnings("unchecked")
				HashMap<Integer, Boolean> isSelect=(HashMap<Integer, Boolean>) data.getSerializableExtra("isSelect");
	        	 int i=0;
	        	 String numbersString="";
	        	 for (int j = 0; j < isSelect.size(); j++) {
						if (isSelect.get(j)) {	
							if (i==0) {
								numbersString=numbers[j];
							}else {
								numbersString=numbersString+","+numbers[j];
							}
							i++;
						}	
					}
					if (i>0) {
						CheckNumber(numbersString,phonename);
					}
		         break;
	         case PICK_CONTACT5:
	        	   String[] phoneinfors=data.getStringExtra("data").split(",");
	        	   databaseManager.InsertCallFilterNumber(phoneinfors[0],phoneinfors[1] , listid);
	        	   ShowNumberListView();
	        	 break;
			}
		}
		
	}
	public void finish() {
		// TODO Auto-generated method stub
		if (databaseManager != null) {
			databaseManager.close();
			databaseManager = null;
		}
		super.finish();
	}
}
