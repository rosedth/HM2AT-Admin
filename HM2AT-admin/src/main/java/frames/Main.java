package frames;

import java.awt.CardLayout;
import java.awt.Toolkit;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerController;
import javaswingdev.drawer.DrawerItem;
import javaswingdev.drawer.EventDrawer;
import logic.AdaptivityModel;
import utils.FileManager;
import utils.JSONManager;

public class Main extends JFrame {
	private DrawerController drawer;
	private JPanel contentPane;
	private CardLayout cardLayout;

	// Panels for Menu Functionality
	private JPanel homePanel;
	private JPanel manageModelPanel;
	private JPanel manageExamplePanel;
	private JPanel manageImplementationPanel;
	private JPanel manageDependencyPanel;
	private JPanel settingsPanel;

	public static Path repository;
	public static List<AdaptivityModel> models;
	
	public static Map<String,Integer> indexes;

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, 653, 559);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		/**
		 * Loading icons
		 */
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/IconHM2AT.png")));

		/**
		 * Look for Repository
		 */
		 loadSettings();
		 loadIndexes();
		 loadData();

		/**
		 * Create the drawer menu.
		 */
//		if(repository==null){
//			drawer=createDisabledMenu();
//		}
//		else {
		drawer = createEnabledMenu();
		// }

		cardLayout = new CardLayout();

		homePanel = new HomePanel(drawer);
		manageModelPanel = new ModelPanel(drawer, cardLayout, contentPane);
		manageExamplePanel = new ExamplePanel(drawer, cardLayout, contentPane);
		manageImplementationPanel = new ImplementationPanel(drawer, cardLayout, contentPane);
		manageDependencyPanel = new DependencyPanel(drawer, cardLayout, contentPane);
		settingsPanel = new SettingsPanel(this, drawer, cardLayout, contentPane);

		contentPane.setLayout(cardLayout);
		contentPane.add(homePanel, "Home");
		contentPane.add(manageModelPanel, "Model");
		contentPane.add(manageExamplePanel, "Example");
		contentPane.add(manageImplementationPanel, "Code");
		contentPane.add(manageDependencyPanel, "Library");
		contentPane.add(settingsPanel, "Settings");
		cardLayout.show(contentPane, "Home");

		setLocationRelativeTo(null);

	}

	private void loadSettings() {
		WorkingRepositoryDialog chooserRepo=new WorkingRepositoryDialog(this);
		chooserRepo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		chooserRepo.setModal(true);
		chooserRepo.setLocationRelativeTo(null);
		chooserRepo.setVisible(true);
	}
	
	private void loadData() {
		// load models from repository
		Main.models=JSONManager.readModels();
		
	}
	
	private void loadIndexes() {
		// load indexes from repository
		Main.indexes=FileManager.readIndexes(Main.repository+"\\indexes.txt");
	}

	public DrawerController createDisabledMenu() {
		ImageIcon modelIcon = new ImageIcon(getClass().getResource("/model-disabled.png"));
		ImageIcon exampleIcon = new ImageIcon(getClass().getResource("/example-disabled.png"));
		ImageIcon codeIcon = new ImageIcon(getClass().getResource("/code-disabled.png"));
		ImageIcon libraryIcon = new ImageIcon(getClass().getResource("/library-disabled.png"));
		ImageIcon settingsIcon = new ImageIcon(getClass().getResource("/settings-color.png"));
		ImageIcon exitIcon = new ImageIcon(getClass().getResource("/exit-color.png"));

		DrawerController drawer = Drawer.newDrawer(this).header(new Header()).space(5).backgroundTransparent(0.3f)
				.duration(300).space(10).closeOnPress(true)
				.addChild(new DrawerItem("Manage Model").icon(modelIcon).build()).space(3)
				.addChild(new DrawerItem("Manage Example").icon(exampleIcon).build()).space(3)
				.addChild(new DrawerItem("Manage Implementation").icon(codeIcon).build()).space(3)
				.addChild(new DrawerItem("Manage Dependency").icon(libraryIcon).build()).space(3)
				.addFooter(new DrawerItem("Settings").icon(settingsIcon).build())
				.addFooter(new DrawerItem("Exit").icon(exitIcon).build()).event(new EventDrawer() {

					@Override
					public void selected(int arg0, DrawerItem arg1) {
						// TODO Auto-generated method stub
						processSelectedItemDisabled(arg0, arg1);
					}
				}).build();
		return drawer;
	}

	public DrawerController createEnabledMenu() {
		ImageIcon modelIcon = new ImageIcon(getClass().getResource("/model.png"));
		ImageIcon exampleIcon = new ImageIcon(getClass().getResource("/example.png"));
		ImageIcon codeIcon = new ImageIcon(getClass().getResource("/code.png"));
		ImageIcon libraryIcon = new ImageIcon(getClass().getResource("/file-system.png"));
		ImageIcon settingsIcon = new ImageIcon(getClass().getResource("/settings-color.png"));
		ImageIcon exitIcon = new ImageIcon(getClass().getResource("/exit-color.png"));

		DrawerController drawer = Drawer.newDrawer(this).header(new Header()).space(5).backgroundTransparent(0.3f)
				.duration(300).space(10).closeOnPress(true)
				.addChild(new DrawerItem("Manage Model").icon(modelIcon).build()).space(3)
				.addChild(new DrawerItem("Manage Example").icon(exampleIcon).build()).space(3)
				.addChild(new DrawerItem("Manage Implementation").icon(codeIcon).build()).space(3)
				.addChild(new DrawerItem("Manage Dependency").icon(libraryIcon).build()).space(3)
				.addFooter(new DrawerItem("Settings").icon(settingsIcon).build())
				.addFooter(new DrawerItem("Exit").icon(exitIcon).build()).event(new EventDrawer() {

					@Override
					public void selected(int arg0, DrawerItem arg1) {
						// TODO Auto-generated method stub
						processSelectedItem(arg0, arg1);
					}
				}).build();
		return drawer;
	}

	public void processSelectedItem(int arg0, DrawerItem arg1) {
		switch (arg0) {
		case 0:
			cardLayout.show(contentPane, "Model");
			break;
		case 1:
			cardLayout.show(contentPane, "Example");
			break;
		case 2:
			cardLayout.show(contentPane, "Code");
			break;
		case 3:
			cardLayout.show(contentPane, "Library");
			break;
		case 4:
			cardLayout.show(contentPane, "Settings");
			break;
		case 5:
			updateIndexes();
			this.dispose();
			break;
		}
	}

	private void updateIndexes() {
		FileManager.updateIndexes();
	}

	public void processSelectedItemDisabled(int arg0, DrawerItem arg1) {
		switch (arg0) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			cardLayout.show(contentPane, "Settings");
			break;
		case 5:
			this.dispose();
			break;
		}
	}

	public JPanel getHomePanel() {
		return homePanel;
	}

	public JPanel getManageModelPanel() {
		return manageModelPanel;
	}

	public JPanel getManageExamplePanel() {
		return manageExamplePanel;
	}

	public JPanel getManageImplementationPanel() {
		return manageImplementationPanel;
	}

	public JPanel getManageDependencyPanel() {
		return manageDependencyPanel;
	}

	public JPanel getSettingsPanel() {
		return settingsPanel;
	}

	public static Path getRepository() {
		return repository;
	}

	public static boolean repositoryExists() {
		return repository != null;
	}
}
