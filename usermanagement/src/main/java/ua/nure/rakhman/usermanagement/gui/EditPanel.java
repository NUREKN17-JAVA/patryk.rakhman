package ua.nure.rakhman.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.rakhman.usermanagement.db.DatabaseException;
import ua.nure.rakhman.usermanagement.domain.User;
import ua.nure.rakhman.usermanagement.util.Messages;

public class EditPanel extends JPanel implements ActionListener,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5466262224178286566L;
	private static final String FIRST_NAME_FIELD = "firstNameField";
	private static final String LAST_NAME_FIELD = "lastNameField";
	private static final String DATE_OF_BIRTH_FIELD = "dateOfBirthField";
	private static final String CANCEL_BUTTON_COMPONENT_NAME = "cancelButton";
	private static final String OK_BUTTON_COMPONENT_NAME = "okButton";
	private static final String OK_COMMAND = "ok";
	private static final String CANCEL_COMMAND = "cancel";
	private MainFrame parent;
	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton cancelButton;
	private JButton okButton;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField dateOfBirthField;
	private Color bgColor;
	
	private User user;
	
	public EditPanel(MainFrame parent) {
		this.parent = parent;
		initialize();
	}
	
	private void initialize() {
		this.setName("editPanel");
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}
	
	
	private JPanel getButtonPanel() {
		if(buttonPanel==null) {
			buttonPanel=new JPanel();
			buttonPanel.add(getOkButton(),null);
			buttonPanel.add(getCancelButton(),null);
		}
		return buttonPanel;
	}

	private JButton getCancelButton() {
		if(cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(Messages.getString("AddPanel.cancel")); //$NON-NLS-1$
			cancelButton.setName(CANCEL_BUTTON_COMPONENT_NAME); //$NON-NLS-1$
			cancelButton.setActionCommand(CANCEL_COMMAND); //$NON-NLS-1$
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOkButton() {
		if(okButton == null) {
			okButton = new JButton();
			okButton.setText(Messages.getString("AddPanel.ok")); //$NON-NLS-1$
			okButton.setName(OK_BUTTON_COMPONENT_NAME); //$NON-NLS-1$
			okButton.setActionCommand(OK_COMMAND); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JPanel getFieldPanel() {
		if(fieldPanel==null) {
			fieldPanel=new JPanel();
			fieldPanel.setLayout(new GridLayout(3,2));
			addLabeledField(fieldPanel,Messages.getString("AddPanel.first_name"),getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel,Messages.getString("AddPanel.last_name"),getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel,Messages.getString("AddPanel.date_of_birth"),getDateOfBirthField()); //$NON-NLS-1$
		}
		return fieldPanel;
	}

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
	}

	private JTextField getDateOfBirthField() {
		if(dateOfBirthField == null) {
			dateOfBirthField = new JTextField();
			dateOfBirthField.setName(DATE_OF_BIRTH_FIELD); //$NON-NLS-1$
		}
		return dateOfBirthField;
	}

	private JTextField getLastNameField() {
		if(lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName(LAST_NAME_FIELD); //$NON-NLS-1$
		}
		return lastNameField;
	}

	private JTextField getFirstNameField() {
		if(firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName(FIRST_NAME_FIELD); //$NON-NLS-1$
		}
		return firstNameField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("ok".equalsIgnoreCase(e.getActionCommand())) {
			User user = new User();
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			DateFormat format = DateFormat.getDateInstance();
			try {
			user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
			} catch (ParseException e1) {
				getDateOfBirthField().setBackground(Color.RED);
				return;
			}
			try {
				parent.getDao().create(user);
			} catch (DatabaseException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
			clearFields();
			this.setVisible(false);
			parent.showBrowsePanel();
		}
		this.setVisible(false);
		parent.showBrowsePanel();
	}
	
	private void clearFields() {
		getFirstNameField().setText("");
		getFirstNameField().setBackground(bgColor);
		
		getLastNameField().setText("");
		getLastNameField().setBackground(bgColor);
		
		getDateOfBirthField().setText("");
		getDateOfBirthField().setBackground(bgColor);
	}
	
	public void setUser(User user) {
		this.user=user;
		getFirstNameField().setText(user.getFirstName());
		getLastNameField().setText(user.getLastName());
		DateFormat format = DateFormat.getDateInstance();
		getDateOfBirthField().setText(format.format(user.getDateOfBirth()));
	}
}
