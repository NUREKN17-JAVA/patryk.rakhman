package ua.nure.rakhman.usermanagement.db;

import junit.framework.TestCase;
import ua.nure.rakhman.usermanagement.db.Dao;
import ua.nure.rakhman.usermanagement.db.DaoFactory;

public class DaoFactoryTest extends TestCase {

	public void testGetUserDao() {
		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			assertNotNull("DaoFactory is null",daoFactory);
			Dao userDao = daoFactory.getUserDao();
			assertNotNull("UserDao is null",userDao);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
}
