package onward.mobisuite;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import onward.mobisuite.utils.DatabaseManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.preference.PreferenceManager;

public class MobiSuiteGlobal {
	public static boolean register_flag=true;
	public static String PrimaryNumber="+919870832233";//"9870832233";//"9870800101";
	public static String SecondaryNumber="5676716";
	public static String svraddr="122.169.115.63:8192";//"122.169.115.64" "async.mobicopy.in";
	
	public static boolean InitWhiteList=false;
	
	public static String Registered = "0";
	public static String Name = "";
	public static String Scratchcode = "";
	public static String Gateway = "GPRS";
	public static String Month = "1";
	public static String Year = "2000";
	public static String Day = "1";
	public static String Randomnumber = "";
	public static String Imei = "";
	public static String Password = "1234";
	public static String Firsttime = "0";
	public static String MobileNumber="";
	public static String SubId="";
	public static String reminder="";
	
	public static String preference=RegistActivity.PreA;
	public static String occupation=RegistActivity.Employed;
	public static String education=RegistActivity.Graduate;
	public static String emailid="";
	public static String gender="M";
	public static String location="";
	public static String brand="";
	
	public static void SaveInitWhiteList(Context cnt)
	{
		SharedPreferences shr=PreferenceManager.getDefaultSharedPreferences(cnt);
		SharedPreferences.Editor shred=shr.edit();
		shred.putBoolean("WHITELIST", true);
		shred.commit();
	}
	
	public static boolean GetInitWhiteList(Context cnt)
	{
		SharedPreferences shr=PreferenceManager.getDefaultSharedPreferences(cnt);
		boolean ret=shr.getBoolean("WHITELIST", false);
		
		return ret;
	}
	
	public static synchronized String run(String[] cmd, String workdirectory)
			throws IOException {
		String result = "";
		try {
			ProcessBuilder builder = new ProcessBuilder(cmd);
			InputStream in = null;
			if (workdirectory != null) {
				builder.directory(new File(workdirectory));
				builder.redirectErrorStream(true);
				Process process = builder.start();
				in = process.getInputStream();
				byte[] re = new byte[1024];
				while (in.read(re) != -1)
					result = result + new String(re);
			}
			if (in != null) {
				in.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public static void savePrefereces(Context cnt) {
		DatabaseManager dm = new DatabaseManager(cnt);
		dm.updateSettings(Registered, Name, Scratchcode, Gateway, Month, Year,
				Day, Randomnumber, Imei, Password, Firsttime,MobileNumber,SubId,reminder,
				preference,occupation,education,emailid,gender,location,brand);
		dm.close();
	}
	public static void initialize(Context cnt) 
	{
		try {
			DatabaseManager dm = new DatabaseManager(cnt);
			Cursor c = dm.getSettings();
			if (c.moveToFirst()) {
				if (c.getString(0).equalsIgnoreCase("1")) {
					Registered = "1";
				}
				Name = c.getString(1);
				Scratchcode = c.getString(2);
				Gateway = c.getString(3);
				Month = c.getString(4);
				Year = c.getString(5);
				Day = c.getString(6);
				Randomnumber = c.getString(7);
				Imei = c.getString(8);
				Password = c.getString(9);
				Firsttime = c.getString(10);
				MobileNumber=c.getString(11);
				SubId = c.getString(12);
				reminder=c.getString(13);
				
				preference=c.getString(14);
				occupation=c.getString(15);
				education=c.getString(16);
				emailid=c.getString(17);
				gender=c.getString(18);
				location=c.getString(19);
				brand=c.getString(20);
			} else {
				dm.insertSettings(Registered, Name, Scratchcode, Gateway, Month,
						Year, Day, Randomnumber, Imei, Password, Firsttime,MobileNumber,SubId,reminder,
						preference,occupation,education,emailid,gender,location,brand);
			}
			c.close();
			dm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
