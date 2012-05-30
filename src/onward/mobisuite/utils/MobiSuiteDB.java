package onward.mobisuite.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MobiSuiteDB extends SQLiteOpenHelper{
	
	//register information 
	public static final String KEY_Registered = "Registered";
	public static final String KEY_Name = "Name";
	public static final String KEY_Scratchcode = "Scratchcode";
	public static final String KEY_Gateway = "Gateway";
	public static final String KEY_Month = "Month";
	public static final String KEY_Year = "Year";
	public static final String KEY_Day = "Day";
	public static final String KEY_Randomnumber = "Randomnumber";
	public static final String KEY_Imei = "Imei";
	public static final String KEY_Password = "Password";
	public static final String KEY_Firsttime = "Firsttime";
	public static final String KEY_MobileNumber = "MobileNumber";
	public static final String KEY_SubId = "SubId";
	public static final String KEY_Reminder = "Reminder";
	public static final String KEY_MsgType = "KIND";
	public static final String KEY_LastMsg = "MSG";
	public static final String KEY_Preference = "preference";
	public static final String KEY_Occupation = "occupation";
	public static final String KEY_Education = "education";
	public static final String KEY_EmailID = "emailid";
	public static final String KEY_Gender = "gender";
	public static final String KEY_Location = "location";
	public static final String KEY_Brand = "brand";
	public static final String TAB_SETTINGS = "Settings";
	
	
	public static final int  VERSION=1;
	public static final String KEY_ID="_id";
	public static final String KEY_FILENAME="filename";
	public static final String KEY_FILEPATH="filepath";
	public static final String TAB_PRIVATEFLODERS="privatefolders";
	public static final String KEY_PHONENAME="phonename";
	public static final String KEY_PHONENUMBER="phonenumber";
	public static final String TAB_PRIVATECONTACTS="privatecontacts";
	public static final String KEY_LISTNAME="listname";
	public static final String KEY_LISTTYPE="listtype";
	public static final String KEY_LISTACTION="listaction";
	public static final String KEY_LISTSMS="listsms";
	public static final String TAB_CALLFILTERLIST="callfilterlist";
	public static final String KEY_CALLFILTERLISTID="listid";
	public static final String TAB_CALLFILTERNUMBER="callfilternumber";
	public static final String KEY_SMSCONTENT="smscontent";
	public static final String TAB_SMSTEMPLATES="smstemplates";
	public MobiSuiteDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public MobiSuiteDB(Context context, String name) {
		this(context, name, VERSION);
	}
	public MobiSuiteDB(Context context, String name,int version) {
		this(context, name,null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("create mobisuite database");
		
		//Create the "settings" table for register activity
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TAB_SETTINGS + " (" + 
				"Registered TEXT," + 
				"Name TEXT," + 
				"Scratchcode TEXT," + 
				"Gateway TEXT," + 
				"Month TEXT," + 
				"Year TEXT," + 
				"Day TEXT," + 
				"Randomnumber TEXT," + 
				"Imei TEXT," + 
				"Password TEXT," + 
				"Firsttime TEXT,"+
				"MobileNumber TEXT," + 
				"SubId TEXT,"+
				"Reminder TEXT," + 
				"preference Text," +
				"occupation TEXT, " +
				"education TEXT, " +
				"emailid TEXT," +
				"gender TEXT, " +
				"location TEXT, " +
				"brand TEXT"
				+");");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_PRIVATEFLODERS+"("+
				KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
				KEY_FILENAME+" TEXT,"+
				KEY_FILEPATH+" TEXT);");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_PRIVATECONTACTS+"("+
				KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
				KEY_PHONENAME+" TEXT,"+
				KEY_PHONENUMBER+" TEXT);");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_CALLFILTERLIST+"("+
				KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
				KEY_LISTNAME+" TEXT,"+
				KEY_LISTTYPE+" TEXT,"+
				KEY_LISTACTION+" TEXT,"+
				KEY_LISTSMS+" TEXT);");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_CALLFILTERNUMBER+"("+
				KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
				KEY_PHONENAME+" TEXT,"+
				KEY_PHONENUMBER+" TEXT,"+
				KEY_CALLFILTERLISTID+" TEXT);");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_SMSTEMPLATES+"("+
				KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
				KEY_SMSCONTENT+" TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TAB_SETTINGS+ ";");
		db.execSQL("DROP TABLE IF EXISTS " + TAB_PRIVATEFLODERS+ ";");
		db.execSQL("DROP TABLE IF EXISTS " + TAB_PRIVATECONTACTS+ ";");
		db.execSQL("DROP TABLE IF EXISTS " + TAB_CALLFILTERLIST+ ";");
		db.execSQL("DROP TABLE IF EXISTS " + TAB_CALLFILTERNUMBER+ ";");
		db.execSQL("DROP TABLE IF EXISTS " + TAB_SMSTEMPLATES+ ";");
		onCreate(db);
	}

}
