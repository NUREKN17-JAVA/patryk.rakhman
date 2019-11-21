package ITKN177Rakhman.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ITKN177Rakhman.domain.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {

	private static final Date DATE_OF_BIRTH_UPDATE2 = new Date(1999-10-16);
	private static final Date DATE_OF_BIRTH_UPDATE = new Date(2000-04-17);
	private static final String LAST_NAME_UPDATE2 = "Borodin";
	private static final String FIRST_NAME_UPDATE2 = "Nikita";
	private static final String FIRST_NAME2 = "Will";
	private static final String LAST_NAME2 = "Smith";
	private static final long ID = 1000L;

	private static final String LAST_NAME_UPDATE = "Rakhman";
	private static final String FIRST_NAME_UPDATE = "Patrick";
	private static final String LAST_NAME = "Due";
	private static final String FIRST_NAME = "John";
	
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}
	
	public void testCreate() throws DatabaseException {
		User user = new User();
		user.setFirstName(FIRST_NAME);
		user.setLastName(LAST_NAME);
		user.setDateOfBirth(new Date());
		assertNull(user.getId());
		User userToChek = dao.create(user);
		assertNotNull(userToChek);
		assertNotNull(userToChek.getId());
		assertEquals(user.getFirstName(),userToChek.getFirstName());
		assertEquals(user.getLastName(),userToChek.getLastName());
		assertEquals(user.getDateOfBirth(),userToChek.getDateOfBirth());		
	}
	public void testUpdate() throws DatabaseException{
		User user = new User();
		user.setFirstName(FIRST_NAME_UPDATE);
		user.setLastName(LAST_NAME_UPDATE);
		user.setDateOfBirth(DATE_OF_BIRTH_UPDATE);
		User userToChek = dao.create(user);
		assertEquals(FIRST_NAME_UPDATE,userToChek.getFirstName());
		assertEquals(LAST_NAME_UPDATE,userToChek.getLastName());
		assertEquals(DATE_OF_BIRTH_UPDATE,userToChek.getDateOfBirth());
		userToChek.setFirstName(FIRST_NAME_UPDATE2);
		userToChek.setLastName(LAST_NAME_UPDATE2);
		userToChek.setDateOfBirth(DATE_OF_BIRTH_UPDATE2);
		dao.update(userToChek);
		assertEquals(FIRST_NAME_UPDATE2,userToChek.getFirstName());
		assertEquals(LAST_NAME_UPDATE2,userToChek.getLastName());
		assertEquals(DATE_OF_BIRTH_UPDATE2,userToChek.getDateOfBirth());
	}
	public void testDelete() throws DatabaseException{
		User user = new User();
		user.setFirstName(FIRST_NAME_UPDATE);
		user.setLastName(LAST_NAME_UPDATE);
		user.setDateOfBirth(DATE_OF_BIRTH_UPDATE);
		User userToChek = dao.create(user);
		assertNotNull(userToChek.getId());
		dao.delete(userToChek);
		User user2 = dao.find(userToChek.getId());
		assertNull(user2);
		assertNull(user2.getId());
	}
	
	public void testFind() throws DatabaseException{
		User userToChek = dao.find(ID);
		assertEquals("Wrong data",LAST_NAME2,userToChek.getLastName());
		assertEquals("Wrong data",FIRST_NAME2,userToChek.getFirstName());
	}
	
	public void testFindAll() throws DatabaseException {
		Collection <User> users= dao.findAll();
		assertNotNull(users);
		assertEquals("Collection size does not match",2, users.size());
	}
	//getDataSet().getTable("users").
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver"
				,"jdbc:hsqldb:file:db/usermanagment"
				,"sa"
				,"");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet=new XmlDataSet(
				getClass().
				getClassLoader().
				getResourceAsStream("usersDataSet.xml")
				);
		return dataSet;
	}
	
	
	
}