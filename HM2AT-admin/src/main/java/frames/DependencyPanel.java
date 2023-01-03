package frames;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import javaswingdev.drawer.DrawerController;
import utils.ComboPopulator;

public class DependencyPanel extends JPanel {
	private DrawerController drawer;
	private CardLayout cardLayout;
	
	private JComboBox cbModelParadigm;
	private JComboBox cbModelLanguage;
	private boolean namefilter;
	private boolean langFilter;
	private boolean paraFilter;
	private List<String> models;
	private JPanel dependencyPanel;
	

	/**
	 * Create the panel.
	 */
	public DependencyPanel(DrawerController drawer, CardLayout cardLayout, JPanel contentPane) {
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
				if (DependencyPanel.this.drawer.isShow()) {
					DependencyPanel.this.drawer.hide();
				} else {
					DependencyPanel.this.drawer.show();
				}
			}
		});
		
		JLabel lblNewCode = new JLabel("Managing Dependencies for a model implementation");
		lblNewCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewCode.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JPanel modelPanel = new JPanel();
		modelPanel.setBorder(new TitledBorder(null, "Model", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Dependency saved!");
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DependencyPanel.this.cardLayout.show(contentPane, "Home");
			}
		});
		
		dependencyPanel = new JPanel();
		dependencyPanel.setBorder(new TitledBorder(null, "Dependency", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(93)
							.addComponent(lblNewCode, GroupLayout.PREFERRED_SIZE, 491, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(50)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnCancel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnSave))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(dependencyPanel, GroupLayout.PREFERRED_SIZE, 545, GroupLayout.PREFERRED_SIZE)
									.addComponent(modelPanel, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(54, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnMenu, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(lblNewCode, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addComponent(modelPanel, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(dependencyPanel, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnCancel))
					.addGap(23))
		);
		
		JComboBox cbDependencyManager = new JComboBox();
		cbDependencyManager.setEnabled(false);
		ComboPopulator.populateDependencyManager(cbDependencyManager);
		cbDependencyManager.setMaximumRowCount(4);
		cbDependencyManager.addActionListener(new ActionListener() {
			private boolean found = false;
			public void actionPerformed(ActionEvent e) { 
			String s = (String) cbDependencyManager.getSelectedItem();
            for (int i = 0; i < cbDependencyManager.getItemCount(); i++) {
                if (cbDependencyManager.getItemAt(i).toString().equals(s)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Added: " + s);
                cbDependencyManager.addItem(s);
            }
            found = false;
        }
		});
		cbDependencyManager.setEditable(true);
		
		JLabel lblDependencyManager = new JLabel("Manager");
		
		JLabel lblDependencySpec = new JLabel("Specification");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_dependencyPanel = new GroupLayout(dependencyPanel);
		gl_dependencyPanel.setHorizontalGroup(
			gl_dependencyPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dependencyPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_dependencyPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDependencySpec)
						.addComponent(lblDependencyManager))
					.addGap(31)
					.addGroup(gl_dependencyPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(cbDependencyManager, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_dependencyPanel.setVerticalGroup(
			gl_dependencyPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dependencyPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_dependencyPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbDependencyManager, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDependencyManager))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_dependencyPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDependencySpec)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		
		JTextArea textArea = new JTextArea();
		textArea.setEnabled(false);
		scrollPane.setViewportView(textArea);
		dependencyPanel.setLayout(gl_dependencyPanel);
		
		JLabel lblLibraryModelName = new JLabel("Name");
		JComboBox cbModelName = new JComboBox();
		cbModelName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				namefilter=true;
			}
		});
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
		
		JLabel lblLibraryLanguage = new JLabel("Language");
		cbModelLanguage = new JComboBox();
		ComboPopulator.populateProgrammingLanguage(cbModelLanguage);
		cbModelLanguage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbModelLanguage.getSelectedIndex()==-1) {
					langFilter=false;
				}else {
					langFilter=true;
				}
				filterCombo(cbModelName, langFilter,paraFilter);
				enableComponents(dependencyPanel,namefilter&&langFilter&&paraFilter);
			}
		});
		
		JLabel lblLibraryParadigm = new JLabel("Paradigm");
		cbModelParadigm = new JComboBox();
		ComboPopulator.populateProgrammingParadigm(cbModelParadigm);
		cbModelParadigm.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbModelParadigm.getSelectedIndex()==-1) {
					paraFilter=false;
				}else {
					paraFilter=true;
				}
				enableComponents(dependencyPanel,namefilter&&langFilter&&paraFilter);
			}
		});
		
		GroupLayout gl_modelPanel = new GroupLayout(modelPanel);
		gl_modelPanel.setHorizontalGroup(
			gl_modelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_modelPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_modelPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_modelPanel.createSequentialGroup()
							.addGroup(gl_modelPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblLibraryModelName)
								.addComponent(lblLibraryParadigm))
							.addGap(47)
							.addGroup(gl_modelPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(cbModelName, 0, 418, Short.MAX_VALUE)
								.addGroup(gl_modelPanel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(cbModelLanguage, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cbModelParadigm, Alignment.LEADING, 0, 192, Short.MAX_VALUE)))
							.addContainerGap())
						.addGroup(gl_modelPanel.createSequentialGroup()
							.addComponent(lblLibraryLanguage)
							.addContainerGap(470, Short.MAX_VALUE))))
		);
		gl_modelPanel.setVerticalGroup(
			gl_modelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_modelPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_modelPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbModelName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLibraryModelName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_modelPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLibraryLanguage)
						.addComponent(cbModelLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_modelPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLibraryParadigm)
						.addComponent(cbModelParadigm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		modelPanel.setLayout(gl_modelPanel);
		setLayout(groupLayout);
		enableComponents(dependencyPanel, false);
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

	private void filterCombo(JComboBox cbModelName, boolean langFilter2, boolean paraFilter2) {
		// TODO Auto-generated method stub
		
		
	}

	private void loadComboFromFile(JComboBox combo, String path) {
		
	}
	
//	private String[] populateModelLanguage() {
//		// TODO Auto-generated method stub
//		String[] options = { "---", "Java", "C#", "C++", "C", "Other" };
//		return options;
//	}
//
//	private String[] populateModelParadigm() {
//		// TODO Auto-generated method stub
//		String[] options = { "---", "Object-oriented", "Service-oriented", "Aspect-oriented", "Declarative", "Other" };
//		return options;
//	}
	
	private void populateModel() {
		
	}
}
