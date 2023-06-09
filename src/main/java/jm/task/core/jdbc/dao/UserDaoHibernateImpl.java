package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = jm.task.core.jdbc.util.Util.getSessionFactory();
    private static Transaction transaction;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS user " +
                            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(255) NOT NULL, " +
                            "lastName VARCHAR(255) NOT NULL, " +
                            "age INT NOT NULL)")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("no create table " + e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("no drop table" + e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            System.err.println("no save user " + e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            int delete = session.createQuery("DELETE FROM User " + "WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("no remove user " + e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            list = session.createCriteria(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("no get all user" + e);
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<User> instances = session.createCriteria(User.class).list();
            for (Object i : instances) {
                session.delete(i);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("no clean user" + e);
        }
    }
}
