package org.rossedth.hm2aTool_admin;

import java.awt.EventQueue;

import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;

import frames.Main;

public class App 
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/* Set the FlatLaf look and feel from https://www.formdev.com/flatlaf/themes/ */
		FlatCyanLightIJTheme.setup();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}