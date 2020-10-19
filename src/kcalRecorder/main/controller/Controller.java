package kcalRecorder.main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import common.JDBCTemplate;
import kcalRecorder.func.file.FileController;
import kcalRecorder.model.vo.Food;
import kcalRecorder.model.vo.Meal;
import kcalRecorder.model.vo.User;
import kcalRecorder.view.MainFrame;
import kcalRecorder.view.MenuBar;
import kcalRecorder.view.ShowMealFrame;
import kcalRecorder.view.AddMealFrame;
import kcalRecorder.view.AddMealFrame.*;
import kcalRecorder.view.LoginFrame;
import kcalRecorder.view.ShowMealFrame.*;
import kcalRecorder.view.SignUpFrame;
import khRecorder.model.dao.Dao;

public class Controller {
	private ArrayList<Meal> mealArr;
	private MainFrame mainFrame;
	private kcalRecorder.view.AddMealFrame AddMealFrameInstance;
	private kcalRecorder.view.ShowMealFrame ShowMealFrameInstance;
	addMealFrameConfirmButton addMealFrameConfirmButton;
	JButton consonantSearchButton;
	JButton nameSearchButton;
	JButton dateSearchButton;
	JButton loginFrameLoginButton;
	JButton signUpFrameSignUpButton;
	MenuBar menuBar;
	FileController fileController;
	String filePath, fileName;
	User loggedInUser;
	ArrayList<Food> foodList;
	LoginFrame loginFrame;
	SignUpFrame signUpFrame;
	Dao dao;

	public Controller() {
		fileName = "data.dat";
		loggedInUser = null;
		foodList = new ArrayList<Food>();
		fileController = new FileController(filePath, fileName);
		mealArr = new ArrayList<Meal>();
		dao = new Dao();
		setTestValues();
	}

	public void setTestValues() {
		ArrayList<Food> list = new ArrayList<Food>();
		Calendar calendar = Calendar.getInstance();

		list.add(new Food(500, 1, "제육볶음"));
		list.add(new Food(200, 1, "소시지 야채볶음"));
		list.add(new Food(300, 1, "공기밥"));
		list.add(new Food(250, 1, "소고기 무국"));
		list.add(new Food(100, 1, "미역줄기 볶음"));
		Meal temp = new Meal(list);
		Date date1 = new Date();
		temp.setDate(date1);
		mealArr.add(temp);

		list.clear();

		list.add(new Food(650, 1, "돈까스"));
		list.add(new Food(200, 1, "마카로니 샐러드"));
		list.add(new Food(150, 0.5, "공기밥"));
		list.add(new Food(250, 1, "카레"));
		list.add(new Food(200, 1, "콩나물 무침"));
		temp = new Meal(list);

		calendar.set(Calendar.YEAR, 2019);
		calendar.set(Calendar.MONTH, 3);
		calendar.set(Calendar.DAY_OF_MONTH, 20);

		Date date2 = new Date(calendar.getTimeInMillis());
		temp.setDate(date2);
		mealArr.add(temp);

		list.clear();
		list.add(new Food(700, 1, "카레 돈까스"));
		list.add(new Food(200, 1, "두부 무침"));
		list.add(new Food(150, 0.5, "공기밥"));
		list.add(new Food(250, 1, "된장찌개"));
		list.add(new Food(300, 1, "조기구이"));
		temp = new Meal(list);

		calendar.set(Calendar.YEAR, 2017);
		calendar.set(Calendar.MONTH, 7);
		calendar.set(Calendar.DAY_OF_MONTH, 2);
		Date date3 = new Date(calendar.getTimeInMillis());
		temp.setDate(date3);
		mealArr.add(temp);

	}

	public void main() {
		mainFrame = new MainFrame("temp");
		setMenuBar();
		setMenuBarActionListener();
		setMainFrameActionListener();

	}

	private void setMenuBar() {
		menuBar = new MenuBar();
		mainFrame.setJMenuBar(menuBar.getJMenuBar());
	}

