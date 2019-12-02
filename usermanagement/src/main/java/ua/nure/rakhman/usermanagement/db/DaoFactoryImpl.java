package ua.nure.rakhman.usermanagement.db;

import ua.nure.rakhman.usermanagement.domain.User;

public class DaoFactoryImpl extends DaoFactory {

	@Override
	public Dao<User> getUserDao() {
		Dao<User> result;
		try {
			Class<?> clazz = Class.forName(properties.getProperty(USER_DAO));
			result=(Dao<User>) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}

}