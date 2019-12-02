package ua.nure.rakhman.usermanagement.gui;

import ua.nure.rakhman.usermanagement.db.Dao;
import ua.nure.rakhman.usermanagement.db.DaoFactory;
import ua.nure.rakhman.usermanagement.domain.User;
import ua.nure.rakhman.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3538220276603591172L;
	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 800;
	private JPanel contentPanel;
	private BrowsePanel browsePanel;
	private Dao<User> dao;
	private AddPanel addPanel;
	private EditPanel editPanel;
	private DetailsPanel detailsPanel;
	
	public MainFrame() {
		super();
		dao = DaoFactory.getInstance().getUserDao();
		initialize();
	}
	
	public Dao<User> getDao() {
		return dao;
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
		this.setContentPane(getContentPanel());
	}
	
	private JPanel getContentPanel() {
		if(contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
		}
		return contentPanel;
	}
	private JPanel getBrowsePanel() {
		if(browsePanel == null) {
			browsePanel = new BrowsePanel(this);
		}
		((BrowsePanel)browsePanel).initTable();
		return browsePanel;
	}
	
	public void showAddPanel() {
		showPanel(getAddPanel());
	}//done
	
	private AddPanel getAddPanel() {
		if(addPanel == null) {
			addPanel = new AddPanel(this);
		}
		return addPanel;
	}//done
	
	public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(false);
		panel.repaint();
	}//done

	void showEditPanel(User user) {
		getEditPanel().setUser(user);
		showPanel(getEditPanel());
	}//done
	private EditPanel getEditPanel() {
		if(editPanel==null) {
			editPanel = new EditPanel(this);
		}
		return editPanel;
	}//done
	void showDetailsPanel(User user) {
		getDetailsPanel().setUser(user);
		showPanel(getDetailsPanel());
	}//done
	private DetailsPanel getDetailsPanel() {
		if(detailsPanel==null) {
			detailsPanel = new DetailsPanel(this);
		}
		return detailsPanel;
	}//done
	public static void main(String[]args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}
}

