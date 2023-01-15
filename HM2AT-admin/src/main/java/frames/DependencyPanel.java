package frames;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javaswingdev.drawer.DrawerController;
import logic.AdaptivityModelImplementation;
import logic.ImplementationDependency;
import utils.ComboPopulator;
import utils.JSONManager;

public class DependencyPanel extends JPanel {
	private DrawerController drawer;
	private CardLayout cardLayout;
	
	// Elements of Implementation Panel
	private JComboBox cbImplementationName;
	private JComboBox cbImplementationParadigm;
	private JComboBox cbImplementationLanguage;
	private boolean namefilter;
	private boolean langFilter;
	private boolean paraFilter;
	
	// Elements of Dependency Panel
	private JPanel dependencyPanel;
	private JComboBox cbDependencyManager;
	private JTextArea textArea;
	
	// Verification properties
	private boolean hasImplementation;
	protected boolean hasManager;
	protected boolean hasDependency;
	
	protected String selectedManager;
	private AdaptivityModelImplementation selectedImplementation;

	

	

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
		modelPanel.setBorder(new TitledBorder(null, "Implementation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnSave = new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean result = submitDependency();
				if (result) {
					clearDependencyPanel();
				}
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
		
		cbDependencyManager = new JComboBox();
		cbDependencyManager.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if(cbDependencyManager.getSelectedItem()!="---") {
						selectedManager = cbDependencyManager.getSelectedItem().toString();
						hasManager = true;					
					}else {
						selectedManager="";
						hasManager=false;						
					}
					btnSave.setEnabled(hasDependency&&hasManager&&hasImplementation);
				}
			}
		});
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
		
		textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.getDocument().addDocumentListener(new DocumentListener() {
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
				if (textArea.getText().isBlank()) {
					hasDependency = false;
				} else {
					hasDependency = true;
				}
				btnSave.setEnabled(hasDependency&&hasManager&&hasImplementation);
			}
		});
		
		scrollPane.setViewportView(textArea);
		dependencyPanel.setLayout(gl_dependencyPanel);
		
		JLabel lblLibraryModelName = new JLabel("Name");
		cbImplementationName = new JComboBox();
		ComboPopulator.populateAdaptivityModelImplementations(cbImplementationName);
		cbImplementationName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectedImplementation = Main.implementations.get(cbImplementationName.getSelectedIndex());
					cbImplementationLanguage.setSelectedItem(selectedImplementation.getProgrammingLang());;
					cbImplementationParadigm.setSelectedItem(selectedImplementation.getParadigm());
					hasImplementation = true;
					enableComponents(dependencyPanel, true);
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
		
		JLabel lblLibraryLanguage = new JLabel("Language");
		cbImplementationLanguage = new JComboBox();
		ComboPopulator.populateProgrammingLanguage(cbImplementationLanguage);
		cbImplementationLanguage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbImplementationLanguage.getSelectedIndex()==-1) {
					langFilter=false;
				}else {
					langFilter=true;
				}
				filterCombo(cbImplementationName, langFilter,paraFilter);
				enableComponents(dependencyPanel,namefilter&&langFilter&&paraFilter);
			}
		});
		
		JLabel lblLibraryParadigm = new JLabel("Paradigm");
		cbImplementationParadigm = new JComboBox();
		ComboPopulator.populateProgrammingParadigm(cbImplementationParadigm);
		cbImplementationParadigm.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbImplementationParadigm.getSelectedIndex()==-1) {
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
								.addComponent(cbImplementationName, 0, 418, Short.MAX_VALUE)
								.addGroup(gl_modelPanel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(cbImplementationLanguage, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cbImplementationParadigm, Alignment.LEADING, 0, 192, Short.MAX_VALUE)))
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
						.addComponent(cbImplementationName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLibraryModelName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_modelPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLibraryLanguage)
						.addComponent(cbImplementationLanguage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_modelPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLibraryParadigm)
						.addComponent(cbImplementationParadigm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		modelPanel.setLayout(gl_modelPanel);
		setLayout(groupLayout);
		enableComponents(dependencyPanel, false);
	}
	
	private boolean submitDependency() {
		boolean saved = false;
		String dependencyId=ImplementationDependency.getNextId();
		String xml2line=XMLtoLine(textArea.getText());
		//String xmlFile=saveDependencyToXML();
		ImplementationDependency dependency = new ImplementationDependency("[Dependency]"+selectedImplementation.getName(),selectedImplementation.getId(),
				selectedManager,xml2line,Paths.get(""));
		dependency.setId(dependencyId);
		saved = JSONManager.saveDependency(Main.repository + "\\dependencies", dependency);
		JOptionPane.showMessageDialog(null, selectResultMessage(saved));
		return saved;
	}
	
	private String XMLtoLine(String xmlFile) {
		String xmlLine="";
//		StringBuilder sb=new StringBuilder();
//
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(new File(xmlFile)));
//			String line;
//			while((line=br.readLine())!= null){
//			    sb.append(line.trim());
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return sb.toString();
        List<String> lines = Arrays.stream(xmlFile.split("\\r?\\n"))
                .map(x -> x.trim())
                .filter(x -> x.length() > 0)
                .collect(Collectors.toList());

        for (String line : lines) {
            System.out.println(line);
            xmlLine=xmlLine+line;
            //xmlLine.concat(line);
        }
        System.out.println(xmlLine);
        return xmlLine;
	}
	private String saveDependencyToXML() {
		String filename=Main.repository + "\\dependencies\\pom.xml";
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
	    
		try {
		    factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(new InputSource(new StringReader(textArea.getText())));
		    
		    // Write the parsed document to an xml file
		    TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(System.getProperty("user.dir")+ "\\scripts\\format.xslt")));
		    //transformerFactory.setAttribute("indent-number", 4);
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
		    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");	
		    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		    DOMSource source = new DOMSource(doc);
		    FileOutputStream output = new FileOutputStream(filename);
		    StreamResult result =  new StreamResult(output);
		    transformer.transform(source, result);
		    output.close();
		} catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filename;
	} 

	private String selectResultMessage(boolean result) {
		String msg;
		if (result) {
			msg = "<html><font color='green'>Dependency submitted sucessfully!</font></html>";
		} else {
			msg = "<html><font color='red'>Error while attempting submitting the dependency!</font></html>";
		}
		return msg;
	}

	private void filterCombo(JComboBox cbModelName, boolean langFilter2, boolean paraFilter2) {
		// TODO Auto-generated method stub
	}
	
	private void clearDependencyPanel() {
		cbImplementationName.setSelectedIndex(0);
		cbImplementationLanguage.setSelectedIndex(0);
		cbImplementationParadigm.setSelectedIndex(0);
		cbDependencyManager.setSelectedIndex(0);
		textArea.setText("");
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
