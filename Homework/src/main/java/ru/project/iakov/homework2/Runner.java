package ru.project.iakov.homework2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Runner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();) {
            session.beginTransaction();

            session.save(User.builder());

            session.getTransaction().commit();
        }
    }
}
