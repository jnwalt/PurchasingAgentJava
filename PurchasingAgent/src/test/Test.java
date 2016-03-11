package test;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.leetai.mapping.OrderMapper;
import com.leetai.modle.Order;
import com.leetai.tools.MyBATISSqlSessionFactory;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gson gson = new Gson();
//		Order order = new Order();
//		order = MyBATISSqlSessionFactory.getSession()
//				.getMapper(OrderMapper.class).selectByPrimaryKey(1);
//		 System.out.println(order.getBid().getsUser().getPassword());
//		 
		List<Order> list = new ArrayList<Order>();
		list = MyBATISSqlSessionFactory
				.getSession()
				.getMapper(OrderMapper.class)
				.selectAllByStatus(11,0);	
		System.out.println(gson.toJson(list));
	}
 

}
