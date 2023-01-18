package frames;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import javaswingdev.drawer.DrawerController;
import logic.AdaptivityModelImplementation;
import logic.ImplementationExample;
import utils.ComboPopulator;
import utils.JSONManager;

public class ExamplePanel extends JPanel {
	private DrawerController drawer;
	private CardLayout cardLayout;
	private JButton btnExampleSourceSearch;
	private JButton btnExampleCancel;
	private JButton btnExampleSubmit;
	
	// Elements of Implementation Panel
	private JPanel panelImplementation;
	private JComboBox cbImplementationName;
	private JComboBox cbImplementationLang;
	private JComboBox cbImplementationLang_1;
	private JComboBox cbImplementationParadigm;
	
	// Elements of Example Panel
	private JPanel panelExample;
	private JTextField txtExampleName;
	private JLabel lblExampleNameError;
	private JTextArea txtExampleDescription;
	private JComboBox cbExampleLanguage;
	private JComboBox cbExampleParadigm;
	private JTextField txtExampleSourcePath;
	
	// Verification properties	
	private boolean hasImplementation;
	private boolean hasExampleName;
	private boolean hasExampleLang;
	private boolean hasExampleSourcePath;
	private JButton btnMenu;
	
	private boolean namefilter;
	private boolean langFilter;
	private boolean paraFilter;
	
