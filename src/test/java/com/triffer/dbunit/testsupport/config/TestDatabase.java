/*
 * Copyright (c) 2019 Traversals Analytics and Intelligence GmbH. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited. This
 * is proprietary and confidential.
 */
package com.triffer.dbunit.testsupport.config;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

@Slf4j
public class TestDatabase {

    private final Insert INSERT;
    private final Truncate TRUNCATE;

    @Getter
    private final IDatabaseConnection databaseConnection;

    public TestDatabase(IDatabaseConnection databaseConnection) {
        this.INSERT = new Insert(databaseConnection);
        this.TRUNCATE = new Truncate(databaseConnection);

        this.databaseConnection = databaseConnection;
    }

    public void insertIntoTables(URL dbunitXMLFile, Map<String, Object> replacementObjects) {
        INSERT.intoTables(dbunitXMLFile, replacementObjects);
    }

    public void truncateAllTables() {
        TRUNCATE.allTables();
    }

    private static class Insert {
        private final IDatabaseConnection databaseConnection;

        Insert(IDatabaseConnection databaseConnection) {
            this.databaseConnection = databaseConnection;
        }

        void intoTables(URL dbunitXMLFile, Map<String, Object> replacementObjects) {
            try {
                IDataSet dataSet = new FlatXmlDataSetBuilder().build(dbunitXMLFile);
                if (replacementObjects != null) {
                    dataSet = replaceData(dataSet, replacementObjects);
                }
                DatabaseOperation.INSERT.execute(databaseConnection, dataSet);
            } catch (SQLException | DatabaseUnitException e) {
                throw new IllegalStateException("Failed to initialize test data.", e);
            }
        }

        private IDataSet replaceData(IDataSet dataSet, Map<String, Object> replacementObjects) {
            ReplacementDataSet rDataSet = new ReplacementDataSet(dataSet);
            rDataSet.setStrictReplacement(true);

            Set<String> keys = replacementObjects.keySet();
            for (String key : keys) {
                Object value = replacementObjects.get(key);
                rDataSet.addReplacementObject(key, value);
            }
            return rDataSet;
        }
    }

    private static class Truncate {
        private final IDatabaseConnection databaseConnection;

        Truncate(IDatabaseConnection databaseConnection) {
            this.databaseConnection = databaseConnection;
        }

        void allTables() {
            try {
                log.info("Cleaning test data.");
                IDataSet tablesToBeTruncated = loadFlatXmlDataSet("testsupport/datasets/cleanup.xml");
                DatabaseOperation.DELETE_ALL.execute(databaseConnection, tablesToBeTruncated);
            } catch (SQLException | DatabaseUnitException e) {
                throw new IllegalStateException("Failed to clean test data.", e);
            }
        }
    }

    private static IDataSet loadFlatXmlDataSet(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("dataset filename must not be empty or null");
        }
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        if (stream == null) {
            throw new IllegalArgumentException("the file " + filename + " was not found on the classpath");
        }
        log.debug("loading dataset " + filename);
        ReplacementDataSet set = null;
        try {
            set = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(new InputSource(stream)));
            set.addReplacementObject("[NULL]", null);
            set.addReplacementObject("[null]", null);
        } catch (DataSetException e) {
            log.error("Failed to initialize test data for file " + filename, e);
        }
        return set;
    }

}

