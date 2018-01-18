package dao;

import models.Animal;
import models.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class Sql2oPersonDaoTest {
    private Sql2oPersonDao personDao; //ignore me for now
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        personDao = new Sql2oPersonDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Person setupNewPerson() {
        return new Person("Gary", "1234567890", "dog", "chihuahua");
    }

    @Test
    public void newPerson_generateUniqueId_1() throws Exception {
        Person person = setupNewPerson();
        int originalPersonId = person.getId();
        personDao.add(person);
        assertNotEquals(originalPersonId, person.getId());
    }

    @Test
    public void getAll_returnsAllPersonObjectsAddedToDb_true() throws Exception {
        Person person = setupNewPerson();
        Person nextPerson = setupNewPerson();
        Person notAddedPerson = setupNewPerson();
        personDao.add(person);
        personDao.add(nextPerson);
        assertEquals(2, personDao.getAll().size());
    }

    @Test
    public void getAllByPersonsByTypePerference_returnsAllPersonObjectsWithThatTypePreference() throws Exception {
        Person person = setupNewPerson();
        Person secondPerson = setupNewPerson();
        Person catPerson = setupNewPerson();
        catPerson.setTypePreference("cat");
        personDao.add(person);
        personDao.add(secondPerson);
        personDao.add(catPerson);
        assertEquals(2, personDao.getAllPersonsByTypePreference("dog").size());
    }

    @Test
    public void getAllPersonsByBreedPreference_returnsAllPersonObjectsWithSameBreedPreference() throws Exception {
        Person person = setupNewPerson();
        Person otherPerson = setupNewPerson();
        Person pitPerson = new Person("Sally", "0987654321", "dog", "pit bull");
        personDao.add(person);
        personDao.add(otherPerson);
        personDao.add(pitPerson);
        assertEquals(2, personDao.getAllPersonsByBreedPreference("chihuahua").size());
    }


    @Test
    public void findPersonById_returnsPersonWithCorrespondingId() throws Exception {
        Person person = setupNewPerson();
        Person otherPerson = setupNewPerson();
        otherPerson.setName("Grendel");
        personDao.add(person);
        personDao.add(otherPerson);
        assertEquals("Grendel", personDao.findPersonById(2).getName());
    }

    @Test
    public void updatePerson_updatesPersonValuesCorreclty_true() throws Exception {
        Person person = setupNewPerson();
        personDao.add(person);
        personDao.updatePerson(1, "Belinda", "0987654321", "cat", "none");
        assertEquals("cat", personDao.findPersonById(1).getTypePreference());
    }

        @Test
    public void deleteById_deletesCorrectly_true() throws Exception {
        Person person = setupNewPerson();
        Person secondPerson = setupNewPerson();
        personDao.add(person);
        personDao.add(secondPerson);
        personDao.deleteById(1);
        assertEquals(1, personDao.getAll().size());
    }

    @Test
    public void destroyAllHumans_removesAllPersonEntries_true() throws Exception {
        Person testPerson = setupNewPerson();
        Person secondTest = setupNewPerson();
        Person thirdTest = setupNewPerson();
        personDao.add(testPerson);
        personDao.add(secondTest);
        personDao.add(thirdTest);
        personDao.destroyAllHumans();
        assertEquals(0, personDao.getAll().size());
    }

}