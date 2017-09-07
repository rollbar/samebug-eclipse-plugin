package com.samebug.clients.eclipse.handlers;

//import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.samebug.clients.http.Client;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow {

	public JFrame frame;
	private JTextField username;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 420, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(49, 50, 133, 26);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(49, 89, 133, 26);
		frame.getContentPane().add(lblPassword);
		
		username = new JTextField();
		username.setBounds(149, 50, 204, 26);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(149, 88, 204, 26);
		frame.getContentPane().add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Activator.getDefault().client.serverLogin(username.getText(), passwordField.getText());
				if(!Client.getKey().isEmpty()) {
					frame.setVisible(false);
				}
			}
		});
		btnLogin.setBounds(149, 138, 117, 29);
		frame.getContentPane().add(btnLogin);
	}
}
