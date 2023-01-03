package frames;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

public class Header extends JPanel {

	/**
	 * Create the panel.
	 */
	public Header() {
		setBackground(new Color(128, 128, 128));
		ImageIcon logoAdmin = new ImageIcon(getClass().getResource("/Logo-Admin.png"));

		
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(logoAdmin);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblLogo, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblLogo, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
		);
		setLayout(groupLayout);

	}
}
