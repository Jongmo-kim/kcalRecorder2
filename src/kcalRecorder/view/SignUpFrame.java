package kcalRecorder.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SignUpFrame extends JFrame{
	JButton signUpButton;
	JTextField idTextField;
	JTextField pwTextField;
	JTextField nickTextField;
	public SignUpFrame() {
		setDefaultOption();
		makeUI();
	}
	public void makeUI() {
		idPanel id = null;
		pwPanel pw = null;
		nickPanel nick = null;
		setLayout(null);
		
		add(id = new idPanel());
		id.setBounds(10,10,200,30);
		add(pw = new pwPanel());
		pw.setBounds(10,40,200, 30);
		add(nick = new nickPanel());
		nick.setBounds(10, 70, 200, 30);
		add(signUpButton = new JButton("SignUp"));
		signUpButton.setBounds(210, 10, 120, 60);
	}
	public void setDefaultOption() {
		setSize(300,150);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public JButton getLoginButton() {
		return signUpButton;
	}

	public JTextField getIdTextField() {
		return idTextField;
	}

	public JTextField getPwTextField() {
		return pwTextField;
	}
	public JTextField getNickTextField() {
		return nickTextField;
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
	private class nickPanel extends JPanel{
		nickPanel(){
			setLayout(new FlowLayout());
			add(new JLabel("NICKNAME "));
			add(nickTextField = new JTextField(10));
		}
	}
	public void setInvisible() {
		setVisible(false);
	}
	public void setVisible() {
		setVisible(true);
	}
}
