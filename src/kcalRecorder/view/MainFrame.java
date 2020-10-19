package kcalRecorder.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MainFrame extends JFrame {
	public JButton addMealButton,showMealButton;
	public MainFrame(String s){
		super(s);
		setDefaultOptions();
		
		makeMainButton();
	}
	public JButton getAddMeal() {
		return addMealButton;
	}
	public JButton getShowMeal() {
		return showMealButton;
	}
	private void makeMainButton() {
		add(addMealButton = new JButton("먹은것 추가"));
		add(showMealButton = new JButton("먹었던 것들"));
		
	}

	public void setInvisible() {
		setVisible(false);
		setEnabled(false);
	}
	public void setVisible() {
		setVisible(true);
		setEnabled(true);
	}
	private void setDefaultOptions() {
		setSize(700,400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new GridLayout(1,2));
	}
}
