package test;

import java.util.ArrayList;
import java.util.List;

import com.leetai.modle.User;
import com.leetai.tools.DBUtils;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBUtils db = new DBUtils();
		List<User> list = new ArrayList<User>();
		
		list = db.query("select * from sys_user", User.class);
		System.out.println(list.get(0).getPassword());
	}

}
