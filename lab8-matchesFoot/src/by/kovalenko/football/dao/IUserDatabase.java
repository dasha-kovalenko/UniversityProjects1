package by.kovalenko.football.dao;
import java.util.List;

import by.kovalenko.football.domain.User;


public interface IUserDatabase {
	User getUser(int id);
	
	User getUser(String name);
	
	User getUser(String name, String password);
	
	void insertUser(User user);

	void updateUser(User user);
	
	void deleteUser(int id);
	
	List<User> listUsers();

}
