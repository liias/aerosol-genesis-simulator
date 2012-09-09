package ee.ut.simulator.dao;

import ee.ut.simulator.pojo.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void addUser(User user) {
        entityManager.persist(user);
    }


    public List<User> listUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    public void removeUser(long id) {
        User user = entityManager.find(User.class, id);
        if (null != user) {
            entityManager.remove(user);
        }
    }
}