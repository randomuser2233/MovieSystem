package com.mvms.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null || sessionFactory.isClosed()) {
            registry = new StandardServiceRegistryBuilder().configure().build();
            MetadataSources sources = new MetadataSources(registry);
            Metadata metadata = sources.getMetadataBuilder().build();
            sessionFactory = metadata.buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if(registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
