package frames;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import javaswingdev.drawer.DrawerController;
import logic.AdaptivityModel;
import logic.AdaptivityModelImplementation;
import utils.ComboPopulator;
import utils.JSONManager;

public class ImplementationPanel extends JPanel {
	private DrawerController drawer;
	private CardLayout cardLayout;
	private JButton btnImpSave;
	private JButton btnImpCancel;

	// Elements of Model Panel
	private JPanel panelModel;
	private JComboBox cbModelName;
	private JComboBox cbModelApproach;
	private JComboBox cbModelLanguage;

	// Elements of Implementation Panel
	private JPanel panelImplementation;
	private JTextField txtImpName;
	private JTextField txtImpSourcePath;
	private JTextField txtImpScriptPath;
	private JComboBox cbImpLanguage;
	private JComboBox cbImpParadigm;
	private JComboBox cbImpType;

	// Verification properties
	private boolean hasImpType;
	private boolean hasImpSourcePath;
	private boolean hasImpScriptPath;
	private boolean hasModel;
	private boolean hasImpName;
	private boolean hasImpLang;

	private AdaptivityModel selectedModel;

	/**
	 * Create the panel.
	 */
	public ImplementationPanel(DrawerController drawer, CardLayout cardLayout, JPanel contentPane) {
		setBounds(100, 100, 653, 559);
		this.drawer = drawer;
		this.cardLayout = cardLayout;

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
		btnImpSave.setEnabled(false);
		btnImpSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean result = submitImplementation();
				if (result) {
					clearImplementationPanel();
				}
			}
		});

		btnImpCancel = new JButton("Cancel");
		btnImpCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImplementationPanel.this.cardLayout.show(contentPane, "Home");
			}
		});

		panelModel = new JPanel();// new ModelSelectionPanel(panelExample);
		panelModel.setBorder(new TitledBorder(null, "Model", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panelImplementation = new JPanel();
		panelImplementation.setBorder(
				new TitledBorder(null, "Implementation", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnMenu,
										GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(160).addComponent(lblNewCode,
										GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(173, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGap(52)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup().addComponent(btnImpCancel)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnImpSave))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(panelImplementation, GroupLayout.PREFERRED_SIZE, 554,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(panelModel, GroupLayout.PREFERRED_SIZE, 554,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(47, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(lblNewCode, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE).addGap(29)
				.addComponent(panelModel, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(panelImplementation, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout
						.createParallelGroup(Alignment.BASELINE).addComponent(btnImpCancel).addComponent(btnImpSave))
				.addContainerGap(26, Short.MAX_VALUE)));

		/**
		 * GUI to display Implementation's information
		 */
		JLabel lblImpName = new JLabel("Name");
		txtImpName = new JTextField();
		txtImpName.setColumns(10);
		txtImpName.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				changed();
			}

			public void removeUpdate(DocumentEvent e) {
				changed();
			}

			public void insertUpdate(DocumentEvent e) {
				changed();
			}

			public void changed() {
				if (txtImpName.getText().isBlank()) {
					hasImpName = false;
				} else {
					hasImpName = true;
				}

			}
		});

		JLabel lblImpLanguage = new JLabel("Language");
		cbImpLanguage = new JComboBox();
		cbImpLanguage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				hasImpLang = cbImpLanguage.getSelectedIndex() != 0;
			}
		});
		ComboPopulator.populateProgrammingLanguage(cbImpLanguage);

		JLabel lblParadigm = new JLabel("Paradigm");
		cbImpParadigm = new JComboBox();
		ComboPopulator.populateProgrammingParadigm(cbImpParadigm);

		JLabel lblFileType = new JLabel("Type");
		cbImpType = new JComboBox();
		cbImpType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				hasImpType = cbImpType.getSelectedIndex() != 0;
			}
		});
		ComboPopulator.populateCodeTypeFile(cbImpType);

		JLabel lblImpSourcePath = new JLabel("Source Path");
		txtImpSourcePath = new JTextField();
		txtImpSourcePath.setColumns(10);

		JLabel lblImpScript = new JLabel("Script");
		txtImpScriptPath = new JTextField();
		txtImpScriptPath.setColumns(10);

		// Search button for the Implementation's source file
		ImageIcon imgSearch = new ImageIcon(this.getClass().getResource("/search.png"));
		JButton btnImpSourceSearch = new JButton("");
		btnImpSourceSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showImpSourceDialog();
			}
		});
		btnImpSourceSearch.setIcon(imgSearch);

		// Search button for the Implementation's Script
		JButton btnImpScriptSearch = new JButton("");
		btnImpScriptSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showImpScriptDialog();
			}
		});
		btnImpScriptSearch.setIcon(imgSearch);

		GroupLayout gl_panelImplementation = new GroupLayout(panelImplementation);
		gl_panelImplementation.setHorizontalGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelImplementation.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING).addComponent(lblImpName)
								.addComponent(lblImpLanguage).addComponent(lblFileType).addComponent(lblImpSourcePath)
								.addComponent(lblImpScript))
						.addGap(18)
						.addGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panelImplementation.createSequentialGroup()
										.addGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING, false)
												.addComponent(txtImpScriptPath).addComponent(txtImpSourcePath,
														GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
										.addGap(24)
										.addGroup(gl_panelImplementation.createParallelGroup(Alignment.TRAILING)
												.addComponent(btnImpScriptSearch, GroupLayout.PREFERRED_SIZE, 24,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnImpSourceSearch, GroupLayout.PREFERRED_SIZE, 24,
														GroupLayout.PREFERRED_SIZE)))
								.addGroup(Alignment.TRAILING, gl_panelImplementation
										.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(Alignment.LEADING, gl_panelImplementation.createSequentialGroup()
												.addGroup(gl_panelImplementation
														.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(cbImpType, Alignment.LEADING, 0,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(cbImpLanguage, Alignment.LEADING, 0, 146,
																Short.MAX_VALUE))
												.addGap(54).addComponent(lblParadigm).addGap(18).addComponent(
														cbImpParadigm, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(txtImpName, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 436,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_panelImplementation.setVerticalGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelImplementation.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblImpName).addComponent(txtImpName, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblImpLanguage)
								.addComponent(cbImpLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblParadigm).addComponent(cbImpParadigm, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_panelImplementation.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelImplementation.createSequentialGroup()
										.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblFileType)
												.addComponent(cbImpType, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(19)
										.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblImpSourcePath).addComponent(txtImpSourcePath,
														GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)))
								.addComponent(btnImpSourceSearch, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_panelImplementation.createParallelGroup(Alignment.LEADING)
								.addComponent(btnImpScriptSearch, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelImplementation.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblImpScript).addComponent(txtImpScriptPath,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addGap(11)));
		panelImplementation.setLayout(gl_panelImplementation);
		enableComponents(panelImplementation, false);

		/**
		 * GUI to display Model's information
		 */
		JLabel lblModelName = new JLabel("Name");

		cbModelName = new JComboBox();
		ComboPopulator.populateAdaptivityModels(cbModelName);
		cbModelName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectedModel = Main.models.get(cbModelName.getSelectedIndex());
					hasModel = true;
					enableComponents(panelImplementation, true);
				}
			}
		});
		cbModelName.setEditable(true);

