package khRecorder.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import kcalRecorder.model.vo.Food;
import kcalRecorder.model.vo.Meal;
import kcalRecorder.model.vo.User;

/*
create SEQUENCE food_SEQ;
create sequence meal_seq;
create sequence meals_seq;
create SEQUENCE user__SEQ;
create SEQUENCE foods_SEQ;

 */
public class Dao {

	private User getUser(ResultSet rset) {
		User u = new User();
		try {
			u.setId(rset.getString("id"));
			u.setPw(rset.getString("pw"));
			u.setNickname(rset.getString("nickName"));
			u.setNo(rset.getInt("u_code"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	public User loginUser(Connection conn, String id, String pw) {
		PreparedStatement pstmt = null;
		String sql = "select * from user_ where id = ? and pw = ?";
		ResultSet rset = null;
		User u = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				u = getUser(rset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return u;
	}

	public int signUpUser(Connection conn, String id, String pw, String nick) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into User_ values(user__seq.nextval,?,?,?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nick);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int insertFood(Connection conn, Food food) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "insert into food values(food_seq.nextval,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, food.getName());
			pstmt.setInt(2, food.getKcalPerOneHundred());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int insertMultipleFood(Connection conn, ArrayList<Food> foodList) {
		int result = 0;
		for (Food food : foodList) {
			if (!isExistFood(conn, food)) {
				result += insertFood(conn, food);
			}
		}
		return result;
	}

	private boolean isExistFood(Connection conn, Food food) {
		String sql = "select * from food where f_name = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, food.getName());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result == 0 ? false : true;
	}

	public int insertMultipleMeal(Connection conn, ArrayList<Meal> mealArr, User u) {
		int result = 0;
		ArrayList<Food> foodArr = null;
		for (Meal meal : mealArr) {
			result += insertMeal(conn, meal, u);

			foodArr = meal.getFoodArr();
			setFoodfNum(conn, foodArr);

			result = insertFoods(conn, meal, foodArr);
		}
		return result;
	}

	private int insertFoods(Connection conn, Meal meal, ArrayList<Food> foodArr) {
		int result = 0;
		for (Food food : foodArr) {
			result += insertFoods(conn, meal, food);
		}
		return result;
	}

	private int insertFoods(Connection conn, Meal meal, Food food) {
		int result = 0;
//		controller에서 meal마다 하나씩 처리하므로 현재 seq값을 넣는다.
		String sql = "insert into foods values(meal_seq.currval,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, food.getF_no());
			pstmt.setDouble(2, food.getSize());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	private void setFoodfNum(Connection conn, ArrayList<Food> foodArr) {
		int fNum = 0;
		for (Food food : foodArr) {
			fNum = getfNumByName(conn, food);
			food.setF_no(fNum);
		}
	}

	private int getfNumByName(Connection conn, Food food) {
		String sql = "select * from food where f_name = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int fNum = -1;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, food.getName());
			rset = pstmt.executeQuery();
			if (rset.next()) {
				fNum = rset.getInt("f_code");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return fNum;
	}

	public int insertMeal(Connection conn, Meal meal, User u) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "insert into meal values(meal_seq.nextval,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, meal.getSqlDate());
			pstmt.setInt(2, u.getNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<Meal> getMealList(Connection conn, User u) {
//		데이터 베이스의 경우 foods,food,meal로 나뉘어져있고
//		자바는 food meal로 나뉘어져 있다
//		또한 food와 meal의 속성값들도 다르므로 이에대한 처신이 요구된다.
//		Java food 필수 kcal , amount , name
//		Java meal 필수 ArrayList<food>, date
//		이므로
//		foods에서 m_code를 기준으로 조회하여 저장하면 되겠다.
//		왜냐하면 foods에선 m_code를 기준으로 분류하여 저장하는데 
//		여기서 meal마다 어떤 음식이 들어갔는지 알려면 m_code를 기준으로 조회하면 된다.
//		데이터베이스에서 foods에 amount food에 f_name, kcal이 있으므로 meal까지 들어가 조회할 필요는 없다.
//		그렇게 ArrayList<Food>를 가져오고 date같은경우는 meal 에 동일하게 있으므로 그대로 넣어주면 되겠다.
//		그후 m_code마다 해당 위의 내용을 반복하여 그렇게 저장된 meal을 리턴하여 메소드를 종료한다.

		ArrayList<Meal> list = new ArrayList<Meal>();
		String sql = "select m_code from meal";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Meal tempMeal = null;
			int m_no = 0;
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				m_no = getMnoFromRset(rset);
				tempMeal = readMeal(conn,m_no);
				list.add(tempMeal);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	private int getMnoFromRset(ResultSet rset) {
		int result=0;
		try {
			result = rset.getInt("m_code");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Meal readMeal(Connection conn,int m_no) {
		Meal meal = new Meal();
		ArrayList<Food> foodList = new ArrayList<Food>();
		String sql = "select eat_date from meal where m_code = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m_no);
			rset = pstmt.executeQuery();
			meal.setDate(getDateFromMeal(rset));
			
			foodList = getFoodList(conn,m_no);
			meal.setFoodArr(foodList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return meal;
	}

	private java.sql.Date getDateFromMeal(ResultSet rset) {
		java.sql.Date date = new java.sql.Date(0);
		try {
			if(rset.next()) {
				date = rset.getDate("eat_date");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return date;
	}

	private ArrayList<Food> getFoodList(Connection conn, int m_no) {
		ArrayList<Food> list = new ArrayList<Food>();
		String sql = "select f_name,kcal,amount from foods \r\n"
				+ "join food on foods.f_code = food.f_code where m_code = ?\r\n";
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m_no);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Food f = getFoodFromRset(rset);
				list.add(f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return list;
	}

	private Food getFoodFromRset(ResultSet rset) {
		Food food = new Food();
		try {
			food.setName(rset.getString("f_name"));
			food.setKcalPerOneHundred(rset.getInt("kcal"));
			food.setSize(rset.getDouble("amount"));
			food.setTotalKcal();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return food;
	}

}
