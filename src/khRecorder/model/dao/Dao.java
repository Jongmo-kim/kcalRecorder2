package khRecorder.model.dao;

import java.sql.Connection;
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

	public int serverSave(Connection conn, User loggedInUser, ArrayList<Meal> mealArr) {
		int result =0;
		PreparedStatement pstmt = null;
		
		return result;
	}
	public int insertFood(Connection conn,Food food) {
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
		int result =0;
		for(Food food : foodList) {
			if(!isExistFood(conn,food)) {
				result += insertFood(conn, food);
			}
		}
		return result;
	}	


	private boolean isExistFood(Connection conn, Food food) {
		String sql = "select * from food where name = ?";
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
	public int insertMultipleMeal(Connection conn, ArrayList<Meal> mealArr,User u ) {
		int result = 0;
		ArrayList<Food> foodArr = null;
		for(Meal meal : mealArr) {
			result += insertMeal(conn, meal, u);
			
			foodArr = meal.getFoodArr();
			setFoodfNum(conn,foodArr);
			
			result = insertFoods(conn, meal,foodArr);
		}
		return result;
	}
	private int insertFoods(Connection conn, Meal meal, ArrayList<Food> foodArr) {
		//TODO insertFoods 구현후 확인하기 
		return 0;
	}

	private void setFoodfNum(Connection conn,ArrayList<Food> foodArr) {
		int fNum = 0;
		for(Food food : foodArr) {
			if(food.getF_no() == -1) {
				fNum = getfNumByName(conn,food);
			}
			food.setF_no(fNum);
			
		}
	}

	private int getfNumByName(Connection conn, Food food) {
		String sql  ="select * from food where name = ?";
		PreparedStatement pstmt  = null;
		ResultSet rset = null;
		int fNum = -1 ;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, food.getName());
			rset = pstmt.executeQuery();
			if(rset.next()) {
				fNum = rset.getInt("f_code");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return fNum;
	}

	public int insertMeal(Connection conn, Meal meal,User u) {
		int result =0;
		PreparedStatement pstmt = null;
		String sql = "insert into meal values(meal_seq.nextval,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, meal.getSqlDate());
			pstmt.setInt(2,u.getNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			 JDBCTemplate.close(pstmt);
		}
		return result;
	}
}
