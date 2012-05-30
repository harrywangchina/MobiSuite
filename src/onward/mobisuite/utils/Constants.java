package onward.mobisuite.utils;

import android.net.Uri;
import android.provider.Contacts.People;
import android.provider.Contacts.Phones;
import android.provider.ContactsContract;


public class Constants {
	public  static class ConstantsAndroid1_6{
		public static final Uri CONTACT_CONTENT_URI=People.CONTENT_URI;
		public static final Uri PHONE_CONTENT_URI=Phones.CONTENT_URI;
		public static final String PEOPLE_ID=People._ID;
		public static final String PEOPLE_NAME=People.NAME;
		public static final String PEOPLE_NUMBER=People.NUMBER;
		public static final String PHONES_PERSON_ID= Phones.PERSON_ID;
		public static final String PHONES_NUMBER=Phones.NUMBER;
	}
	public static class ConstantsAndroid2_X{
		public  static final  Uri CONTACT_CONTENT_URI=ContactsContract.Contacts.CONTENT_URI;
		public  static final Uri PHONE_CONTENT_URI=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		public static final String PEOPLE_ID=ContactsContract.Contacts._ID;
		public static final String PEOPLE_NAME=ContactsContract.Contacts.DISPLAY_NAME;
		public static final String PEOPLE_NUMBER=ContactsContract.Contacts.HAS_PHONE_NUMBER;
		public static final String PHONES_PERSON_ID=ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		public static final String PHONES_NUMBER=ContactsContract.CommonDataKinds.Phone.NUMBER;
    }
	public static Uri GetCONTACT_CONTENT_URI(int i){
		if (i==0) {
			return ConstantsAndroid1_6.CONTACT_CONTENT_URI;
		} else {
			return ConstantsAndroid2_X.CONTACT_CONTENT_URI;
		}
	}
	public static Uri GetPHONE_CONTENT_URI(int i){
		if (i==0) {
			return ConstantsAndroid1_6.PHONE_CONTENT_URI;
		} else {
			return ConstantsAndroid2_X.PHONE_CONTENT_URI;
		}
	}
	public static String GetPEOPLE_ID(int i){
		if (i==0) {
			return ConstantsAndroid1_6.PEOPLE_ID;
		} else {
			return ConstantsAndroid2_X.PEOPLE_ID;
		}
	}
	public static String GetPEOPLE_NAME(int i){
		if (i==0) {
			return ConstantsAndroid1_6.PEOPLE_NAME;
		} else {
			return ConstantsAndroid2_X.PEOPLE_NAME;
		}
	}
	public static String GetPEOPLE_NUMBER(int i){
		if (i==0) {
			return ConstantsAndroid1_6.PEOPLE_NUMBER;
		} else {
			return ConstantsAndroid2_X.PEOPLE_NUMBER;
		}
	}
	public static String GetPHONES_PERSON_ID(int i){
		if (i==0) {
			return ConstantsAndroid1_6.PHONES_PERSON_ID;
		} else {
			return ConstantsAndroid2_X.PHONES_PERSON_ID;
		}
	}
	public static String GetPHONES_NUMBER(int i){
		if (i==0) {
			return ConstantsAndroid1_6.PHONES_NUMBER;
		} else {
			return ConstantsAndroid2_X.PHONES_NUMBER;
		}
	}
	
}
