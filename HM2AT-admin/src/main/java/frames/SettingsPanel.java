package frames;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;

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
import javax.swing.border.TitledBorder;

import org.python.util.PythonInterpreter;

import javaswingdev.drawer.DrawerController;
import logic.Repository;
import utils.ComboPopulator;
import utils.FileManager;
import utils.JSONManager;

public class SettingsPanel extends JPanel {

	private DrawerController drawer;
	private CardLayout cardLayout;
	private JPanel contentPane;
	private JComboBox cbRepoType;
	private JTextField txtRepositoryPath;
	private Path repository;
	private JButton btnRepositorySearch;
	private JButton btnSave;
	private JButton btnUpdateIndexes;
	private JTextField txtScriptsPath;
	
	public SettingsPanel(Main main, DrawerController drawer, CardLayout cardLayout, JPanel contentPane) {
		setBounds(100, 100, 653, 559);
		this.drawer = drawer;
		this.cardLayout = cardLayout;
		this.contentPane=contentPane;
		
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
		JPanel panelRepository = new JPanel();
		panelRepository.setBorder(new TitledBorder(null, "Repository", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Tahoma", Font.BOLD, 14));

		ImageIcon imgSearch = new ImageIcon(this.getClass().getResource("/search.png"));
		btnRepositorySearch = new JButton("");
		btnRepositorySearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSearchRepositoryDialog();
			}
		});
		btnRepositorySearch.setIcon(imgSearch);

