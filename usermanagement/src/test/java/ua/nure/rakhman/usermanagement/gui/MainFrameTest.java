package ua.nure.rakhman.usermanagement.gui;

import java.awt.Component;
import java.awt.List;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.rakhman.usermanagement.db.DaoFactory;
import ua.nure.rakhman.usermanagement.db.DaoFactoryImpl;
import ua.nure.rakhman.usermanagement.db.MockDaoFactory;
import ua.nure.rakhman.usermanagement.db.MockUserDao;
import ua.nure.rakhman.usermanagement.domain.User;
import ua.nure.rakhman.usermanagement.gui.MainFrame;
import ua.nure.rakhman.usermanagement.util.Messages;

public class MainFrameTest<users, users1> extends JFCTestCase {

    private MainFrame mainFrame;
    private Mock mockUserDao;
    private ArrayList<User> users;

    private static final String BROWSE_PANEL_COMMAND_NAME = "browsePanel";
    private static final String ADD_PANEL_COMMAND_NAME = "addPanel";
    private static final String EDIT_PANEL_COMMAND_NAME = "editPanel";

    private static final String USER_TABLE_COMMAND_NAME = "userTable";
    private static final String ADD_BUTTON_COMMAND_NAME = "addButton";
    private static final String EDIT_BUTTON_COMMAND_NAME = "editButton";
    private static final String DELETE_BUTTON_COMMAND_NAME = "deleteButton";
    private static final String DETAILS_BUTTON_COMMAND_NAME = "detailsButton";
    private static final String CANCEL_BUTTON_COMMAND_NAME = "cancelButton";
    private static final String OK_BUTTON_COMMAND_NAME = "okButton";

    private static final String FIRST_NAME_FIELD = "firstNameField";
    private static final String LAST_NAME_FIELD = "lastNameField";
    private static final String DATE_OF_BIRTH_FIELD = "dateOfBirthField";

