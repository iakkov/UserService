/*
package ru.project.iakov.homework2.legacy;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.project.iakov.homework2.User;

public class HibernateUtil {
    @Getter
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.addAnnotatedClass(User.class);
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void rebuildSessionFactoryForTests() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.connection.url", System.getProperty("DB_URL"));
        configuration.setProperty("hibernate.connection.username", System.getProperty("DB_USERNAME"));
        configuration.setProperty("hibernate.connection.password", System.getProperty("DB_PASSWORD"));

        configuration.addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }
}*/
