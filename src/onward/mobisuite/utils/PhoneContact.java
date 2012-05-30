package onward.mobisuite.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class PhoneContact implements Serializable{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String name="";
  private ArrayList<String> phones=null;

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public ArrayList<String> getPhoneArrayList() {
	return phones;
}
public void setPhoneArrayList(ArrayList<String> phoneArrayList) {
	this.phones = phoneArrayList;
}
@Override
public String toString() {
	return "PhoneContact [name=" + name + ", phones="
			+ phones + "]";
}
 
}
