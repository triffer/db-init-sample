package com.triffer.dbunit.testsupport.commands;

import com.triffer.dbunit.testsupport.commands.base.Command;
import com.triffer.dbunit.testsupport.config.TestDatabase;
import lombok.extern.slf4j.Slf4j;

/**
 * Command to explicite clean the database
 */
@Slf4j
public class CleanDb implements Command {

    private final TestDatabase testDatabase;

    private CleanDb(TestDatabase testDatabase) {
        this.testDatabase = testDatabase;
    }

    public static CleanDb instance(TestDatabase testDatabase) {
        return new CleanDb(testDatabase);
    }

    @Override
    public void execute() {
        log.info("Deleting all test data.");
        testDatabase.truncateAllTables();
    }
}
