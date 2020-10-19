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
		
		fileMenu.add(menuFileSave = new JMenuItem("���� ����"));
		fileMenu.add(menuFileLoad = new JMenuItem("���� �ҷ�����"));
		fileMenu.add(menuServerSave = new JMenuItem("���� ����"));
		fileMenu.add(menuServerLoad = new JMenuItem("������ ����ȭ"));
		fileMenu.addSeparator();
		fileMenu.add(menuExit = new JMenuItem("������"));
		
		memberMenu.add(menuLogin = new JMenuItem("�α���"));
		memberMenu.add(menuSignUp = new JMenuItem("ȸ������"));
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
