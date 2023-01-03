package frames;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import javaswingdev.drawer.DrawerController;
import utils.ComboPopulator;
import javax.swing.border.TitledBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ExamplePanel extends JPanel {
	private DrawerController drawer;
	private CardLayout cardLayout;

	private JLabel lblExampleTitle;
	private JLabel lblExampleName;
	private JTextField txtExampleName;
	private JComboBox cbExampleLanguage;
	private JLabel lblExampleSourcePath;
	private JTextField txtExampleSourcePath;
	private JButton btnExampleSourceSearch;
	private JButton btnExampleCancel;
	private JButton btnExampleSubmit;
	private boolean hasExampleName;
	private boolean hasExampleLang;
	private boolean hasExampleSourcePath;
	private JButton btnMenu;
	private JLabel lblExampleLanguage;
	private JLabel lblExampleParadigm;
	private JComboBox cbExampleParadigm;
	private JLabel lblExampleDesc;
	private JPanel panelExample;
	private JPanel panelModel;
	private JLabel lblExampleNameError;
	
	
	public ExamplePanel(DrawerController drawer, CardLayout cardLayout, JPanel contentPane) {
		setBounds(100, 100, 653, 559);
		this.drawer=drawer;
		this.cardLayout=cardLayout;
		
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
				if (drawer.isShow()) {
					drawer.hide();
				} else {
					drawer.show();
				}
			}
		});
		
		
		/**
		 * Create the panel.
		 */
		lblExampleTitle = new JLabel("Adding a new example for the " + "HM" + "\u00B2" + "AT ");
		lblExampleTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblExampleTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		ImageIcon imgSearch = new ImageIcon(this.getClass().getResource("/search.png"));
		btnExampleSourceSearch = new JButton("");
		
		btnExampleCancel = new JButton("Cancel");
		btnExampleCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "Home");
			}
		});

		btnExampleSubmit = new JButton("Save");
		btnExampleSubmit.setEnabled(false);
		btnExampleSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Example saved!");
			}
		});
		
		panelExample = new JPanel();
		panelExample.setBorder(new TitledBorder(null, "Example", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panelModel = new ModelSelectionPanel(panelExample);
		panelModel.setBorder(new TitledBorder(null, "Model", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		GroupLayout gl_newExamplePanel = new GroupLayout(this);
		gl_newExamplePanel.setHorizontalGroup(
			gl_newExamplePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newExamplePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(106)
					.addComponent(lblExampleTitle, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(191, Short.MAX_VALUE))
				.addGroup(gl_newExamplePanel.createSequentialGroup()
					.addGap(38)
					.addGroup(gl_newExamplePanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panelExample, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelModel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_newExamplePanel.createSequentialGroup()
							.addGap(408)
							.addComponent(btnExampleCancel)
							.addGap(18)
							.addComponent(btnExampleSubmit)))
					.addGap(67))
		);
		gl_newExamplePanel.setVerticalGroup(
			gl_newExamplePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newExamplePanel.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_newExamplePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_newExamplePanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_newExamplePanel.createSequentialGroup()
							.addGap(50)
							.addComponent(lblExampleTitle, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
					.addGap(39)
					.addComponent(panelModel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelExample, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_newExamplePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnExampleCancel)
						.addComponent(btnExampleSubmit))
					.addContainerGap())
		);
		
		lblExampleName = new JLabel("Name");
		
		lblExampleDesc = new JLabel("Description");
		
		txtExampleName = new JTextField();
		txtExampleName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!txtExampleName.getText().isBlank()) {
					hasExampleName = true;
					txtExampleName.putClientProperty("JComponent.outline", null);
				} else {
					txtExampleName.putClientProperty("JComponent.outline", "error");
					lblExampleNameError.setText("Example's name is not valid.");
					lblExampleNameError.setVisible(true);
					hasExampleName = false;
				}
				btnExampleSubmit.setEnabled(hasExampleName && hasExampleLang && hasExampleSourcePath);
			}
			@Override
			public void focusGained(FocusEvent e) {
				lblExampleNameError.setVisible(false);
			}

		});
		txtExampleName.setColumns(10);
		
		lblExampleLanguage = new JLabel("Language");
		
		cbExampleLanguage = new JComboBox();
		cbExampleLanguage.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtExampleName.getText().isBlank()) {
					txtExampleName.putClientProperty("JComponent.outline", "error");
					lblExampleNameError.setText("Model's name is not valid.");
					lblExampleNameError.setVisible(true);
				} else {
					txtExampleName.putClientProperty("JComponent.outline", null);
				}
			}
		});
		cbExampleLanguage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				hasExampleLang = cbExampleLanguage.getSelectedIndex() != 0;
				if (hasExampleLang && hasExampleName) {
					btnExampleSourceSearch.setEnabled(true);
				} else {
					btnExampleSourceSearch.setEnabled(false);
				}
				btnExampleSubmit.setEnabled(hasExampleLang && hasExampleName && hasExampleSourcePath);
			}
		});
		cbExampleLanguage.setMaximumRowCount(4);
		ComboPopulator.populateProgrammingLanguage(cbExampleLanguage);
		
		lblExampleParadigm = new JLabel("Paradigm");
		
		cbExampleParadigm = new JComboBox();
		ComboPopulator.populateProgrammingParadigm(cbExampleParadigm);
		
		lblExampleSourcePath = new JLabel("Source Path");
		
		txtExampleSourcePath = new JTextField();
		txtExampleSourcePath.setEditable(false);
		txtExampleSourcePath.setColumns(10);
		
		JTextArea txtExampleDescription = new JTextArea();
		txtExampleDescription.setLineWrap(true);
		

		btnExampleSourceSearch.setIcon(imgSearch);
		btnExampleSourceSearch.setEnabled(false);
		btnExampleSourceSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModelSourceDialog();
			}
		});
		
		lblExampleNameError = new JLabel("New label");
		lblExampleNameError.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblExampleNameError.setForeground(Color.RED);
		lblExampleNameError.setVisible(false);
		
		GroupLayout gl_panel = new GroupLayout(panelExample);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblExampleName)
							.addGap(36))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblExampleDesc)
								.addComponent(lblExampleSourcePath)
								.addComponent(lblExampleLanguage))
							.addGap(12)))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblExampleNameError)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtExampleSourcePath, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExampleSourceSearch, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(cbExampleLanguage, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
							.addComponent(lblExampleParadigm)
							.addGap(26)
							.addComponent(cbExampleParadigm, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtExampleDescription, 0, 0, Short.MAX_VALUE)
						.addComponent(txtExampleName, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblExampleName)
						.addComponent(txtExampleName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblExampleDesc)
							.addGap(32))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblExampleNameError)
							.addGap(18)
							.addComponent(txtExampleDescription, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(18)
							.addComponent(lblExampleLanguage))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(cbExampleParadigm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(cbExampleLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblExampleParadigm)))))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblExampleSourcePath)
							.addComponent(txtExampleSourcePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnExampleSourceSearch))
					.addGap(24))
		);
		panelExample.setLayout(gl_panel);
		enableComponents(panelExample, false);
		setLayout(gl_newExamplePanel);
	}
	
    public void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }
    
	private void showModelSourceDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Choose source file for the model");
		int response = fileChooser.showOpenDialog(this);
		if (response == JFileChooser.APPROVE_OPTION) {
			hasExampleSourcePath = true;
			File selectedFile = fileChooser.getSelectedFile();
			txtExampleSourcePath.setText(selectedFile.getAbsolutePath());
			txtExampleSourcePath.setEnabled(false);
		} else {
			hasExampleSourcePath = false;
		}
		btnExampleSubmit.setEnabled(hasExampleName && hasExampleLang&& hasExampleSourcePath);
	}
}
