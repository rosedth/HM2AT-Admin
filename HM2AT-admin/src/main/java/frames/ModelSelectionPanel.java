package frames;

import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import utils.ComboPopulator;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ModelSelectionPanel extends JPanel {
	private boolean namefilter;
	private boolean langFilter;
	private boolean paraFilter;

	private JComboBox cbModelName;
	private JComboBox cbModelLanguage;
	private JComboBox cbModelParadigm;
	/**
	 * Create the panel.
	 */
	public ModelSelectionPanel(JPanel pane) {
		
		JLabel lblModelName = new JLabel("Name");
		
		cbModelName = new JComboBox();
		ComboPopulator.populateAdaptivityModelImplementations(cbModelName);
		
		JLabel lblModelLanguage = new JLabel("Language");
			
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
		
		cbModelLanguage = new JComboBox();
		ComboPopulator.populateProgrammingLanguage(cbModelLanguage);
		cbModelLanguage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbModelLanguage.getSelectedIndex()<=0) {
					langFilter=false;
				}else {
					langFilter=true;
				}
				//filterCombo(cbModelName, langFilter,paraFilter);
				enableComponents(pane,namefilter&&langFilter&&paraFilter);
			}
		});
		
		JLabel lblModelParadigm = new JLabel("Paradigm");
		
		cbModelParadigm = new JComboBox();
		ComboPopulator.populateProgrammingParadigm(cbModelParadigm);
		cbModelParadigm.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbModelParadigm.getSelectedIndex()<=0) {
					paraFilter=false;
				}else {
					paraFilter=true;
				}
				enableComponents(pane,namefilter&&langFilter&&paraFilter);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(29, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblModelName)
						.addComponent(lblModelLanguage)
						.addComponent(lblModelParadigm))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(cbModelParadigm, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(cbModelLanguage, Alignment.LEADING, 0, 214, Short.MAX_VALUE))
						.addComponent(cbModelName, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbModelName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblModelName))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblModelLanguage)
						.addComponent(cbModelLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbModelParadigm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblModelParadigm))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

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
