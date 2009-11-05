package org.hibernate.example.compositeid;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


public class EntityTest {
    private SessionFactory sessionFactory;
    private Session session;

    @Before
    public void setUp() throws SQLException {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .configure("hibernate-test.cfg.xml")
                .setNamingStrategy(new ImprovedNamingStrategy())
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    @After
    public void tearDown() {
        if (session != null) {
            session.close();
            session = null;
        }
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }

    @Test
    public void testSimpleSave() {
        EntityWithCompositeId objectToSave = new EntityWithCompositeId();
        Serializable id = session.save(objectToSave);
        session.flush();
        assertThat("Should have been a CompositeId", id, is(instanceOf(CompositeId.class)));
        CompositeId compositeId = (CompositeId) id;
        assertThat("id should match", objectToSave.getId1(), is(equalTo(compositeId.getId1())));
        assertThat("id should match", objectToSave.getId2(), is(equalTo(compositeId.getId2())));
    }

    @Test
    public void testSimpleSubclassSave() {
        SubclassWithCompositeId objectToSave = new SubclassWithCompositeId();
        Serializable id = session.save(objectToSave);
        session.flush();
        assertThat("Should have been a CompositeId", id, is(instanceOf(CompositeId.class)));
        CompositeId compositeId = (CompositeId) id;
        assertThat("id should match", objectToSave.getId1(), is(equalTo(compositeId.getId1())));
        assertThat("id should match", objectToSave.getId2(), is(equalTo(compositeId.getId2())));
    }
}
