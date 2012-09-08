package ee.ut.simulator.service;

import java.util.List;

import ee.ut.simulator.pojo.User;
 
public interface UserService {
 
    public void addUser(User user);
    public List<User> listUsers();
    public void removeUser(long id);
}