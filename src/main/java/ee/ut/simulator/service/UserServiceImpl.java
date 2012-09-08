package ee.ut.simulator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import ee.ut.simulator.dao.UserDao;
import ee.ut.simulator.pojo.User;
 
@Service
public class UserServiceImpl implements UserService {
 
    @Autowired
    private UserDao userDao;
 
    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
    }
 
    @Transactional
    public List<User> listUsers() {
        return userDao.listUsers();
    }
 
    @Transactional
    public void removeUser(long id) {
        userDao.removeUser(id);
    }
}