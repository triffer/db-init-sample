/*
 * Copyright (c) 2019 Traversals Analytics and Intelligence GmbH. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited. This
 * is proprietary and confidential.
 */
package com.triffer.dbunit.testsupport.commands.base;

import com.triffer.dbunit.testsupport.config.TestDatabase;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommand implements Command {

    private final TestDatabase testDatabase;
    private final Map<String, Object> replacementObjects;

    protected AbstractCommand(TestDatabase testDatabase) {
        this.testDatabase = testDatabase;
        this.replacementObjects = new HashMap<>();
        putDefaultReplaceValues();
    }

    @Override
    public void execute() {
        String dataDefinition = getDataDefinition();
        try {
            testDatabase.insertIntoTables(new ClassPathResource(dataDefinition).getURL(), this.replacementObjects);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load data definition from " + dataDefinition, e);
        }
    }

    public void putValue(String columnName, Object Value) {
        replacementObjects.put(String.format("[%s.%s]", getTableName(), columnName), Value);
    }

    protected abstract void putDefaultReplaceValues();

    protected abstract String getTableName();

    protected abstract String getDataDefinition();
}
