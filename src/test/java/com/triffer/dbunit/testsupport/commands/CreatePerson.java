package com.triffer.dbunit.testsupport.commands;

import com.triffer.dbunit.testsupport.commands.base.AbstractCommand;
import com.triffer.dbunit.testsupport.config.TestDatabase;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class CreatePerson extends AbstractCommand {

    private CreatePerson(TestDatabase testDatabase) {
        super(testDatabase);
    }

    public static CreatePerson instance(TestDatabase testDatabase) {
        return new CreatePerson(testDatabase);
    }

    public CreatePerson withId(long value) {
        putValue("ID", value);
        return this;
    }

    public CreatePerson withName(String value) {
        putValue("NAME", value);
        return this;
    }

    @Override
    protected void putDefaultReplaceValues() {
        putValue("ID", 1L);
        putValue("NAME", "DefaultUser");
    }

    @Override
    protected String getTableName() {
        return "PERSON";
    }

    @Override
    protected String getDataDefinition() {
        return "testsupport/datasets/definition/person.xml";
    }
}
