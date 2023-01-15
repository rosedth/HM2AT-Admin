package frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class WorkingRepositoryDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRepositoryPath;
	private JButton btnRepositorySearch;
	private JButton btnOK;
	private Main parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WorkingRepositoryDialog dialog = new WorkingRepositoryDialog(new Main(),"");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WorkingRepositoryDialog(Main parent,String metadata) {
		setBounds(100, 100, 485, 202);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Choose the location of the working repository");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.parent=(Main)parent;
		
		JLabel lblLocation = new JLabel("Working Repository");
		
		txtRepositoryPath = new JTextField();
		txtRepositoryPath.setColumns(10);
		
		
		ImageIcon imgSearch = new ImageIcon(this.getClass().getResource("/search.png"));
		btnRepositorySearch = new JButton("");
		btnRepositorySearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSearchRepositoryDialog(metadata);
			}
		});
		btnRepositorySearch.setIcon(imgSearch);
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLocation)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtRepositoryPath, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRepositorySearch, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(52)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRepositorySearch)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblLocation)
							.addComponent(txtRepositoryPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(43, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnOK = new JButton("OK");
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.repository=Paths.get(txtRepositoryPath.getText());
						dispose();
					}
				});
				btnOK.setActionCommand("OK");
				btnOK.setEnabled(false);
				buttonPane.add(btnOK);
				getRootPane().setDefaultButton(btnOK);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						WorkingRepositoryDialog.this.parent.dispose();
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void showSearchRepositoryDialog(String suggestedDirectory) {
		txtRepositoryPath.setText("");
		txtRepositoryPath.setEnabled(false);
		JFileChooser fileChooser;
		if(suggestedDirectory.isBlank()) {
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		}else {
			fileChooser = new JFileChooser(suggestedDirectory);
			fileChooser.setCurrentDirectory(new File(suggestedDirectory));
		}
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Choose the Repository root folder");
		int response = fileChooser.showOpenDialog(this);
		if (response == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			//repository = selectedFile.toPath();
			txtRepositoryPath.setText(selectedFile.getAbsolutePath());
			btnOK.setEnabled(true);
		}
	}

}