    public void setUp() throws Exception {
        super.setUp();

        try {
            Properties properties = new Properties();
            properties
                    .setProperty("dao.Factory", MockDaoFactory.class.getName());
            DaoFactory.init(properties);
            mockUserDao = ((MockDaoFactory) DaoFactory.getInstance())
                    .getMockUserDao();
            User expectedUser = new User(1000L, "George", "Bush",
                    new Date());
            users = (ArrayList<User>) Collections
                    .singletonList(expectedUser);
            mockUserDao.expectAndReturn("findAll", users);
            setHelper(new JFCTestHelper());
            mainFrame = new MainFrame();

        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame.setVisible(true);
    }

    public void tearDown() throws Exception {
        try {
            mockUserDao.verify();
            mainFrame.setVisible(false);
            getHelper();
            TestHelper.cleanUp(
                    this);//очистит все невыполненные действия, чтобы они не повлияли на следующий тест
            super.tearDown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testBrowseControls() { //тест ищет компоненты
        find(JPanel.class, BROWSE_PANEL_COMMAND_NAME);
        JTable table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
        assertEquals(3, table.getColumnCount());
        assertEquals(Messages.getString("UserTableModel.id"),
                table.getColumnName(0));
        assertEquals(Messages.getString("UserTableModel.first_name"),
                table.getColumnName(1));
        assertEquals(Messages.getString("UserTableModel.last_name"),
                table.getColumnName(2));
        find(JButton.class, ADD_BUTTON_COMMAND_NAME);
        find(JButton.class, EDIT_BUTTON_COMMAND_NAME);
        find(JButton.class, DELETE_BUTTON_COMMAND_NAME);
        find(JButton.class, DETAILS_BUTTON_COMMAND_NAME);
    }

    public void testAddUserOk() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();

            User user = new User(firstName, lastName, now);

            User expectedUser = new User(1L, firstName, lastName,
                    now);
            mockUserDao.expectAndReturn("create", user, expectedUser);

            JTable table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(1, table.getRowCount());

            JButton addButton = (JButton) find(JButton.class,
                    ADD_BUTTON_COMMAND_NAME);
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

            find(JPanel.class, ADD_PANEL_COMMAND_NAME);

            fillField(firstName, lastName, now);

            JButton okButton = (JButton) find(JButton.class,
                    OK_BUTTON_COMMAND_NAME);
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, BROWSE_PANEL_COMMAND_NAME);
            table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(2, table.getRowCount());

            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testCancelAddUser() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();

            ArrayList<User> users = new ArrayList<>(this.users);
            mockUserDao.expectAndReturn("findAll", users);

            JTable table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(1, table.getRowCount());

            JButton addButton = (JButton) find(JButton.class,
                    ADD_BUTTON_COMMAND_NAME);
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

            find(JPanel.class, "addPanel");

            fillField(firstName, lastName, now);

            JButton cancelButton = (JButton) find(JButton.class,
                    CANCEL_BUTTON_COMMAND_NAME);
            getHelper()
                    .enterClickAndLeave(new MouseEventData(this, cancelButton));

            find(JPanel.class, BROWSE_PANEL_COMMAND_NAME);
            table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(1, table.getRowCount());

            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testEditUser() {
        try {
            User expectedUser = new User(1000L, "George", "Bush",
                    new Date());
            System.out.println(expectedUser);
            mockUserDao.expect("update", expectedUser);

            ArrayList<User> users = new ArrayList<>(this.users);
            mockUserDao.expectAndReturn("findAll", users);

            JTable table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(1, table.getRowCount());
            JButton editButton = (JButton) find(JButton.class,
                    EDIT_BUTTON_COMMAND_NAME);
            getHelper().enterClickAndLeave(
                    new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper()
                    .enterClickAndLeave(new MouseEventData(this, editButton));

            find(JPanel.class, EDIT_PANEL_COMMAND_NAME);

            JTextField firstNameField = (JTextField) find(JTextField.class,
                    FIRST_NAME_FIELD);
            JTextField lastNameField = (JTextField) find(JTextField.class,
                    LAST_NAME_FIELD);

            getHelper()
                    .sendString(new StringEventData(this, firstNameField, "1"));
            getHelper()
                    .sendString(new StringEventData(this, lastNameField, "1"));

            JButton okButton = (JButton) find(JButton.class,
                    OK_BUTTON_COMMAND_NAME);
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, BROWSE_PANEL_COMMAND_NAME);
            table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(1, table.getRowCount());
            mockUserDao.verify();

        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testCancelEditUser() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();

            User expectedUser = new User(1L, firstName, lastName,
                    now);
            ArrayList<User> users = new ArrayList<>(this.users);
            users.add(expectedUser);

            mockUserDao.expectAndReturn("findAll", users);

            JTable table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(1, table.getRowCount());

            JButton editButton = (JButton) find(JButton.class,
                    EDIT_BUTTON_COMMAND_NAME);
            getHelper()
                    .enterClickAndLeave(new MouseEventData(this, editButton));

            String title = "Edit user";
            findDialog(title);

            getHelper().enterClickAndLeave(
                    new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper()
                    .enterClickAndLeave(new MouseEventData(this, editButton));

            find(JPanel.class, EDIT_PANEL_COMMAND_NAME);

            JTextField firstNameField = (JTextField) find(JTextField.class,
                    FIRST_NAME_FIELD);
            JTextField lastNameField = (JTextField) find(JTextField.class,
                    LAST_NAME_FIELD);
            JTextField dateOfBirthField = (JTextField) find(JTextField.class,
                    DATE_OF_BIRTH_FIELD);

            assertEquals("George", firstNameField.getText());
            assertEquals("Bush", lastNameField.getText());

            getHelper().sendString(
                    new StringEventData(this, firstNameField, firstName));
            getHelper().sendString(
                    new StringEventData(this, lastNameField, lastName));
            DateFormat formatter = DateFormat.getDateInstance();
            String date = formatter.format(now);
            getHelper().sendString(
                    new StringEventData(this, dateOfBirthField, date));

            JButton cancelButton = (JButton) find(JButton.class,
                    CANCEL_BUTTON_COMMAND_NAME);
            getHelper()
                    .enterClickAndLeave(new MouseEventData(this, cancelButton));

            find(JPanel.class, BROWSE_PANEL_COMMAND_NAME);
            table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(2, table.getRowCount());
            mockUserDao.verify();

        } catch (Exception e) {
            fail(e.toString());
        }
    }

    private void findDialog(String title) {
        JDialog dialog;
        DialogFinder dFinder = new DialogFinder(title);
        dialog = (JDialog) dFinder.find();
        assertNotNull("Could not find dialog '" + title + "'", dialog);
        getHelper();
        TestHelper.disposeWindow(dialog, this);
    }

    private void fillField(String firstName, String lastName, Date now) {
        JTextField firstNameField = (JTextField) find(JTextField.class,
                FIRST_NAME_FIELD);
        JTextField lastNameField = (JTextField) find(JTextField.class,
                LAST_NAME_FIELD);
        JTextField dateOfBirthField = (JTextField) find(JTextField.class,
                DATE_OF_BIRTH_FIELD);

        getHelper().sendString(
                new StringEventData(this, firstNameField, firstName));
        getHelper()
                .sendString(new StringEventData(this, lastNameField, lastName));
        DateFormat formatter = DateFormat.getDateInstance();
        String date = formatter.format(now);
        getHelper()
                .sendString(new StringEventData(this, dateOfBirthField, date));
    }

    protected Component find(Class<?> componentClass,
            String componentName) {//получем ссылку на класс и его имя
        NamedComponentFinder finder = new NamedComponentFinder(componentClass,
                componentName);
        finder.setWait(
                0);//количество милисекунд, которое должно пройти до появления каких-то компонентов
        Component component = finder.find(mainFrame,
                0);//будем искать от текцщего контейнера и во всех доерних подконтейнеров, вложенных в него
        assertNotNull("Could not find component '" + componentName + "'",
                component);
        return component;
    }

    public void testDeleteUser() {
        try {
            User expectedUser = new User(1000L, "George", "Bush",
                    new Date());
            mockUserDao.expect("delete", expectedUser);

            ArrayList<User> users = new ArrayList<>();
            mockUserDao.expectAndReturn("findAll", users);

            JTable table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(1, table.getRowCount());
            JButton deleteButton = (JButton) find(JButton.class,
                    DELETE_BUTTON_COMMAND_NAME);
            getHelper().enterClickAndLeave(
                    new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper()
                    .enterClickAndLeave(new MouseEventData(this, deleteButton));

            find(JPanel.class, BROWSE_PANEL_COMMAND_NAME);
            table = (JTable) find(JTable.class, USER_TABLE_COMMAND_NAME);
            assertEquals(0, table.getRowCount());
            mockUserDao.verify();

        } catch (Exception e) {
            fail(e.toString());
        }
    }
}