	private void setMenuBarActionListener() {
		JMenuItem save = menuBar.getMenuFileSave();
		JMenuItem load = menuBar.getMenuFileLoad();
		JMenuItem serverSave = menuBar.getMenuServerSave();
		JMenuItem serverLoad = menuBar.getMenuServerLoad();
		JMenuItem login = menuBar.getMenuLogin();
		JMenuItem signUp = menuBar.getMenuSignUp();

		signUp.addActionListener(actionListenerMenuSignUp());
		save.addActionListener(actionListenerMenuFileSave());
		load.addActionListener(actionListenerMenuFileLoad());
		serverSave.addActionListener(actionListenerMenuServerSave());
		serverLoad.addActionListener(actionListenerMenuServerLoad());
		login.addActionListener(actionListenerMenuLogin());
	}

	public void setMainFrameActionListener() {
		JButton addMealButton = mainFrame.getAddMeal();
		addMealButton.addActionListener(actionListenerAddMeal());

		JButton showMealButton = mainFrame.getShowMeal();
		showMealButton.addActionListener(actionListenerShowMeal());
	}

	public ActionListener actionListenerMenuSignUp() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUpFrame = new SignUpFrame();
				signUpFrameSignUpButton = signUpFrame.getLoginButton();
				signUpFrameSignUpButton.addActionListener(actionListenerSignUpButton());
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerSignUpButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (signUp() > 0) {
					JOptionPane.showMessageDialog(mainFrame, "회원가입 성공");
					signUpFrame.setInvisible();
				} else {
					JOptionPane.showMessageDialog(mainFrame, "회원가입 실패");
				}

			}
		};
		return actionListener;
	}

	private int signUp() {
		JTextField idField = signUpFrame.getIdTextField();
		JTextField pwField = signUpFrame.getPwTextField();
		JTextField nickField = signUpFrame.getNickTextField();
		String id = idField.getText();
		String pw = pwField.getText();
		String nick = nickField.getText();
		idField.setText("");
		pwField.setText("");
		nickField.setText("");
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.signUpUser(conn, id, pw, nick);
		JDBCTemplate.close(conn);
		return result;
	}

	public ActionListener actionListenerMenuLogin() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginFrame = new LoginFrame();
				loginFrameLoginButton = loginFrame.getLoginButton();
				loginFrameLoginButton.addActionListener(actionListenerLoginButton());
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerLoginButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginUser();
				if (isLoggedIn()) {
					JOptionPane.showMessageDialog(mainFrame, "로그인 성공");
					loginFrame.setInvisible();
				} else {
					JOptionPane.showMessageDialog(mainFrame, "로그인 실패");
				}
			}

		};
		return actionListener;
	}

	private boolean isLoggedIn() {
		if (loggedInUser == null)
			return false;
		return true;
	}

	private void loginUser() {
		JTextField idField = loginFrame.getIdTextField();
		JTextField pwField = loginFrame.getPwTextField();
		String id = idField.getText();
		String pw = pwField.getText();
		idField.setText("");
		pwField.setText("");
		Connection conn = JDBCTemplate.getConnection();
		loggedInUser = dao.loginUser(conn, id, pw);
		JDBCTemplate.close(conn);
	}

	public ActionListener actionListenerMenuFileSave() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileController.saveFile(mealArr)) {
					JOptionPane.showMessageDialog(mainFrame, "저장되었습니다.");
				} else {
					JOptionPane.showMessageDialog(mainFrame, "저장 실패!");
				}
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerMenuFileLoad() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mealArr = fileController.readFile();
				if (mealArr.isEmpty()) {
					JOptionPane.showMessageDialog(mainFrame, "불러오기 실패!");
				} else {
					JOptionPane.showMessageDialog(mainFrame, "불러오기 성공!");
				}
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerMenuServerSave() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isLoggedIn()) {
					if (serverSave() > 0) {
						JOptionPane.showMessageDialog(mainFrame, "저장 성공");
					} else {
						JOptionPane.showMessageDialog(mainFrame, "저장 실패");
					}
				} else {
					JOptionPane.showMessageDialog(mainFrame, "로그인이 되지 않았습니다.");
				}

			}
		};
		return actionListener;
	}

	private int serverSave() {
		Connection conn = JDBCTemplate.getConnection();
		setNotNestedFoodListFromMealArr();
		int foodResult = dao.insertMultipleFood(conn, foodList);
		int mealResult = dao.insertMultipleMeal(conn, mealArr, loggedInUser);

		
		if (foodResult == 1 || mealResult == 1) {
			commitOrRollback(conn, 1);
		} else {
			commitOrRollback(conn, 0);
		}
		JDBCTemplate.close(conn);
		return mealResult;
	}

	private void commitOrRollback(Connection conn, int result) {
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
	}

	private void setNotNestedFoodListFromMealArr() {
		for (Meal meal : mealArr) {
			ArrayList<Food> list = meal.getFoodArr();
			for (Food food : list) {
				if (isNestedFood(food)) {
					foodList.add(food);
				}
			}
		}
	}

	private boolean isNestedFood(Food ori) {
		for (Food des : foodList) {
			if (ori.getName().equals(des.getName())) {
				return false;
			}
		}
		return true;
	}

	public ActionListener actionListenerMenuServerLoad() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerMenuExit() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerAddMeal() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setInvisible();
				AddMealFrameInstance = new AddMealFrame();

				JButton confirmButton = AddMealFrameInstance.getAddMealFrameConfirmButton();
				confirmButton.addActionListener(actionListenerAddMealConfirmButton());

			}
		};
		return actionListener;
	}

	public ActionListener actionListenerShowMeal() {

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setInvisible();

				ShowMealFrameInstance = new ShowMealFrame("temp", mealArr);
				ShowMealFrameInstance.getShowMealConfirmButton().addActionListener(actionListenerShowMealReturnMain());

				consonantSearchButton = ShowMealFrameInstance.getConsonantSearchButton();
				consonantSearchButton.addActionListener(actionListenerConsonantSearchButton());

				nameSearchButton = ShowMealFrameInstance.getNameSearchButton();
				nameSearchButton.addActionListener(actionListenerNameSearchButton());

				dateSearchButton = ShowMealFrameInstance.getDateSearchButton();
				dateSearchButton.addActionListener(actionListenerDateSearchButton());

			}
		};
		return actionListener;
	}

	public ActionListener actionListenerAddMealConfirmButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible();
				AddMealFrameInstance.setInvisible();
				insertNewMeals();
			}
		};
		return actionListener;
	}

	public void insertNewMeals() {
		ArrayList<Food> list = AddMealFrameInstance.getFoodList();
		mealArr.add(new Meal(list));
		list.clear();
	}

	public ActionListener actionListenerShowMealReturnMain() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowMealFrameInstance.setInvisible();
				mainFrame.setVisible();
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerConsonantSearchButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerNameSearchButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFoodListWithSearchedName();
			}
		};
		return actionListener;
	}

	public void updateFoodListWithSearchedName() {
		JTextField textField = ShowMealFrameInstance.getNameSearchTextField();
		String valueBeIncluded = textField.getText();
		ArrayList<Meal> list = new ArrayList<Meal>();
		textField.setText("");
		for (Meal meal : mealArr) {
			if (meal.isFoodNameIncluded(valueBeIncluded)) {
				list.add(meal);
			}
		}
		ShowMealFrameInstance.updateFoodListTextArea(list);
	}

	public ActionListener actionListenerDateSearchButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFoodListWithSearchedDate();
			}
		};
		return actionListener;
	}

	public void updateFoodListWithSearchedDate() {
		showMealDateSearchPanel tempPanel = ShowMealFrameInstance.getShowMealDateSearchPanel();
		Date date = tempPanel.getDateFromTextField();
		tempPanel.clearTimeFields();
		ArrayList<Meal> list = new ArrayList<Meal>();
		for (Meal meal : mealArr) {

			if (isEqualDateInYearMonthDay(meal.getDate(), date)) {
				list.add(meal);
			}
		}

		ShowMealFrameInstance.updateFoodListTextArea(list);

	}

	public boolean isEqualDateInYearMonthDay(Date d1, Date d2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d1);
		int firstYear = calendar.get(Calendar.YEAR);
		int firstMonth = calendar.get(Calendar.MONTH);
		int firstDay = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.setTime(d2);
		int secondYear = calendar.get(Calendar.YEAR);
		int secondMonth = calendar.get(Calendar.MONTH);
		int secondDay = calendar.get(Calendar.DAY_OF_MONTH);

		if (firstYear == secondYear && firstMonth == secondMonth && firstDay == secondDay) {
			return true;
		}
		return false;
	}
}
