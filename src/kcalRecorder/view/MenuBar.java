package kcalRecorder.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar {
	public JMenuBar JMenuBar; 
	private JMenuItem menuFileSave,menuFileLoad,menuServerSave,menuServerLoad,menuLogin,menuSignUp,menuExit;
	
	public MenuBar() {
		JMenuBar = new JMenuBar();

		JMenu fileMenu;
		JMenu memberMenu;
		JMenuBar.add(fileMenu = new JMenu("File"));
		JMenuBar.add(memberMenu = new JMenu("Member"));
		
		fileMenu.add(menuFileSave = new JMenuItem("파일 저장"));
		fileMenu.add(menuFileLoad = new JMenuItem("파일 불러오기"));
		fileMenu.add(menuServerSave = new JMenuItem("서버 저장"));
		fileMenu.add(menuServerLoad = new JMenuItem("서버와 동기화"));
		fileMenu.addSeparator();
		fileMenu.add(menuExit = new JMenuItem("나가기"));
		
		memberMenu.add(menuLogin = new JMenuItem("로그인"));
		memberMenu.add(menuSignUp = new JMenuItem("회원가입"));
	}
	
	public JMenuBar getJMenuBar() {
		return JMenuBar;
	}
	
	public JMenuItem getMenuSignUp() {
		return menuSignUp;
	}
	public JMenuItem getMenuLogin() {
		return menuLogin;
	}

	public JMenuItem getMenuFileSave() {
		return menuFileSave;
	}

	public JMenuItem getMenuFileLoad() {
		return menuFileLoad;
	}
	public JMenuItem getMenuServerSave() {
		return menuServerSave;
	}

	public JMenuItem getMenuServerLoad() {
		return menuServerLoad;
	}
	public JMenuItem getMenuExit() {
		return menuExit;
	}

}