		JLabel lblRepoType = new JLabel("Type");
		cbRepoType = new JComboBox();
		cbRepoType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbRepoType.getSelectedIndex() != 0) {
					JOptionPane.showMessageDialog(null,
							"Remote Repository option to be implemented. Please, use Local Repository instead.");
					btnRepositorySearch.setEnabled(false);
				} else {
					btnRepositorySearch.setEnabled(true);
				}
			}
		});
		ComboPopulator.populateRepositoryType(cbRepoType);

		JLabel lblRepository = new JLabel("Location");

		txtRepositoryPath = new JTextField();
		txtRepositoryPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtRepositoryPath.getText().isBlank()) {
					// verify if text is a directory
					File file= new File(txtRepositoryPath.getText());
					if (file.isDirectory()) {
						repository=file.toPath();
						btnSave.setEnabled(true);
					}else {
						JOptionPane.showMessageDialog(null, "Repository path is not a valid directory!");
						txtRepositoryPath.setText("");
					}
					
				}
			}
		});
		txtRepositoryPath.setToolTipText("Copy the Repository path here. ");
		txtRepositoryPath.setColumns(10);
		verifyRepository();
		btnSave = new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String repositoryPath=repository.toString() + "\\repository-root";
				if(!new File(repositoryPath).exists()) {
					createRepository(repositoryPath);
				}else {
					loadRepository(repositoryPath);
					btnUpdateIndexes.setEnabled(true);
				}
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsPanel.this.cardLayout.show(contentPane, "Home");
			}
		});

		btnUpdateIndexes = new JButton("Update Indexes");
		btnUpdateIndexes.setEnabled(false);
		
		JPanel panelScripts = new JPanel();
		panelScripts.setBorder(new TitledBorder(null, "Scripts", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(224)
					.addComponent(lblSettings)
					.addContainerGap(291, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnUpdateIndexes))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap(60, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panelScripts, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(btnCancel)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnSave))
									.addComponent(panelRepository, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)))))
					.addGap(67))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(43, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addGap(49))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblSettings)
							.addGap(33)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelRepository, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panelScripts, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(btnUpdateIndexes)
					.addGap(74)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnCancel))
					.addGap(27))
		);
		
		JLabel lblScripts = new JLabel("Location");
		
		txtScriptsPath = new JTextField();
		txtScriptsPath.setColumns(10);
		
		JButton btnScriptSearch = new JButton("");
		btnScriptSearch.setIcon(imgSearch);
		
		GroupLayout gl_panelScripts = new GroupLayout(panelScripts);
		gl_panelScripts.setHorizontalGroup(
			gl_panelScripts.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelScripts.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblScripts)
					.addGap(18)
					.addComponent(txtScriptsPath, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(btnScriptSearch, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panelScripts.setVerticalGroup(
			gl_panelScripts.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelScripts.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_panelScripts.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnScriptSearch, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelScripts.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblScripts)
							.addComponent(txtScriptsPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		panelScripts.setLayout(gl_panelScripts);

		GroupLayout gl_panelRepository = new GroupLayout(panelRepository);
		gl_panelRepository.setHorizontalGroup(
			gl_panelRepository.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRepository.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelRepository.createParallelGroup(Alignment.LEADING)
						.addComponent(lblRepoType)
						.addComponent(lblRepository))
					.addGap(18)
					.addGroup(gl_panelRepository.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelRepository.createSequentialGroup()
							.addComponent(txtRepositoryPath, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
							.addComponent(btnRepositorySearch, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addComponent(cbRepoType, 0, 426, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelRepository.setVerticalGroup(
			gl_panelRepository.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRepository.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelRepository.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRepoType)
						.addComponent(cbRepoType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(25)
					.addGroup(gl_panelRepository.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelRepository.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtRepositoryPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblRepository))
						.addComponent(btnRepositorySearch))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		panelRepository.setLayout(gl_panelRepository);
		setLayout(groupLayout);
	}

	public void verifyRepository() {
		if (Main.repositoryExists()) {
			repository=Main.getRepository();
			txtRepositoryPath.setText(repository.toString());
		}
	}

	public void showSearchRepositoryDialog() {
		txtRepositoryPath.setText("");
		txtRepositoryPath.setEnabled(false);
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Choose Root Folder for the Repository");
		int response = fileChooser.showOpenDialog(this);
		if (response == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			repository = selectedFile.toPath();
			txtRepositoryPath.setText(selectedFile.getAbsolutePath());
			btnSave.setEnabled(true);
		} else {
			btnSave.setEnabled(false);
		}
	}

	public void createRepository(String repositoryPath) {
		// using Jython to execute python script
		String[] arguments = { "initialize-repo.py", repositoryPath};
		PythonInterpreter.initialize(System.getProperties(), System.getProperties(), arguments);
		try (PythonInterpreter interpreter = new PythonInterpreter()) {
			StringWriter out = new StringWriter();
			interpreter.setOut(out);
			interpreter.execfile(System.getProperty("user.dir") + "\\scripts\\initialize-repo.py");
			String outputStr = out.toString();
			System.out.println(outputStr);
		}
		Main.repository = Path.of(repositoryPath);
		saveConfigFile();
		// System.out.println(Main.repository.toString());
		JOptionPane.showMessageDialog(null, "Settings saved!");
		clearPanel();
		SettingsPanel.this.cardLayout.show(contentPane, "Home");
	}

	public void saveConfigFile(){
		String type=cbRepoType.getSelectedItem().toString();
		File repoConfig=new File(Main.repository.toString()+"\\repo-config.json");
		Repository repo= new Repository();
		repo.setType(type);
		repo.setPath(Main.repository);
		repo.setConfig(repoConfig);
		JSONManager.saveRepositoryConfig(repoConfig.getPath(), repo);
		
	}
	
	public void clearPanel() {
		btnSave.setEnabled(false);
	}

	public void loadRepository(String repositoryPath) {
		System.out.println("Loading repository at"+repositoryPath+"!");
	}

	public JTextField getTxtRepositoryPath() {
		return txtRepositoryPath;
	}

	public Path getRepository() {
		return repository;
	}

	public JButton getBtnRepositorySearch() {
		return btnRepositorySearch;
	}

	public JButton getBtnSave() {
		return btnSave;
	}

	public JButton getBtnUpdateIndexes() {
		return btnUpdateIndexes;
	}
}
