package org.hibernate.example.compositeid;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class CompositeIdGenerator implements PersistentIdentifierGenerator, Configurable {
    private SequenceStyleGenerator realGenerator = new SequenceStyleGenerator();

    @Override
    public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
        // The real generator will generate longs for us
        realGenerator.configure(new LongType(), params, dialect);
    }

    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        // This is just a stupid example, so I just need some sort of
        // composite key
        Serializable realKey = realGenerator.generate(session, null);
        return new CompositeId(1L, (Long)realKey);
    }


    @Override
    public String[] sqlCreateStrings(Dialect dialect) throws HibernateException {
        return realGenerator.sqlCreateStrings(dialect);
    }

    @Override
    public String[] sqlDropStrings(Dialect dialect) throws HibernateException {
        return realGenerator.sqlDropStrings(dialect);
    }

    @Override
    public Object generatorKey() {
        return realGenerator.generatorKey();
    }
}
