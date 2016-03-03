package test;

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
		Order order = new Order();
		order = MyBATISSqlSessionFactory.getSession()
				.getMapper(OrderMapper.class).selectByPrimaryKey(1);
		 System.out.println(order.getBid().getsUser().getPassword());
		 
		 
//		Bid bid = new Bid();
//		bid = MyBATISSqlSessionFactory.getSession()
// 			.getMapper(BidMapper.class).selectByPrimaryKey(1);
//		 System.out.println(gson.toJson(bid.getsUser()));
		 
		
	}

}
