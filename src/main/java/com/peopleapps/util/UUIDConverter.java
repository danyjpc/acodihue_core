package com.peopleapps.util;

import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectCollectionMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

import java.util.UUID;

public class UUIDConverter implements Converter {

    @Override
    public Object convertObjectValueToDataValue(Object o, Session session) {
        return o;
    }

    @Override
    public UUID convertDataValueToObjectValue(Object o, Session session) {
        return (UUID) o;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public void initialize(DatabaseMapping databaseMapping, Session session) {

        final DatabaseField field;

        if (databaseMapping instanceof DirectCollectionMapping) {

            // handle @ElementCollection...

            field = ((DirectCollectionMapping) databaseMapping).getDirectField();

        } else {

            field = databaseMapping.getField();

        }

        field.setSqlType(java.sql.Types.OTHER);

        field.setTypeName("java.util.UUID");

        field.setColumnDefinition("your column name in database");

    }
}
