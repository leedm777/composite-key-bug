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

/**
 * Unit tests demonstrating my confusion.
 */
public class EntityTest {
    private SessionFactory sessionFactory;
    private Session session;

    @Before
    public void setUp() throws SQLException {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .configure("hibernate-test.cfg.xml")
                .configure("hibernate-hsqldb.cfg.xml")
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
        // flush fails with:
        // identifier of an instance of org.hibernate.example.compositeid.EntityWithCompositeId was altered from CompositeId{id1=1, id2=1} to CompositeId{id1=null, id2=null}
        // actually, id in objectToSave was never updates to the generated id
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
        // flush succeeds, even though it fails in the above test.
        session.flush();
        assertThat("Should have been a CompositeId", id, is(instanceOf(CompositeId.class)));
        CompositeId compositeId = (CompositeId) id;
        assertThat("id should match", objectToSave.getId1(), is(equalTo(compositeId.getId1())));
        assertThat("id should match", objectToSave.getId2(), is(equalTo(compositeId.getId2())));
    }
}
