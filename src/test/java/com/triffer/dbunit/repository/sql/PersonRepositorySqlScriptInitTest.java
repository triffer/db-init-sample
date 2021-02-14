package com.triffer.dbunit.repository.sql;

import com.triffer.dbunit.model.Person;
import com.triffer.dbunit.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

public class PersonRepositorySqlScriptInitTest extends RepositorySqlScriptInitTest {

    @Autowired
    private PersonRepository sut;

    @Test
    @Sql("/testdata/person/findAll.sql")
    void findAll() {
        // when
        List<Person> result = sut.findAll();

        // then
        assertFalse(result.isEmpty());
        assertEquals("testperson", result.get(0).getName());
        assertFalse(result.get(0).getPosts().isEmpty());
        assertEquals("a title", result.get(0).getPosts().iterator().next().getTitle());
    }

    @Test
    @Sql("/testdata/person/findAllByNameOrderByIdDesc_filtersByNameAndOrdersByIdDesc.sql")
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