package onward.mobisuite.privacy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import onward.mobisuite.R;
import onward.mobisuite.utils.DatabaseManager;
import onward.mobisuite.utils.MobiSuiteDB;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SelectFolderActivity extends Activity{
	private final static String ITEM_COL_ICON = "icon";
	private final static String ITEM_COL_NAME = "path";
	private final static String LIST_COL_NAME[] = { ITEM_COL_ICON,
			ITEM_COL_NAME };
	private final static int LIST_COL_VALUE[] = { R.id.file_icon,
		R.id.file_name };
	private Button titleback=null;
	private Button okbtn=null;
	private Button cancelbtn=null;
	private ListView filelist=null;
	private TextView file_path=null;
	private File[] currentFiles;
	private File currentParent;
	private ArrayList<File> listfiles=new ArrayList<File>();
	private String SDcardPath=null;
	private ImageButton fileimgbtnback=null;
	private Button fileimgbtnroot=null;
	private DatabaseManager databaseManager=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		databaseManager=new DatabaseManager(this);
		setContentView(R.layout.scan_file_browser);
		titleback=(Button)findViewById(R.id.btn_title_back);
		((TextView)findViewById(R.id.textview_top_title)).setText(R.string.select_folder);
		okbtn=(Button)findViewById(R.id.button_back);
		cancelbtn=(Button)findViewById(R.id.button_scan);
		filelist=(ListView)findViewById(R.id.file_list);
		file_path=(TextView)findViewById(R.id.file_path);
		fileimgbtnback=(ImageButton)findViewById(R.id.file_imgbtn_back);
		fileimgbtnroot=(Button)findViewById(R.id.file_imgbtn_root);
		okbtn.setText(R.string.ok);
		cancelbtn.setText(R.string.cancel);
		titleback.setOnClickListener(new TitleBackBtnOnClickListener());
		//filelist.setDivider(null);
		filelist.setOnItemClickListener(new FileListItemClickListener());
		fileimgbtnback.setOnClickListener(new BackButtonClickListener());
		fileimgbtnroot.setOnClickListener(new ScanFileImgbtnRootListener());
		okbtn.setOnClickListener(new OKBtnOnClickListener());
		cancelbtn.setOnClickListener(new TitleBackBtnOnClickListener());
		try {
			SDcardPath=Environment.getExternalStorageDirectory().getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File sdcard = new File(SDcardPath);
		if (sdcard.exists()) {
			currentParent = sdcard;
			currentFiles = sdcard.listFiles();
			// ʹ�õ�ǰĿ¼�µ�ȫ���ļ����ļ��������ListView
			inflateListView(currentFiles);
		}
	}
	  private class TitleBackBtnOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			finish();
	 		}
	 		
	 	}
	  private class OKBtnOnClickListener implements OnClickListener{

	 		@Override
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			Cursor cursor=databaseManager.QueryPrivateFolder(MobiSuiteDB.KEY_FILEPATH+"=?", new String[]{file_path.getText().toString()});
	 			if (cursor==null||cursor.getCount()==0) {
	 				Intent intent=new Intent();
		 			intent.putExtra("filepath", file_path.getText().toString());
		 			setResult(1, intent);
		 			finish();
				}else {
					Toast.makeText(SelectFolderActivity.this, R.string.the_select_folder_exist, Toast.LENGTH_LONG).show();
				}
	 		   if (cursor!=null) {
	 			  cursor.close();
			}
	 		}
	 		
	 	}
	  private void inflateListView(File[] files) {
	  		ArrayList<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
	  		if (files!=null) {
	  			listfiles.clear();
	  			for (int i = 0; i < files.length; i++) {
	  				Map<String, Object> listItem = new HashMap<String, Object>();
	  				if (files[i].isDirectory()) {
	  					listItem.put(ITEM_COL_ICON, R.drawable.ic_settings_about);
	  					listItem.put(ITEM_COL_NAME, files[i].getName());
	  					listItems.add(listItem);
	  					listfiles.add(files[i]);
	  				} 
	  			}
	  		}
	  		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
	  				R.layout.file_list_items, LIST_COL_NAME, LIST_COL_VALUE);
	  		filelist.setAdapter(simpleAdapter);
	  		try {
	  			file_path.setText(currentParent.getCanonicalPath());
	  		} catch (IOException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	  	}
	  
      class FileListItemClickListener implements OnItemClickListener {

    		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
    				long arg3) {

    			// �û��������ļ���ֱ�ӷ��أ������κδ���
    			File file=listfiles.get(position);
    		/*	if (file.isFile()) {
    				try {
    					file_path.setText(file.getCanonicalPath());
    					return;
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}*/
               if (file.canWrite()) {
            	   File[] tmp = file.listFiles();
       			// ��ȡ�û��������б����Ӧ���ļ��У���Ϊ��ǰ�ĸ��ļ���
            	   boolean havedirectory=false;
            	   for (int i = 0; i < tmp.length; i++) {
					if (tmp[i].isDirectory()) {
						havedirectory=true;
						break;
					}
			   	}
            	   if (havedirectory) {
						currentParent = file;
		       			// ���浱ǰ�ĸ��ļ����ڵ�ȫ���ļ����ļ���
		       			currentFiles = tmp;
		       			// �ٴθ���ListView
		       			inflateListView(currentFiles);
					}
					else {
						try {
							file_path.setText(file.getCanonicalPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
    			} else {
    	          Toast.makeText(SelectFolderActivity.this, R.string.selected_file_can_not_be_write, Toast.LENGTH_SHORT).show();
    			}
    		}

    	}
      class BackButtonClickListener implements OnClickListener {

			public void onClick(View v) {
				try {
					if (!currentParent.getCanonicalPath().equals(SDcardPath)) {
						// ��ȡ��һ��Ŀ¼
						currentParent = currentParent.getParentFile();
						// �г���ǰĿ¼�������ļ�
						currentFiles = currentParent.listFiles();
						// �ٴθ���ListView
						inflateListView(currentFiles);
					}/*else {
						CallCheaterVoicesChooseActivity.this.finish();
					}*/
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
      class ScanFileImgbtnRootListener implements OnClickListener{

    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			File sdcard = new File(SDcardPath);
    			if (sdcard.exists()) {
    				currentParent = sdcard;
    				currentFiles = sdcard.listFiles();
    				// ʹ�õ�ǰĿ¼�µ�ȫ���ļ����ļ��������ListView
    				inflateListView(currentFiles);
    			}
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
