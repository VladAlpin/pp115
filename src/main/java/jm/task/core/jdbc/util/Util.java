package jm.task.core.jdbc.util;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static SessionFactory sessionFactory;

    public static SessionFactory getConnection() {
        if (sessionFactory == null) {
                Configuration configuration = new Configuration()
                        .addAnnotatedClass(User.class)
                        .setProperty(Environment.DRIVER, DRIVER)
                        .setProperty(Environment.URL, URL)
                        .setProperty(Environment.USER, USERNAME)
                        .setProperty(Environment.PASS, PASSWORD)
                        .setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect")
                        .setProperty(Environment.SHOW_SQL, "true")
                        .setProperty(Environment.HBM2DDL_AUTO, "");
                System.out.println("good connection!");
            try {
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                System.err.println("no good connection!" + e);
            }
        }
        return sessionFactory;
    }


//    public static Connection getConnection() {
//        Connection connection = null;
//        try {
//            Class.forName(DB_DRIVER);
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            System.out.println("good connection!");
//        } catch (SQLException e) {
//            System.err.println("no good connection!" + e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return connection;
//    }

}