	private AdaptivityModelImplementation selectedImplementation;

	
	
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
		JLabel lblExampleTitle = new JLabel("Adding a new example for the " + "HM" + "\u00B2" + "AT ");
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
				boolean result = submitExample();
				if (result) {
					clearExamplePanel();
				}
			}
		});
		
		panelExample = new JPanel();
		panelExample.setBorder(new TitledBorder(null, "Example", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//panelModel = new ModelSelectionPanel(panelExample);
		panelImplementation = new JPanel();
		panelImplementation.setBorder(new TitledBorder(null, "Implementation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		GroupLayout gl_newExamplePanel = new GroupLayout(this);
		gl_newExamplePanel.setHorizontalGroup(
			gl_newExamplePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newExamplePanel.createSequentialGroup()
					.addGroup(gl_newExamplePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_newExamplePanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(106)
							.addComponent(lblExampleTitle, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_newExamplePanel.createSequentialGroup()
							.addGap(38)
							.addGroup(gl_newExamplePanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panelImplementation, 0, 0, Short.MAX_VALUE)
								.addGroup(gl_newExamplePanel.createSequentialGroup()
									.addGap(408)
									.addComponent(btnExampleCancel)
									.addGap(18)
									.addComponent(btnExampleSubmit))
								.addComponent(panelExample, GroupLayout.PREFERRED_SIZE, 576, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(39, Short.MAX_VALUE))
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
					.addComponent(panelImplementation, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelExample, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_newExamplePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnExampleCancel)
						.addComponent(btnExampleSubmit))
					.addContainerGap())
		);
		
		JLabel lblImplementationName = new JLabel("Name");
		cbImplementationName = new JComboBox();
		ComboPopulator.populateAdaptivityModelImplementations(cbImplementationName);
		cbImplementationName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectedImplementation = Main.implementations.get(cbImplementationName.getSelectedIndex());
					hasImplementation = true;
					cbImplementationLang.setSelectedItem(selectedImplementation.getProgrammingLang());;
					cbImplementationParadigm.setSelectedItem(selectedImplementation.getParadigm());
					enableComponents(panelExample, true);
				}
				namefilter=true;
			}
		});
		cbImplementationName.addActionListener(new ActionListener() {
			private boolean found = false;
			public void actionPerformed(ActionEvent e) { 
			String s = (String) cbImplementationName.getSelectedItem();
            for (int i = 0; i < cbImplementationName.getItemCount(); i++) {
                if (cbImplementationName.getItemAt(i).toString().equals(s)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Added: " + s);
                cbImplementationName.addItem(s);
            }
            found = false;
        }
		});
		cbImplementationName.setEditable(true);
		
		JLabel lblImplementationLang = new JLabel("Language");
		cbImplementationLang = new JComboBox();
		cbImplementationLang_1 = new JComboBox();
		ComboPopulator.populateProgrammingLanguage(cbImplementationLang_1);
		cbImplementationLang_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbImplementationLang.getSelectedIndex()==-1) {
					langFilter=false;
				}else {
					langFilter=true;
				}
				//filterCombo(cbImplementationName, langFilter,paraFilter);
				enableComponents(panelExample,namefilter&&langFilter&&paraFilter);
			}
		});
		
		JLabel lblImplementationParadigm = new JLabel("Paradigm");
		cbImplementationParadigm = new JComboBox();;
		ComboPopulator.populateProgrammingParadigm(cbImplementationParadigm);
		cbImplementationParadigm.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbImplementationParadigm.getSelectedIndex()==-1) {
					paraFilter=false;
				}else {
					paraFilter=true;
				}
				enableComponents(panelExample,namefilter&&langFilter&&paraFilter);
			}
		});	
		
		GroupLayout gl_panelModel = new GroupLayout(panelImplementation);
		gl_panelModel.setHorizontalGroup(
			gl_panelModel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelModel.createSequentialGroup()
					.addContainerGap(35, Short.MAX_VALUE)
					.addGroup(gl_panelModel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblImplementationLang)
						.addComponent(lblImplementationName)
						.addComponent(lblImplementationParadigm))
					.addGap(18)
					.addGroup(gl_panelModel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelModel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(cbImplementationName, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(cbImplementationLang_1, 0, 426, Short.MAX_VALUE))
						.addComponent(cbImplementationParadigm, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		gl_panelModel.setVerticalGroup(
			gl_panelModel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelModel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelModel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImplementationName)
						.addComponent(cbImplementationName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelModel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImplementationLang)
						.addComponent(cbImplementationLang_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelModel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImplementationParadigm)
						.addComponent(cbImplementationParadigm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		
		panelImplementation.setLayout(gl_panelModel);
		
		
		
		JLabel lblExampleName = new JLabel("Name");
		
		JLabel lblExampleDesc = new JLabel("Description");
		
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
		
		JLabel lblExampleLanguage = new JLabel("Language");
		
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
		
		JLabel lblExampleParadigm = new JLabel("Paradigm");
		
		cbExampleParadigm = new JComboBox();
		ComboPopulator.populateProgrammingParadigm(cbExampleParadigm);
		
		JLabel lblExampleSourcePath = new JLabel("Source Path");
		
		txtExampleSourcePath = new JTextField();
		txtExampleSourcePath.setEditable(false);
		txtExampleSourcePath.setColumns(10);
		
		txtExampleDescription = new JTextArea();
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
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(40)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblExampleDesc)
								.addComponent(lblExampleName)
								.addComponent(lblExampleLanguage)
								.addComponent(lblExampleSourcePath))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
									.addComponent(txtExampleSourcePath, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnExampleSourceSearch, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(cbExampleLanguage, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
									.addComponent(lblExampleParadigm)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbExampleParadigm, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtExampleName)
								.addComponent(txtExampleDescription, GroupLayout.PREFERRED_SIZE, 414, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(140)
							.addComponent(lblExampleNameError)))
					.addGap(24))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtExampleName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblExampleName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblExampleNameError)
					.addGap(15)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtExampleDescription, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblExampleDesc))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbExampleLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbExampleParadigm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblExampleLanguage)
						.addComponent(lblExampleParadigm))
					.addGap(13)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtExampleSourcePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblExampleSourcePath))
						.addComponent(btnExampleSourceSearch))
					.addGap(29))
		);
		panelExample.setLayout(gl_panel);
		enableComponents(panelExample, false);
		setLayout(gl_newExamplePanel);
	}
	
	protected boolean submitExample() {
		boolean saved = false;
		ImplementationExample example = new ImplementationExample(txtExampleName.getText(), selectedImplementation.getId(),
				txtExampleDescription.getText(),cbExampleLanguage.getSelectedItem().toString(),
				cbExampleParadigm.getSelectedItem().toString(), Paths.get(txtExampleSourcePath.getText()));
		example.setId(ImplementationExample.getNextId());
		saved = JSONManager.saveExample(Main.repository + "\\examples", example);
		JOptionPane.showMessageDialog(null, selectResultMessage(saved));
		return saved;
	}
	
	private String selectResultMessage(boolean result) {
		String msg;
		if (result) {
			msg = "<html><font color='green'>Example submitted sucessfully!</font></html>";
		} else {
			msg = "<html><font color='red'>Error while attempting submitting the example!</font></html>";
		}
		return msg;
	}


	private void clearExamplePanel() {
		cbImplementationName.setSelectedIndex(0);
		cbImplementationLang_1.setSelectedIndex(0);
		cbImplementationParadigm.setSelectedIndex(0);
		
		txtExampleName.setText("");
		txtExampleDescription.setText("");
		
		cbExampleLanguage.setSelectedIndex(0);
		cbExampleLanguage.setSelectedIndex(0);
		cbExampleParadigm.setSelectedIndex(0);
		txtExampleSourcePath.setText("");
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
