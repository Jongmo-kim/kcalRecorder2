package kcalRecorder.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginFrame extends JFrame{
	JButton loginButton;
	JTextField idTextField;
	JTextField pwTextField;
	public LoginFrame() {
		setDefaultOption();
		makeUI();
	}
	public void makeUI() {
		idPanel id = null;
		pwPanel pw = null;
		setLayout(null);
		
		add(id = new idPanel());
		id.setBounds(10,10,200,30);
		add(pw = new pwPanel());
		pw.setBounds(10,40,200, 30);
		add(loginButton = new JButton("Login"));
		loginButton.setBounds(210, 10, 70, 60);
	}
	public void setDefaultOption() {
		setSize(300,150);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
	}
	
	public JButton getLoginButton() {
		return loginButton;
	}

	public JTextField getIdTextField() {
		return idTextField;
	}

	public JTextField getPwTextField() {
		return pwTextField;
	}
	

	private class idPanel extends JPanel{
		idPanel(){
			setLayout(new FlowLayout());
			add(new JLabel("ID "));
			add(idTextField = new JTextField(10));
			
		}
	}
	private class pwPanel extends JPanel{
		pwPanel(){
			setLayout(new FlowLayout());
			add(new JLabel("PW "));
			add(pwTextField = new JTextField(10));
		}
	}
	public void setInvisible() {
		setVisible(false);
	}
	public void setVisible() {
		setVisible(true);
	}
}
