package frames;

import java.awt.CardLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

import javaswingdev.drawer.DrawerController;
import utils.ComboPopulator;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class ImplementationPanel extends JPanel {
	private DrawerController drawer;
	private CardLayout cardLayout;
	private JButton btnImpSave;
	private JButton btnImpCancel;
	private JPanel panelModel;
	private JTextField txtImpName;
	private JTextField txtImpSourcePath;
	private JTextField txtImpScriptPath;
	private boolean hasImpSourcePath;
	private boolean hasImpScriptPath;
	private boolean hasModelName;
	private boolean hasImpName;
	private boolean hasImpLang;
	
	/**
	 * Create the panel.
	 */
	public ImplementationPanel(DrawerController drawer,CardLayout cardLayout, JPanel contentPane) {
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
				if (ImplementationPanel.this.drawer.isShow()) {
					ImplementationPanel.this.drawer.hide();
				} else {
					ImplementationPanel.this.drawer.show();
				}
			}
		});
		
		JLabel lblNewCode = new JLabel("Adding a new implementation for a model");
		lblNewCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewCode.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		btnImpSave = new JButton("Save");
		btnImpSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Implementation saved!");
			}
		});
		
		btnImpCancel = new JButton("Cancel");
		btnImpCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImplementationPanel.this.cardLayout.show(contentPane, "Home");
			}
		});
		
		
		panelModel = new JPanel();//new ModelSelectionPanel(panelExample);
		panelModel.setBorder(new TitledBorder(null, "Model", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		
		JPanel panelImplementation = new JPanel();
		panelImplementation.setBorder(new TitledBorder(null, "Implementation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(160)
							.addComponent(lblNewCode, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(173, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(52)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnImpCancel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnImpSave))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(panelImplementation, GroupLayout.PREFERRED_SIZE, 554, GroupLayout.PREFERRED_SIZE)
							.addComponent(panelModel, GroupLayout.PREFERRED_SIZE, 554, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(47, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewCode, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(panelModel, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelImplementation, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnImpCancel)
						.addComponent(btnImpSave))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		
		
		JLabel lblImpName = new JLabel("Name");
		
		txtImpName = new JTextField();
		txtImpName.setColumns(10);
		
		JLabel lblImpLanguage = new JLabel("Language");
		JComboBox cbImpLanguage = new JComboBox();
		ComboPopulator.populateProgrammingLanguage(cbImpLanguage);
		
		JLabel lblParadigm = new JLabel("Paradigm");
		
		JComboBox cbImpParadigm = new JComboBox();
		ComboPopulator.populateProgrammingParadigm(cbImpParadigm);
		
		JLabel lblFileType = new JLabel("Type");
		
		JComboBox cbImpType = new JComboBox();
		ComboPopulator.populateCodeTypeFile(cbImpType); 
		
		JLabel lblImpSourcePath = new JLabel("Source Path");
		
		txtImpSourcePath = new JTextField();
		txtImpSourcePath.setColumns(10);
		
		JLabel lblImpScript = new JLabel("Script");
		
		txtImpScriptPath = new JTextField();
		txtImpScriptPath.setColumns(10);
		
		JButton btnImpSourceSearch = new JButton("");
		btnImpSourceSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showImpSourceDialog();				
			}
		});
		ImageIcon imgSearch = new ImageIcon(this.getClass().getResource("/search.png"));
		btnImpSourceSearch.setIcon(imgSearch);
		
		JButton btnImpScriptSearch = new JButton("");
		btnImpScriptSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showImpScriptDialog();
			}
		});
		btnImpScriptSearch.setIcon(imgSearch);
		
		GroupLayout gl_panelImplementation = new GroupLayout(panelImplementation);
		gl_panelImplementation.setHorizontalGroup(
			gl_panelImplementation.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelImplementation.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING)
						.addComponent(lblImpName)
						.addComponent(lblImpLanguage)
						.addComponent(lblFileType)
						.addComponent(lblImpSourcePath)
						.addComponent(lblImpScript))
					.addGap(18)
					.addGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelImplementation.createSequentialGroup()
							.addGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtImpScriptPath)
								.addComponent(txtImpSourcePath, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
							.addGap(24)
							.addGroup(gl_panelImplementation.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnImpScriptSearch, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnImpSourceSearch, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING, gl_panelImplementation.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_panelImplementation.createSequentialGroup()
								.addGroup(gl_panelImplementation.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(cbImpType, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cbImpLanguage, Alignment.LEADING, 0, 146, Short.MAX_VALUE))
								.addGap(54)
								.addComponent(lblParadigm)
								.addGap(18)
								.addComponent(cbImpParadigm, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addComponent(txtImpName, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelImplementation.setVerticalGroup(
			gl_panelImplementation.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelImplementation.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImpName)
						.addComponent(txtImpName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImpLanguage)
						.addComponent(cbImpLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblParadigm)
						.addComponent(cbImpParadigm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelImplementation.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelImplementation.createSequentialGroup()
							.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFileType)
								.addComponent(cbImpType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(19)
							.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblImpSourcePath)
								.addComponent(txtImpSourcePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnImpSourceSearch, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING)
						.addComponent(btnImpScriptSearch, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblImpScript)
							.addComponent(txtImpScriptPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(11))
		);
		panelImplementation.setLayout(gl_panelImplementation);
		enableComponents(panelImplementation, false);
		
		
		JLabel lblModelName = new JLabel("Name");
		
		JComboBox cbModelName = new JComboBox();
		cbModelName.addActionListener(new ActionListener() {
			private boolean found = false;
			public void actionPerformed(ActionEvent e) { 
			String s = (String) cbModelName.getSelectedItem();
            for (int i = 0; i < cbModelName.getItemCount(); i++) {
                if (cbModelName.getItemAt(i).toString().equals(s)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Added: " + s);
                cbModelName.addItem(s);
            }
            found = false;
        }
		});
		cbModelName.setEditable(true);
		
		JLabel lblModelApproach = new JLabel("Approach");
		
		JComboBox cbModelApproach = new JComboBox();
		ComboPopulator.populateModelApproach(cbModelApproach);
		

		JLabel lblModelLanguage = new JLabel("Language");
		JComboBox cbModelLanguage = new JComboBox();
		ComboPopulator.populateModelingLanguage(cbModelLanguage);
		
		GroupLayout gl_panelModel = new GroupLayout(panelModel);
		gl_panelModel.setHorizontalGroup(
			gl_panelModel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelModel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelModel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblModelName)
						.addComponent(lblModelApproach)
						.addComponent(lblModelLanguage))
					.addGap(29)
					.addGroup(gl_panelModel.createParallelGroup(Alignment.LEADING)
						.addComponent(cbModelName, GroupLayout.PREFERRED_SIZE, 423, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelModel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(cbModelLanguage, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(cbModelApproach, Alignment.LEADING, 0, 205, Short.MAX_VALUE)))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		gl_panelModel.setVerticalGroup(
			gl_panelModel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelModel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelModel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblModelName)
						.addComponent(cbModelName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelModel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbModelLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblModelLanguage))
					.addGap(14)
					.addGroup(gl_panelModel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbModelApproach, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblModelApproach))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		panelModel.setLayout(gl_panelModel);
		setLayout(groupLayout);
	}
	
	private void showImpSourceDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Choose source file for the implementation");
		int response = fileChooser.showOpenDialog(this);
		if (response == JFileChooser.APPROVE_OPTION) {
			hasImpSourcePath = true;
			File selectedFile = fileChooser.getSelectedFile();
			txtImpSourcePath.setText(selectedFile.getAbsolutePath());
			txtImpSourcePath.setEnabled(false);
		} else {
			hasImpSourcePath = false;
		}
		//btnImpSave.setEnabled(hasModelName && hasImpName && hasImpLang && hasImpSourcePath&&hasImpScriptPath);
	}
	
	private void showImpScriptDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Choose script file for the implementation");
		int response = fileChooser.showOpenDialog(this);
		if (response == JFileChooser.APPROVE_OPTION) {
			hasImpScriptPath = true;
			File selectedFile = fileChooser.getSelectedFile();
			txtImpScriptPath.setText(selectedFile.getAbsolutePath());
			txtImpScriptPath.setEnabled(false);
		} else {
			hasImpScriptPath = false;
		}
		//btnImpSave.setEnabled(hasModelName && hasImpName && hasImpLang && hasImpSourcePath&&hasImpScriptPath);
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
}
