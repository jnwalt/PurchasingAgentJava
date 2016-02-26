package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.leetai.modle.User;
import com.leetai.tools.DBUtils;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
		String path = "D:\\temp1\\aa\\12\\"+123;
		File file = new File(path);
		if  (!file.exists()  && !file.isDirectory())      
		{       
			file.mkdir();
		}}catch(Exception e){
			e.printStackTrace();
		}
	}

}
