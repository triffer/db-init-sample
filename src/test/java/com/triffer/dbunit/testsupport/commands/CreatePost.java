package com.triffer.dbunit.testsupport.commands;

import com.triffer.dbunit.testsupport.commands.base.AbstractCommand;
import com.triffer.dbunit.testsupport.config.TestDatabase;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class CreatePost extends AbstractCommand {

    private CreatePost(TestDatabase testDatabase) {
        super(testDatabase);
    }

    public static CreatePost instance(TestDatabase testDatabase) {
        return new CreatePost(testDatabase);
    }

    public CreatePost withId(long value) {
        putValue("ID", value);
        return this;
    }

    public CreatePost withText(String value) {
        putValue("TEXT", value);
        return this;
    }

    public CreatePost withTitle(String value) {
        putValue("TITLE", value);
        return this;
    }

    public CreatePost withPersonId(long value) {
        putValue("PERSON_ID", value);
        return this;
    }

    @Override
    protected void putDefaultReplaceValues() {
        putValue("ID", 1);
        putValue("TEXT", "default text");
        putValue("TITLE", "default title");
    }

    @Override
    protected String getTableName() {
        return "POST";
    }

    @Override
    protected String getDataDefinition() {
        return "testsupport/datasets/definition/post.xml";
    }

}