//		cbModelName.addActionListener(new ActionListener() {
//			private boolean found = false;
//			public void actionPerformed(ActionEvent e) { 
//			String s = (String) cbModelName.getSelectedItem();
//            for (int i = 0; i < cbModelName.getItemCount(); i++) {
//                if (cbModelName.getItemAt(i).toString().equals(s)) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                System.out.println("Added: " + s);
//                cbModelName.addItem(s);
//            }
//            found = false;
//        }
//		});

		JLabel lblModelApproach = new JLabel("Approach");
		cbModelApproach = new JComboBox();
		ComboPopulator.populateModelApproach(cbModelApproach);

		JLabel lblModelLanguage = new JLabel("Language");
		cbModelLanguage = new JComboBox();
		ComboPopulator.populateModelingLanguage(cbModelLanguage);

		GroupLayout gl_panelModel = new GroupLayout(panelModel);
		gl_panelModel.setHorizontalGroup(gl_panelModel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelModel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelModel.createParallelGroup(Alignment.LEADING).addComponent(lblModelName)
								.addComponent(lblModelApproach).addComponent(lblModelLanguage))
						.addGap(29)
						.addGroup(gl_panelModel.createParallelGroup(Alignment.LEADING)
								.addComponent(cbModelName, GroupLayout.PREFERRED_SIZE, 423, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelModel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(cbModelLanguage, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(cbModelApproach, Alignment.LEADING, 0, 205, Short.MAX_VALUE)))
						.addContainerGap(24, Short.MAX_VALUE)));
		gl_panelModel.setVerticalGroup(gl_panelModel.createParallelGroup(Alignment.LEADING).addGroup(gl_panelModel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panelModel.createParallelGroup(Alignment.BASELINE).addComponent(lblModelName).addComponent(
						cbModelName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panelModel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbModelLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblModelLanguage))
				.addGap(14)
				.addGroup(gl_panelModel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbModelApproach, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblModelApproach))
				.addContainerGap(22, Short.MAX_VALUE)));
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
		btnImpSave
				.setEnabled(hasModel && hasImpName && hasImpLang && hasImpType && hasImpSourcePath && hasImpScriptPath);
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
		btnImpSave
				.setEnabled(hasModel && hasImpName && hasImpLang && hasImpType && hasImpSourcePath && hasImpScriptPath);
	}

	private boolean submitImplementation() {
		boolean saved = false;
		AdaptivityModelImplementation implementation = new AdaptivityModelImplementation(selectedModel.getId(),
				txtImpName.getText(), cbImpLanguage.getSelectedItem().toString(),
				cbImpParadigm.getSelectedItem().toString(), cbImpType.getSelectedItem().toString(),
				Paths.get(txtImpSourcePath.getText()), Paths.get(txtImpScriptPath.getText()));
		implementation.setId(AdaptivityModelImplementation.getNextId());
		saved = JSONManager.saveImplementation(Main.repository + "\\implementations", implementation);
		JOptionPane.showMessageDialog(null, selectResultMessage(saved));
		return saved;
	}

	private String selectResultMessage(boolean result) {
		String msg;
		if (result) {
			msg = "<html><font color='green'>Implementation submitted sucessfully!</font></html>";
		} else {
			msg = "<html><font color='red'>Error while attempting submitting the model!</font></html>";
		}
		return msg;
	}

	private void clearImplementationPanel() {
		cbModelName.setSelectedIndex(0);
		cbModelLanguage.setSelectedIndex(0);
		cbModelApproach.setSelectedIndex(0);
		txtImpName.setText("");
		cbImpLanguage.setSelectedIndex(0);
		cbImpParadigm.setSelectedIndex(0);
		cbImpType.setSelectedIndex(0);
		txtImpSourcePath.setText("");
		txtImpScriptPath.setText("");
		cbModelLanguage.setSelectedIndex(0);

	}

	public void enableComponents(Container container, boolean enable) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			component.setEnabled(enable);
			if (component instanceof Container) {
				enableComponents((Container) component, enable);
			}
		}
	}
}
