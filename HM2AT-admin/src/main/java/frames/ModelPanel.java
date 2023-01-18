package frames;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.fasterxml.jackson.databind.ObjectMapper;

import javaswingdev.drawer.DrawerController;
import logic.AdaptivityModel;
import utils.ComboPopulator;
import utils.JSONManager;

public class ModelPanel extends JPanel {
	private DrawerController drawer;

	private JLabel lblModelTitle;
	private JLabel lblModelName;
	private JTextField txtModelName;
	private JTextArea txtModelDesc;
	private JComboBox cbModelLanguage;
	private JLabel lblModelSourcePath;
	private JTextField txtModelSourcePath;
	private JButton btnModelRetrieve;
	private JButton btnModelSourceSearch;
	private JButton btnModelCancel;
	private JButton btnModelSave;
	private JLabel lblModelNameError;
	private boolean hasModelName;
	private boolean hasModelLang;
	private boolean hasModelSourcePath;
	private CardLayout cardLayout;
	private JLabel lblModelApproach;
	private JComboBox cbModelApproach;

	public ModelPanel(DrawerController drawer, CardLayout cardLayout, JPanel contentPane) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				if(!Main.repositoryExists()) {
					disableComponents();
					JOptionPane.showMessageDialog(null, "There is no repository. Please go to Settings for configuration.");
				}else {
					enableComponents();
				}
			}
		});
		init(drawer,cardLayout,contentPane);
	}

	protected void disableComponents() {
		txtModelName.setEnabled(false);
		txtModelDesc.setEnabled(false);
		txtModelDesc.setEditable(false);
		cbModelApproach.setEnabled(false);
		cbModelLanguage.setEnabled(false);
		btnModelRetrieve.setEnabled(false);
	}

	protected void enableComponents() {
		txtModelName.setEnabled(true);
		txtModelDesc.setEnabled(true);
		txtModelDesc.setEditable(true);
		cbModelApproach.setEnabled(true);
		cbModelLanguage.setEnabled(true);
		btnModelRetrieve.setEnabled(true);
	}

	private void init(DrawerController drawer, CardLayout cardLayout, JPanel contentPane) {
		
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
				if (ModelPanel.this.drawer.isShow()) {
					ModelPanel.this.drawer.hide();
				} else {
					ModelPanel.this.drawer.show();
				}
			}
		});

		/**
		 * Create the panel.
		 */
		lblModelName = new JLabel("Name");

		lblModelTitle = new JLabel("Adding a new model for the " + "HM" + "\u00B2" + "AT ");
		lblModelTitle.setFont(new Font("Tahoma", Font.BOLD, 14));

		txtModelName = new JTextField();
		
		txtModelName.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				lblModelNameError.setVisible(false);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!txtModelName.getText().isBlank()) {
					hasModelName = true;
					txtModelName.putClientProperty("JComponent.outline", null);
				} else {
					txtModelName.putClientProperty("JComponent.outline", "error");
					lblModelNameError.setText("Model's name is not valid.");
					lblModelNameError.setVisible(true);
					hasModelName = false;
				}
				btnModelSave.setEnabled(hasModelName && hasModelLang && hasModelSourcePath);
			}
		});
		txtModelName.setColumns(10);

		JLabel lblModelLanguage = new JLabel("Language");

		cbModelLanguage = new JComboBox();
		cbModelLanguage.setMaximumRowCount(4);
		ComboPopulator.populateModelingLanguage(cbModelLanguage);
		cbModelLanguage.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtModelName.getText().isBlank()) {
					txtModelName.putClientProperty("JComponent.outline", "error");
					lblModelNameError.setText("Model's name is not valid.");
					lblModelNameError.setVisible(true);
				} else {
					txtModelName.putClientProperty("JComponent.outline", null);
				}
			}
		});
		cbModelLanguage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				hasModelLang = cbModelLanguage.getSelectedIndex() != 0;
				if (hasModelLang && hasModelName) {
					btnModelSourceSearch.setEnabled(true);
				} else {
					btnModelSourceSearch.setEnabled(false);
				}
				btnModelSave.setEnabled(hasModelName && hasModelLang && hasModelSourcePath);
			}
		});

		lblModelSourcePath = new JLabel("Source File");

		txtModelSourcePath = new JTextField();
		txtModelSourcePath.setEditable(false);
		txtModelSourcePath.setColumns(10);

		btnModelSourceSearch = new JButton("");
		ImageIcon imgSearch = new ImageIcon(this.getClass().getResource("/search.png"));
		btnModelSourceSearch.setIcon(imgSearch);
		btnModelSourceSearch.setEnabled(false);
		btnModelSourceSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModelSourceDialog();
			}
		});

		btnModelCancel = new JButton("Cancel");
		btnModelCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearModelPanel();
				ModelPanel.this.cardLayout.show(contentPane, "Home");
			}
		});

		btnModelSave = new JButton("Save");
		btnModelSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtModelName.getText().isBlank()) {
					txtModelName.putClientProperty("JComponent.outline", "error");
					lblModelNameError.setText("Model's name is not valid.");
					lblModelNameError.setVisible(true);
				}
				boolean result = submitModel();
				if (result) {
					clearModelPanel();
				}
			}
		});
		btnModelSave.setEnabled(false);

		lblModelNameError = new JLabel("blablabla");
		lblModelNameError.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblModelNameError.setForeground(Color.RED);
		lblModelNameError.setVisible(false);

		JLabel lblModelDesc = new JLabel("Description");

		JScrollPane scrollPane = new JScrollPane();

		btnModelRetrieve = new JButton("");
		ImageIcon imgRetrieve = new ImageIcon(this.getClass().getResource("/retrieve.png"));
		btnModelRetrieve.setIcon(imgRetrieve);
		btnModelRetrieve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchModelDialog searchDialog = new SearchModelDialog();
				searchDialog.setVisible(true);
			}
		});
		
		lblModelApproach = new JLabel("Approach");
		
		cbModelApproach = new JComboBox();
		ComboPopulator.populateModelApproach(cbModelApproach);

		GroupLayout gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(121)
							.addComponent(lblModelTitle))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(44)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblModelName)
												.addComponent(lblModelApproach)
												.addComponent(lblModelLanguage)
												.addComponent(lblModelSourcePath))
											.addGap(7))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblModelDesc)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(cbModelApproach, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cbModelLanguage, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(txtModelSourcePath)
										.addComponent(txtModelName, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
										.addComponent(lblModelNameError, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(scrollPane, Alignment.TRAILING)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnModelCancel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnModelSave)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnModelRetrieve, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnModelSourceSearch, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
							.addGap(819)))
					.addGap(56))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnMenu))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(51)
									.addComponent(lblModelTitle)))
							.addGap(81)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblModelName)
								.addComponent(txtModelName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnModelRetrieve))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblModelNameError)
							.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblModelDesc))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbModelApproach, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblModelApproach))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbModelLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblModelLanguage))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblModelSourcePath)
								.addComponent(txtModelSourcePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnModelSourceSearch, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(62)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnModelCancel)
						.addComponent(btnModelSave))
					.addGap(54))
		);

		txtModelDesc = new JTextArea();
		txtModelDesc.setWrapStyleWord(true);
		txtModelDesc.setLineWrap(true);
		scrollPane.setViewportView(txtModelDesc);
		setLayout(gl_contentPane);
		
	}
	
	private void showModelSourceDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Choose source file for the model");
		int response = fileChooser.showOpenDialog(this);
		if (response == JFileChooser.APPROVE_OPTION) {
			hasModelSourcePath = true;
			File selectedFile = fileChooser.getSelectedFile();
			txtModelSourcePath.setText(selectedFile.getAbsolutePath());
			txtModelSourcePath.setEnabled(false);
		} else {
			hasModelSourcePath = false;
		}
		btnModelSave.setEnabled(hasModelName && hasModelLang && hasModelSourcePath);
	}

	private boolean submitModel() {
		boolean saved=false;
		boolean verified=false;
		AdaptivityModel model=new AdaptivityModel(txtModelName.getText(), txtModelDesc.getText(), cbModelApproach.getSelectedItem().toString(), cbModelLanguage.getSelectedItem().toString(), Paths.get(txtModelSourcePath.getText()));
		model.setId(AdaptivityModel.getNextId());
		verified=model.verifyModel();
		if (verified){
			saved = JSONManager.saveModel(Main.repository+"\\models", model);			
		}
		JOptionPane.showMessageDialog(null, selectResultMessage(saved));
		return saved;
	}

	private String selectResultMessage(boolean result) {
		String msg;
		if (result) {
			msg = "<html><font color='green'>Model submitted sucessfully!</font></html>";
		} else {
			msg = "<html><font color='red'>Error while attempting submitting the model!</font></html>";
		}
		return msg;
	}

	private void clearModelPanel() {
		txtModelName.setText("");
		txtModelDesc.setText("");
		cbModelLanguage.setSelectedIndex(0);
		txtModelSourcePath.setText("");
	}
	
	
	private boolean saveToJSON(AdaptivityModel model) {
		boolean result;
		ObjectMapper mapper = new ObjectMapper();

        try {
            // Java objects to JSON file
            mapper.writeValue(new File("C:\\Users\\rosed\\Downloads\\models.json"), model);

            // Java objects to JSON string - compact-print
            String jsonString = mapper.writeValueAsString(model);

            System.out.println(jsonString);

            // Java objects to JSON string - pretty-print
            String jsonInString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);

            System.out.println(jsonInString2);
            result=true;

        } catch (IOException e) {
            e.printStackTrace();
            result=false;
        }
		return result;
	}

}
