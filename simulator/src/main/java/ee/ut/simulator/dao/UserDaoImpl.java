package ee.ut.simulator.dao;

import java.util.List;

import ee.ut.simulator.pojo.User;
 
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
@Repository
public class UserDaoImpl implements UserDao {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }
 
    @SuppressWarnings("unchecked")
	public List<User> listUsers() {
 
        return sessionFactory.getCurrentSession().createQuery("from User")
                .list();
    }
 
    public void removeUser(long id) {
        User user = (User) sessionFactory.getCurrentSession().load(
                User.class, id);
        if (null != user) {
            sessionFactory.getCurrentSession().delete(user);
        }
 
    }
}