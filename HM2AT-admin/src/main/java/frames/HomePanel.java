package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import javaswingdev.drawer.DrawerController;

public class HomePanel extends JPanel {
	private DrawerController drawer;

	public HomePanel(DrawerController drawer) {
		setBounds(100, 100, 653, 559);
		this.drawer=drawer;
		
		/**
		 * Create the Menu button
		 */
		ImageIcon menuIcon = new ImageIcon(getClass().getResource("/menu.png"));	
		JButton btnMenu = new JButton();
		btnMenu.setOpaque(false);
		btnMenu.setContentAreaFilled(false);
		btnMenu.setBorderPainted(false);
		btnMenu.setIcon(menuIcon);
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (HomePanel.this.drawer.isShow()) {
					HomePanel.this.drawer.hide();
				} else {
					HomePanel.this.drawer.show();
				}
			}
		});

		
		/**
		 * Create the panel.
		 */
		JLabel lblHomeWelcome = new JLabel("Welcome to the Admin tool for the HM"+"\u00B2"+"AT!");

		JLabel lblHomeInstructions = new JLabel("<html><p align=\"justify\">Please proceed to the drawer menu button located at top right corner of this window to access the functionality.</p><html>");
		

		JLabel lblHomeFunctionalities = new JLabel("<html>"
				+ "<p>Through this tool it is possible to submit:</p>\r\n"
				+ "<ul style=\"align:justify;\"> \r\n"
				+ "  <li>New models, or instances of the holistic meta-model, for adaptivity using modeling languages.</li>\r\n"
				+ "  <li>New examples for a particular model of adaptivity, via source code in a specific programming language.</li>\r\n"
				+ "  <li>New implementations of models, via source code and library,  for a specific programming language.</li>\r\n"
				+ "</ul>  \r\n"
				+ "</html>");
		
		JLabel lblImage = new JLabel("New label");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon img=new ImageIcon(this.getClass().getResource("/Logo-Admin.png"));
		lblImage.setIcon(img);
		
		GroupLayout gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(93)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblHomeInstructions, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(lblHomeFunctionalities, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(126, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(216, Short.MAX_VALUE)
					.addComponent(lblHomeWelcome, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
					.addGap(193))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(162)
					.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(248, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(46)
							.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblHomeWelcome, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addGap(25)
							.addComponent(lblHomeFunctionalities)
							.addGap(18)
							.addComponent(lblHomeInstructions, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(137, Short.MAX_VALUE))
		);
		setLayout(gl_contentPane);
	}

}
