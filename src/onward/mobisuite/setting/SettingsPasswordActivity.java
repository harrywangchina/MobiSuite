package onward.mobisuite.setting;

import onward.mobisuite.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsPasswordActivity extends Activity{

	private Button titleback=null;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.settings_password);
    	titleback=(Button)findViewById(R.id.btn_title_back);
    	EditText currentpassword = (EditText)findViewById(R.id.currentpassword_edittext);
    	currentpassword.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				//menu.add("fiodshaf");
				//MenuItem fds = menu.getItem(2);
				//System.out.println(fds.getGroupId()+":"+fds.getItemId()+":"+fds.getOrder()+":"+fds.getTitle());
				menu.add(0, 3232, 0, "fdshfids");
			}
		});
    	
    	titleback.setOnClickListener(new TitleBackBtnOnClickListener());
    }
     @Override
    public boolean onContextItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	if (item.getItemId()==3232) {
			System.out.println("fjdsoijfdisojfiodsjfoi");
		} 
    	return super.onContextItemSelected(item);
    }
     private class TitleBackBtnOnClickListener implements OnClickListener{

 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			SettingsPasswordActivity.this.finish();
 		}
 		
 	}
}
