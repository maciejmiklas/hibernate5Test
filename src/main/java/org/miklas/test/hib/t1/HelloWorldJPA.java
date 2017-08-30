package org.miklas.test.hib.t1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HelloWorldJPA implements AutoCloseable {

    public static void main(String... arg) throws Exception {
        try (HelloWorldJPA hw = new HelloWorldJPA()) {
            hw.printVersion();
        }
    }

    private ServiceRegistry registry;
    private SessionFactory sessionFactory;

    public HelloWorldJPA() {

        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        registryBuilder
                .applySetting(Environment.DRIVER, "org.postgresql.Driver")
                .applySetting(Environment.URL, "jdbc:postgresql://pgts:5432/HIB")
                .applySetting(Environment.USER, "postgres")
                .applySetting(Environment.PASS, "")
                .applySetting(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect")
                .applySetting(Environment.FORMAT_SQL, Boolean.TRUE)
                .applySetting(Environment.HBM2DDL_AUTO, "create-drop");
        registry = registryBuilder.build();

        MetadataSources sources = new MetadataSources(registry);
        sources.addAnnotatedClass(Message.class);
        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public void printVersion() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String result = (String) session.createNativeQuery("select version()").getSingleResult();
            System.out.println(result);

            session.getTransaction().commit();
        }

    }


    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
