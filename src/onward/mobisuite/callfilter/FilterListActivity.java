package onward.mobisuite.callfilter;

import java.util.ArrayList;
import java.util.HashMap;

import onward.mobisuite.R;
import onward.mobisuite.utils.DatabaseManager;
import onward.mobisuite.utils.DialogActivity;
import onward.mobisuite.utils.ListTextAdapter;
import onward.mobisuite.utils.MobiSuiteDB;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FilterListActivity extends Activity {
	private Button titleback=null;
	private Button whitelistbtn=null;
	private Button blacklistbtn=null;
	private Button addnewlistbtn=null;
	private boolean showwhitelist=true;
	private DatabaseManager databaseManager=null;
	private ArrayList<HashMap<String, String>> listItem=null;
	private ListView filterlist=null;
	private int longpressselectid=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter_list);
		databaseManager=new DatabaseManager(this);
		titleback=(Button)findViewById(R.id.btn_title_back);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		whitelistbtn=(Button)findViewById(R.id.white_list_btn);
		blacklistbtn=(Button)findViewById(R.id.black_list_btn);
		addnewlistbtn=(Button)findViewById(R.id.add_new_list_btn);
		filterlist=(ListView)findViewById(R.id.filter_list);
		whitelistbtn.setBackgroundResource(R.drawable.button_common_pressed);
		whitelistbtn.setOnClickListener(new WhiteListOnClickListener());
		blacklistbtn.setOnClickListener(new BlackListOnClickListener());
		addnewlistbtn.setOnClickListener(new AddNewListOnClickListener());
		filterlist.setOnItemLongClickListener(new FilterListOnItemLongClickListener());
		filterlist.setOnItemClickListener(new FilterListOnItemClickListener());
		SetFliterList();
	}
	private void SetFliterList(){
		String listtype;
		if (showwhitelist) {
			listtype="white";
		}else {
			listtype="black";
		}
		Cursor cursor=databaseManager.QueryCallFilterList(MobiSuiteDB.KEY_LISTTYPE+"=?", new String[]{listtype});
		if (cursor.getCount()>0) {
			listItem=new ArrayList<HashMap<String, String>>();
			while (cursor.moveToNext()) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ItemID", cursor.getString(cursor
						.getColumnIndex(MobiSuiteDB.KEY_ID)));
				map.put("ItemName",cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_LISTNAME)));
				listItem.add(map);
			}
			SimpleAdapter simpleAdapter=new SimpleAdapter(this, listItem, R.layout.filter_list_item, new String[]{"ItemName"}, new int[]{R.id.list_name_text});
			filterlist.setAdapter(simpleAdapter);
		}else {
			ArrayAdapter<String> listItemAdapter = new ArrayAdapter<String>(this,
	                R.layout.simple_list_item, new String[]{this.getText(R.string.have_on_filter_list).toString()});				
			filterlist.setAdapter(listItemAdapter);
		}
		cursor.close();
	}
	private class TitleBackBtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
		
	}
	private class FilterListOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(FilterListActivity.this,FilterNumberListActivity.class);
			intent.putExtra("listid", listItem.get(arg2).get("ItemID"));
			startActivity(intent);
		}
		
	}
	private class FilterListOnItemLongClickListener implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				 int arg2, long arg3) {
			// TODO Auto-generated method stub
			longpressselectid=arg2;
			Builder builder = new Builder(FilterListActivity.this);
			int operates = R.array.filter_operate;			
			builder.setSingleChoiceItems(new ListTextAdapter(FilterListActivity.this, operates), 0, new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					     switch (which) {
						case 0:
							Intent intent=new Intent(FilterListActivity.this,AddNewFilterListActivity.class);
							intent.putExtra("listid", listItem.get(longpressselectid).get("ItemID"));
							startActivityForResult(intent, 1);
							break;
						case 1:
							Intent intent1=new Intent(FilterListActivity.this,DialogActivity.class);
							intent1.putExtra("titletext", getText(R.string.delete).toString());
							intent1.putExtra("messagecontent", getText(R.string.delete_the_selected_list).toString());
							startActivityForResult(intent1, 2);
							break;
						default:
							break;
						}
					     dialog.dismiss(); 
					}
				});
			builder.create().show();
			return false;
		}
		
	}
	private class WhiteListOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			whitelistbtn.setBackgroundResource(R.drawable.button_common_pressed);
			blacklistbtn.setBackgroundResource(R.drawable.button_common_normal);
			showwhitelist=true;
			SetFliterList();
		}
		
	}
	private class BlackListOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			blacklistbtn.setBackgroundResource(R.drawable.button_common_pressed);
			whitelistbtn.setBackgroundResource(R.drawable.button_common_normal);
			showwhitelist=false;
			SetFliterList();
		}
		
	}
	private class AddNewListOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(FilterListActivity.this,AddNewFilterListActivity.class);
			String listtype="black";
			if (showwhitelist) {
				listtype="white";
			}
			intent.putExtra("listtype", listtype);
			startActivityForResult(intent, 1);
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case 1:
			SetFliterList();
			break;
		case 2:
			if (resultCode==Activity.RESULT_OK) {
				databaseManager.DeleteCallFilterList( listItem.get(longpressselectid).get("ItemID"));
				SetFliterList();
			}
			break;
		default:
			break;
		}
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
