package ee.ut.simulator.dao;

import java.util.List;

import ee.ut.simulator.domain.User;
 
public interface UserDao {
	 
    public void addUser(User user);
    public List<User> listUsers();
    public void removeUser(long id);
}