package com.triffer.dbunit.repository.sql;

import com.triffer.dbunit.model.Person;
import com.triffer.dbunit.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

public class PersonRepositorySqlScriptScriptInitTest extends RepositoryIntegrationSqlScriptInitTest {

    @Autowired
    private PersonRepository sut;

    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/testdata/person/findAll.sql"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/testdata/person/clean.sql")
    })
    void findAll() {
        // when
        List<Person> result = sut.findAll();

        // then
        assertFalse(result.isEmpty());
        assertEquals("testperson", result.get(0).getName());
        assertFalse(result.get(0).getPostSet().isEmpty());
        assertEquals("a title", result.get(0).getPostSet().iterator().next().getTitle());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/testdata/person/findAllByNameOrderByIdDesc_filtersByNameAndOrdersByIdDesc.sql"),
            @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/testdata/person/clean.sql")
    })
    void findAllByNameOrderByIdDesc_filtersByNameAndOrdersByIdDesc() {
        // when
        List<Person> result = sut.findAllByNameOrderByIdDesc("Testperson");

        // then
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("Testperson", result.get(0).getName());
        assertEquals(11L, result.get(0).getId());
        assertEquals(10L, result.get(1).getId());
    }
}