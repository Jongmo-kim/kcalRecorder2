package kcalRecorder.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kcalRecorder.model.vo.*;

public class ShowMealFrame extends JFrame{
	private ArrayList<Meal> mealList;
	public showMealConfirmButton showMealConfirmButton; 
	JTextArea foodListTextArea; 
	JScrollPane foodTextAreaWithScroll;
	JTextField nameSearchTextField;
	JTextField consonantSearchTextField;
	JTextField nameTextField;
	showMealDateSearchPanel showMealDateSearchPanel;
	JButton consonantSearchButton;
	JButton nameSearchButton;
	JButton dateSearchButton;
	public ShowMealFrame(String title,ArrayList<Meal> list) {
		super(title);
		mealList = list;
		makeUI();

	}
	private void makeUI() {
		setGridBagConstraintsLayout();
		setDefaultOptions();
	}
	private void setGridBagConstraintsLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		showFoodMainPanel showFoodMainPanel = new showFoodMainPanel();
		add(showFoodMainPanel, gbc);

		showFoodSubPanel showFoodSubPanel = new showFoodSubPanel();
		gbc.weightx = 0;
		gbc.weighty = 1;
		add(showFoodSubPanel, gbc);
	}
	private void setDefaultOptions() {
		setSize(700, 400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new GridLayout(1, 2));
	}
	public void setInvisible() {
		setVisible(false);
		setEnabled(false);
	}
	public void setVisible() {
		setVisible(true);
		setEnabled(true);
	}
	
	public class showFoodMainPanel extends JPanel{

		public showFoodMainPanel() {
			super();
			setLayout(null);
			makeUI();
			updateFoodListTextArea();
		}
		public void makeUI(){
			Font font = new Font("JetBrains Mono", Font.BOLD, 13);
			JLabel showMainLabel =new JLabel("먹은 음식들");
			foodListTextArea = new JTextArea();
			foodTextAreaWithScroll = new JScrollPane(foodListTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			showMealConfirmButton = new showMealConfirmButton("확인");
			
			foodListTextArea.setEditable(false);
			showMainLabel.setBounds(50, 10, 230, 50);
			foodTextAreaWithScroll.setBounds(50, 80, 230, 200);
			showMealConfirmButton.setBounds(120, 300, 100, 30);
			foodListTextArea.setBounds(50, 80, 230, 200);
			
			showMainLabel.setFont(font);
			showMainLabel.setBorder(BorderFactory.createEtchedBorder());
			showMainLabel.setHorizontalAlignment(JLabel.CENTER);
			setBorder(BorderFactory.createLoweredBevelBorder());
			
			add(foodTextAreaWithScroll);
			add(showMainLabel);
			add(showMealConfirmButton);
			
		}
		
	}
	public void updateFoodListTextArea(ArrayList<Meal> list) {
		StringBuilder sb = new StringBuilder();
		Iterator<Meal> iter = list.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY년MM월dd일 hh:mm");
		foodListTextArea.setText("");
		while(iter.hasNext()) {
			Meal meal = iter.next();
			Date date = meal.getDate();
			ArrayList<Food> foods = meal.getFoodArr();
			int totalKcal = 0;
			sb.append(sdf.format(date));
			sb.append('\n');
			for(Food f : foods) {
				sb.append("음식 이름 :");
				sb.append(f.getName());
				sb.append('\n');
				
				sb.append("음식 1인분 당 칼로리:");
				sb.append(f.getKcalPerOneHundred());
				sb.append('\n');
				
				sb.append("몇 인분:");
				sb.append(f.getSize());
				sb.append('\n');
				sb.append("해당 음식에 먹은 칼로리 :");
				sb.append(f.getTotalKcal());
				sb.append('\n');
				sb.append('\n');
				totalKcal += f.getTotalKcal();
			}
			sb.append(String.format("이 끼에 먹은 칼로리 :%d\n\n", totalKcal));
		}
		
		foodListTextArea.setText(sb.toString());

	}
	public void updateFoodListTextArea() {
		StringBuilder sb = new StringBuilder();
		Iterator<Meal> iter = mealList.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY년MM월dd일 hh:mm");
	
		while(iter.hasNext()) {
			Meal meal = iter.next();
			Date date = meal.getDate();
			ArrayList<Food> foods = meal.getFoodArr();
			int totalKcal = 0;
			sb.append(sdf.format(date));
			sb.append('\n');
			for(Food f : foods) {
				sb.append("음식 이름 :");
				sb.append(f.getName());
				sb.append('\n');
				
				sb.append("음식 1인분 당 칼로리:");
				sb.append(f.getKcalPerOneHundred());
				sb.append('\n');
				
				sb.append("몇 인분:");
				sb.append(f.getSize());
				sb.append('\n');
				sb.append("해당 음식에 먹은 칼로리 :");
				sb.append(f.getTotalKcal());
				sb.append('\n');
				sb.append('\n');
				totalKcal += f.getTotalKcal();
			}
			sb.append(String.format("이 끼에 먹은 칼로리 :%d\n\n", totalKcal));
		}
		
		foodListTextArea.setText(sb.toString());

	}

	


	private class showFoodSubPanel extends JPanel {

		public showFoodSubPanel() {
			super();
			setLayout(null);
			JLabel title = new JLabel("검색하기");
			title.setBounds(30, 10, 280, 30);
			title.setHorizontalAlignment(JLabel.CENTER);
			title.setBorder(BorderFactory.createEtchedBorder());
			add(title);
			
			JLabel nameSearchTitle = new JLabel("* 이름으로 음식 검색하기");
			nameSearchButton = new JButton("검색");
			nameSearchTitle.setBounds(30, 80, 280, 20);
			nameSearchTextField = new JTextField(10);
			nameSearchTextField.setBounds(30, 100, 220, 30);
			nameSearchButton.setBounds(260, 80, 60, 50);
			
			JLabel consonantSearchTitle = new JLabel("* 초성으로 음식 검색하기");
			consonantSearchButton = new JButton("검색");
			consonantSearchTitle.setBounds(30, 170, 280, 20);
			consonantSearchTextField = new JTextField(10);
			consonantSearchTextField.setBounds(30, 190, 220, 30);
			consonantSearchButton.setBounds(260, 170, 60, 50);
			
			showMealDateSearchPanel = new showMealDateSearchPanel();
			showMealDateSearchPanel.setBounds(30, 260, 300, 50);
			
			add(consonantSearchButton);
			add(nameSearchButton);
			add(nameSearchTitle);
			add(consonantSearchTitle);
			add(nameSearchTextField);
			add(consonantSearchTextField);
			add(showMealDateSearchPanel);
		}
		
	}
	public class showMealDateSearchPanel extends JPanel{

		JTextField yearTextField;
		JTextField monthTextField;
		JTextField dayTextField;
		public showMealDateSearchPanel() {
			setLayout(null);
			yearTextField = new JTextField(2);
			monthTextField = new JTextField(2);
			dayTextField = new JTextField(2);
			JLabel yearTextLabel = new JLabel("년");
			JLabel monthTextLabel = new JLabel("월");
			JLabel dayTextLabel = new JLabel("일");
			JLabel titleLabel = new JLabel("* 날짜로 검색하기");
			dateSearchButton = new JButton("검색");
			titleLabel.setBounds(0, 5, 200, 20);
			yearTextField.setBounds(0, 30, 20, 20);
			yearTextLabel.setBounds(20, 30, 20, 20);
			monthTextField.setBounds(40, 30, 20, 20);
			monthTextLabel.setBounds(60,30,20,20);
			dayTextField.setBounds(80, 30, 20, 20);
			dayTextLabel.setBounds(100,30,20,20);
			dateSearchButton.setBounds(232, 0, 60, 50);
			add(dateSearchButton);
			
			add(titleLabel);
			add(yearTextField);
			add(yearTextLabel);
			add(monthTextField);
			add(monthTextLabel);
			add(dayTextField);
			add(dayTextLabel);
		}
		public Date getDateFromTextField() {
			int year = Integer.parseInt(yearTextField.getText())+2000;
			int month = Integer.parseInt(monthTextField.getText())-1;
			int day = Integer.parseInt(dayTextField.getText());
			
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			Date date = new Date(calendar.getTimeInMillis());
			
			return date;
			
		}
		public void clearTimeFields() {
			yearTextField.setText("");
			monthTextField.setText("");
			dayTextField.setText("");
		}
	}
	public class showMealConfirmButton extends JButton{
		public showMealConfirmButton(String s) {
			super(s);
		}
	}

	public showMealDateSearchPanel getShowMealDateSearchPanel() {
		return showMealDateSearchPanel;
	}
	public showMealConfirmButton getShowMealConfirmButton() {
		return showMealConfirmButton;
	}
	public JButton getConsonantSearchButton() {
		return consonantSearchButton;
	}

	public JButton getNameSearchButton() {
		return nameSearchButton;
	}
	public JButton getDateSearchButton() {
		return dateSearchButton;
	}
	public JTextField getNameSearchTextField() {
		return nameSearchTextField;
	}

}
