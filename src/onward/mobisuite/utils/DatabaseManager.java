package onward.mobisuite.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
	private MobiSuiteDB dbHelper = null;
	private SQLiteDatabase db = null;
	public DatabaseManager(Context context) {
		// this.context=context;
		dbHelper = new MobiSuiteDB(context, "mobisuite.db");
		db = dbHelper.getWritableDatabase();
		//dbHelper.onCreate(db);
		/*db.execSQL("CREATE TABLE IF NOT EXISTS "+MobiSuiteDB.TAB_SMSTEMPLATES+"("+
				MobiSuiteDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
				MobiSuiteDB.KEY_SMSCONTENT+" TEXT);");*/
	}
	public long InsertPrivateFolder(String filename, String filepath) {
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(MobiSuiteDB.KEY_FILENAME, filename);
		contentvalues.put(MobiSuiteDB.KEY_FILEPATH, filepath);
		long count = db.insert(MobiSuiteDB.TAB_PRIVATEFLODERS, null, contentvalues);
		return count;
	}
	public int DeletePrivateFolder(String folderid){
		int count = db.delete(MobiSuiteDB.TAB_PRIVATEFLODERS, MobiSuiteDB.KEY_ID+"=?", new String[]{folderid});
		return count;
	}
	public Cursor QueryPrivateFolder(String selection,String[] selectionArgs){
		Cursor cursor = db.query(MobiSuiteDB.TAB_PRIVATEFLODERS, new String[]{MobiSuiteDB.KEY_ID,MobiSuiteDB.KEY_FILENAME,MobiSuiteDB.KEY_FILEPATH}, selection, selectionArgs, null, null, MobiSuiteDB.KEY_ID+" asc");
	    return cursor;
	}
	public int UpdatePrivateFolder(ContentValues values,String whereClause,String[] whereArgs){
		int count = db.update(MobiSuiteDB.TAB_PRIVATEFLODERS, values, whereClause, whereArgs);
		return count;
	}
	public long InsertPrivateContacts(String phonename, String phonenumber) {
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(MobiSuiteDB.KEY_PHONENAME, phonename);
		contentvalues.put(MobiSuiteDB.KEY_PHONENUMBER, phonenumber);
		long count = db.insert(MobiSuiteDB.TAB_PRIVATECONTACTS, null, contentvalues);
		return count;
	}
	public int DeletePrivateContacts(String phoneid){
		int count = db.delete(MobiSuiteDB.TAB_PRIVATECONTACTS, MobiSuiteDB.KEY_ID+"=?", new String[]{phoneid});
		return count;
	}
	public Cursor QueryPrivateContacts(String selection,String[] selectionArgs){
		Cursor cursor = db.query(MobiSuiteDB.TAB_PRIVATECONTACTS, new String[]{MobiSuiteDB.KEY_ID,MobiSuiteDB.KEY_PHONENAME,MobiSuiteDB.KEY_PHONENUMBER}, selection, selectionArgs, null, null, MobiSuiteDB.KEY_ID+" asc");
	    return cursor;
	}
	public int UpdatePrivateContacts(ContentValues values,String whereClause,String[] whereArgs){
		int count = db.update(MobiSuiteDB.TAB_PRIVATECONTACTS, values, whereClause, whereArgs);
		return count;
	}
	public long InsertCallFilterList(String listname, String listtype,String listaction,String listsms) {
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(MobiSuiteDB.KEY_LISTNAME, listname);
		contentvalues.put(MobiSuiteDB.KEY_LISTTYPE, listtype);
		contentvalues.put(MobiSuiteDB.KEY_LISTACTION, listaction);
		contentvalues.put(MobiSuiteDB.KEY_LISTSMS, listsms);
		long count = db.insert(MobiSuiteDB.TAB_CALLFILTERLIST, null, contentvalues);
		return count;
	}
	public int DeleteCallFilterList(String listid){
		db.delete(MobiSuiteDB.TAB_CALLFILTERNUMBER, MobiSuiteDB.KEY_CALLFILTERLISTID+"=?", new String[]{listid});
		int count = db.delete(MobiSuiteDB.TAB_CALLFILTERLIST, MobiSuiteDB.KEY_ID+"=?", new String[]{listid});
		return count;
	}
	public Cursor QueryCallFilterList(String selection,String[] selectionArgs){
		Cursor cursor = db.query(MobiSuiteDB.TAB_CALLFILTERLIST, new String[]{MobiSuiteDB.KEY_ID,MobiSuiteDB.KEY_LISTNAME,MobiSuiteDB.KEY_LISTTYPE,MobiSuiteDB.KEY_LISTACTION,MobiSuiteDB.KEY_LISTSMS}, selection, selectionArgs, null, null, MobiSuiteDB.KEY_ID+" asc");
	    return cursor;
	}
	public int UpdateCallFilterList(ContentValues values,String whereClause,String[] whereArgs){
		int count = db.update(MobiSuiteDB.TAB_CALLFILTERLIST, values, whereClause, whereArgs);
		return count;
	}
	public long InsertCallFilterNumber(String phonename, String phonenumber,String listid) {
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(MobiSuiteDB.KEY_PHONENAME, phonename);
		contentvalues.put(MobiSuiteDB.KEY_PHONENUMBER, phonenumber);
		contentvalues.put(MobiSuiteDB.KEY_CALLFILTERLISTID, listid);
		long count = db.insert(MobiSuiteDB.TAB_CALLFILTERNUMBER, null, contentvalues);
		return count;
	}
	public int DeleteCallFilterNumber(String numberid){
		int count = db.delete(MobiSuiteDB.TAB_CALLFILTERNUMBER, MobiSuiteDB.KEY_ID+"=?", new String[]{numberid});
		return count;
	}
	public Cursor QueryCallFilterNumber(String selection,String[] selectionArgs){
		Cursor cursor = db.query(MobiSuiteDB.TAB_CALLFILTERNUMBER, new String[]{MobiSuiteDB.KEY_ID,MobiSuiteDB.KEY_PHONENAME,MobiSuiteDB.KEY_PHONENUMBER,MobiSuiteDB.KEY_CALLFILTERLISTID}, selection, selectionArgs, null, null, MobiSuiteDB.KEY_ID+" asc");
	    return cursor;
	}
	public int UpdateCallFilterNumber(ContentValues values,String whereClause,String[] whereArgs){
		int count = db.update(MobiSuiteDB.TAB_CALLFILTERNUMBER, values, whereClause, whereArgs);
		return count;
	}
	public long InsertSMSTemplates(String smscontent) {
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(MobiSuiteDB.KEY_SMSCONTENT, smscontent);
		long count = db.insert(MobiSuiteDB.TAB_SMSTEMPLATES, null, contentvalues);
		return count;
	}
	public int DeleteSMSTemplates(String smscontentid){
		int count = db.delete(MobiSuiteDB.TAB_SMSTEMPLATES, MobiSuiteDB.KEY_ID+"=?", new String[]{smscontentid});
		return count;
	}
	public Cursor QuerySMSTemplates(String selection,String[] selectionArgs){
		Cursor cursor = db.query(MobiSuiteDB.TAB_SMSTEMPLATES, new String[]{MobiSuiteDB.KEY_ID,MobiSuiteDB.KEY_SMSCONTENT}, selection, selectionArgs, null, null, MobiSuiteDB.KEY_ID+" asc");
	    return cursor;
	}
	public int UpdateSMSTemplates(ContentValues values,String whereClause,String[] whereArgs){
		int count = db.update(MobiSuiteDB.TAB_SMSTEMPLATES, values, whereClause, whereArgs);
		return count;
	}
	public void close() {
		if (dbHelper != null) {
			dbHelper.close();
			dbHelper = null;
		}
		if (db != null) {
			db.close();
			db = null;
		}
	}
	public boolean updateSettings(String registered, String name,
			String scratchcode, String gateway, String month, String year,
			String day, String randomnumber, String imei, String password,
			String firsttime, String mobileNumber, String subId,
			String reminder, String preference, String occupation,
			String education, String emailid, String gender, String location,
			String brand) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(MobiSuiteDB.KEY_Registered, registered);
		initialValues.put(MobiSuiteDB.KEY_Name, name);
		initialValues.put(MobiSuiteDB.KEY_Scratchcode, scratchcode);
		initialValues.put(MobiSuiteDB.KEY_Gateway, gateway);
		initialValues.put(MobiSuiteDB.KEY_Month, month);
		initialValues.put(MobiSuiteDB.KEY_Year, year);
		initialValues.put(MobiSuiteDB.KEY_Day, day);
		initialValues.put(MobiSuiteDB.KEY_Randomnumber, randomnumber);
		initialValues.put(MobiSuiteDB.KEY_Imei, imei);
		initialValues.put(MobiSuiteDB.KEY_Password, password);
		initialValues.put(MobiSuiteDB.KEY_Firsttime, firsttime);
		initialValues.put(MobiSuiteDB.KEY_MobileNumber, mobileNumber);
		initialValues.put(MobiSuiteDB.KEY_SubId, subId);
		initialValues.put(MobiSuiteDB.KEY_Reminder, reminder);
		
		initialValues.put(MobiSuiteDB.KEY_Preference, preference);
		initialValues.put(MobiSuiteDB.KEY_Occupation, occupation);
		initialValues.put(MobiSuiteDB.KEY_Education, education);
		initialValues.put(MobiSuiteDB.KEY_EmailID, emailid);
		initialValues.put(MobiSuiteDB.KEY_Gender, gender);
		initialValues.put(MobiSuiteDB.KEY_Location, location);
		initialValues.put(MobiSuiteDB.KEY_Brand, brand);
		return db.update(MobiSuiteDB.TAB_SETTINGS, initialValues, null, null) > 0;
	}
	public Cursor getSettings() {
		return db.query(MobiSuiteDB.TAB_SETTINGS, new String[] { 
				MobiSuiteDB.KEY_Registered,
				MobiSuiteDB.KEY_Name, 
				MobiSuiteDB.KEY_Scratchcode, 
				MobiSuiteDB.KEY_Gateway, 
				MobiSuiteDB.KEY_Month, 
				MobiSuiteDB.KEY_Year,
				MobiSuiteDB.KEY_Day, 
				MobiSuiteDB.KEY_Randomnumber, 
				MobiSuiteDB.KEY_Imei, 
				MobiSuiteDB.KEY_Password,
				MobiSuiteDB.KEY_Firsttime,
				MobiSuiteDB.KEY_MobileNumber,
				MobiSuiteDB.KEY_SubId,
				MobiSuiteDB.KEY_Reminder,
				MobiSuiteDB.KEY_Preference,
				MobiSuiteDB.KEY_Occupation,
				MobiSuiteDB.KEY_Education,
				MobiSuiteDB.KEY_EmailID, 
				MobiSuiteDB.KEY_Gender,
				MobiSuiteDB.KEY_Location,
				MobiSuiteDB.KEY_Brand}, null, null, null, null, null);
	}
	public long insertSettings(String registered, String name,
			String scratchcode, String gateway, String month, String year,
			String day, String randomnumber, String imei, String password,
			String firsttime, String mobileNumber, String subId,
			String reminder, String preference, String occupation,
			String education, String emailid, String gender, String location,
			String brand) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(MobiSuiteDB.KEY_Registered, registered);
		initialValues.put(MobiSuiteDB.KEY_Name, name);
		initialValues.put(MobiSuiteDB.KEY_Scratchcode, scratchcode);
		initialValues.put(MobiSuiteDB.KEY_Gateway, gateway);
		initialValues.put(MobiSuiteDB.KEY_Month, month);
		initialValues.put(MobiSuiteDB.KEY_Year, year);
		initialValues.put(MobiSuiteDB.KEY_Day, day);
		initialValues.put(MobiSuiteDB.KEY_Randomnumber, randomnumber);
		initialValues.put(MobiSuiteDB.KEY_Imei, imei);
		initialValues.put(MobiSuiteDB.KEY_Password, password);
		initialValues.put(MobiSuiteDB.KEY_Firsttime, firsttime);
		initialValues.put(MobiSuiteDB.KEY_MobileNumber, mobileNumber);
		initialValues.put(MobiSuiteDB.KEY_SubId, subId);
		initialValues.put(MobiSuiteDB.KEY_Reminder, reminder);
		
		initialValues.put(MobiSuiteDB.KEY_Preference, preference);
		initialValues.put(MobiSuiteDB.KEY_Occupation, occupation);
		initialValues.put(MobiSuiteDB.KEY_Education, education);
		initialValues.put(MobiSuiteDB.KEY_EmailID, emailid);
		initialValues.put(MobiSuiteDB.KEY_Gender, gender);
		initialValues.put(MobiSuiteDB.KEY_Location, location);
		initialValues.put(MobiSuiteDB.KEY_Brand, brand);

		return db.insert(MobiSuiteDB.TAB_SETTINGS, null, initialValues);
	}
}
