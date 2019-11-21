package ua.nure.rakhman.usermanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

import java.time.LocalDate;


class UserTest extends TestCase {
	
	private User user;
	
	@BeforeEach
	protected void setUp() throws Exception {
		super.setUp();
		
		user = new User();
		
	}
	
	@Test
	public void testGetFullName() {
		user.setName("Patrick");
		user.setSurname("Rakhman");
		
		assertEquals("Patrick, Rakhman", user.getFullName());
	}
	
	// тест моей даты рождения	
	@Test 
	public void testMyGetAge() {
		user.setDateOfBirthday(LocalDate.of(2000, 3, 25));

        assertEquals(19, user.getAge(user.getDateOfBirthday()));
	}
	
	// тест даты рождения в будущем
	@Test 
	public void testGetAgeInFuture() {
		user.setDateOfBirthday(LocalDate.of(2034, 3, 25));

        assertEquals(0, user.getAge(user.getDateOfBirthday()));
	}
	
	// тест даты рождения в высокосном году	
	@Test 
	public void testGetAgeInLeapYear() {
		user.setDateOfBirthday(LocalDate.of(2016, 2, 29));

        assertEquals(3, user.getAge(user.getDateOfBirthday()));
	}
	
	// тест даты рождения, которая вчера была
	@Test 
	public void testGetAgeYesterday() {
		user.setDateOfBirthday(LocalDate.of(2019, 10, 27));

        assertEquals(0, user.getAge(user.getDateOfBirthday()));
	}
	
	// тест даты рождения, которая сегодня
	@Test 
	public void testGetAgeToday() {
		user.setDateOfBirthday(LocalDate.of(2017, 10, 28));

        assertEquals(2, user.getAge(user.getDateOfBirthday()));
	}

}