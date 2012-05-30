package onward.mobisuite.privacy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import onward.mobisuite.R;
import onward.mobisuite.utils.DatabaseManager;
import onward.mobisuite.utils.DialogActivity;
import onward.mobisuite.utils.ListDeleteAdapter;
import onward.mobisuite.utils.MobiSuiteDB;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class PrivateFoldersActivity extends Activity {
	private Button titleback=null;
	private Button addnewflodersbtn=null;
	private DatabaseManager databaseManager=null;
	private ArrayList<HashMap<String, String>> fileItem=null;
	private ListAdapter listItemAdapter=null;
	private ListView filelist=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		databaseManager=new DatabaseManager(this);
		setContentView(R.layout.private_folders);
		titleback=(Button)findViewById(R.id.btn_title_back);
		addnewflodersbtn=(Button)findViewById(R.id.add_new_floders_btn);
		filelist=(ListView)findViewById(R.id.file_list);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		addnewflodersbtn.setOnClickListener(new AddNewFlodersBtnOnClickListener());
		ShowFolderList();
	}
	  private class TitleBackBtnOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			finish();
	 		}
	 		
	 	}
	  private class AddNewFlodersBtnOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			Intent intent=new Intent(PrivateFoldersActivity.this,SelectFolderActivity.class);
	 			startActivityForResult(intent, 1);
	 		}
	 		
	 	}
	  @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1) {
			switch (resultCode) {
			case Activity.RESULT_OK:
				databaseManager.DeletePrivateFolder(data.getStringExtra("data"));
				ShowFolderList();
				break;
            case 1:
            	System.out.println(data.getStringExtra("filepath"));
				File file=new File(data.getStringExtra("filepath"));
			    try {
					databaseManager.InsertPrivateFolder(file.getName(), file.getCanonicalPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ShowFolderList();
				break;
			default:
				break;
			}
		}
	}
	private void ShowFolderList(){
		fileItem=new ArrayList<HashMap<String, String>>();
		Cursor cursor =databaseManager.QueryPrivateFolder(null, null);
		if (cursor!=null) {
			while (cursor.moveToNext()) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("ItemID", cursor.getString(cursor
						.getColumnIndex(MobiSuiteDB.KEY_ID)));
				map.put("ItemName",cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_FILENAME)));
				map.put("ItemSumm",cursor.getString(cursor.getColumnIndex(MobiSuiteDB.KEY_FILEPATH)));
				fileItem.add(map);
			}
			cursor.close();
		}
		if (fileItem.isEmpty()) {
			
			listItemAdapter=new ArrayAdapter<String>(this,
	                R.layout.simple_list_item, new String[]{this.getText(R.string.have_on_privacy_folder).toString()});				
		}else {
			listItemAdapter=new ListDeleteAdapter(fileItem, this,handler);	
		}
		filelist.setAdapter(listItemAdapter);
	}  
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				Intent intent1=new Intent(PrivateFoldersActivity.this,DialogActivity.class);
				intent1.putExtra("titletext", getText(R.string.delete).toString());
				intent1.putExtra("messagecontent", getText(R.string.delete_the_selected_folder).toString());
				intent1.putExtra("data", fileItem.get(msg.arg1).get("ItemID"));
				startActivityForResult(intent1, 1);		
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	public void finish() {
		// TODO Auto-generated method stub
		if (databaseManager!=null) {
			databaseManager.close();
			databaseManager=null;
		}
		super.finish();
	}
}
