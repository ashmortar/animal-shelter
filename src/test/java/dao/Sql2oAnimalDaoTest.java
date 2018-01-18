package dao;

import models.Animal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class Sql2oAnimalDaoTest {

    private Sql2oAnimalDao animalDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        animalDao = new Sql2oAnimalDao(sql2o);

        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Animal setupNewDog() {
        return new Animal("Pookie", "female", "2018-01-18", "dog", "chihuahua");
    }
    public Animal setupNewCat() {
        return new Animal("mittens", "male", "2018-01-01", "cat", "american shorthair");
    }

    @Test
    public void newAnimal_generateUniqueId_1() throws Exception {
        Animal animal = setupNewDog();
        int originalAnimalId = animal.getId();
        animalDao.add(animal);
        assertNotEquals(originalAnimalId, animal.getId());
    }

    @Test
    public void getAll_returnsAllAnimalObjectsAddedToDb_true() throws Exception {
        Animal animal = setupNewDog();
        Animal nextAnimal = setupNewDog();
        Animal notAddedAnimal = setupNewDog();
        animalDao.add(animal);
        animalDao.add(nextAnimal);
        assertEquals(2, animalDao.getAll().size());
    }

    @Test
    public void getAllByType_returnsAllAnimalObjectsWithThatType() throws Exception {
        Animal animal = setupNewCat();
        Animal secondAnimal = setupNewCat();
        Animal dogAnimal = setupNewDog();
        animalDao.add(animal);
        animalDao.add(secondAnimal);
        animalDao.add(dogAnimal);
        assertEquals(2, animalDao.getAllByType("cat").size());
    }

    @Test
    public void getAllByBreed_returnsAllAnimalObjectsWithSameBreed() throws Exception {
        Animal animal = setupNewDog();
        Animal otherAnimal = setupNewDog();
        Animal thirdAnimal = new Animal("chica", "female", "2018-01-03", "dog", "pit bull");
        animalDao.add(animal);
        animalDao.add(otherAnimal);
        animalDao.add(thirdAnimal);
        assertEquals(2, animalDao.getAllByBreed("chihuahua").size());
    }

    @Test
    public void getAllByAdmittance_returnsAllAnimalsInOrderOfAdmittance() throws Exception {
        Animal animal = setupNewDog(); //actually fourth
        Animal secondAnimal = setupNewDog(); //actually first
        secondAnimal.setAdmittance("2017-01-18");
        Animal thirdAnimal = setupNewCat(); //actually third
        Animal fourthAnimal = setupNewCat(); //actually second
        fourthAnimal.setAdmittance("2017-06-06");
        animalDao.add(animal);
        animalDao.add(secondAnimal);
        animalDao.add(thirdAnimal);
        animalDao.add(fourthAnimal);
        List<Animal> expectedOrder = new ArrayList<>();
        expectedOrder.add(secondAnimal);
        expectedOrder.add(fourthAnimal);
        expectedOrder.add(thirdAnimal);
        expectedOrder.add(animal);
        assertTrue(expectedOrder.equals(animalDao.getAllByAdmittance()));
    }

    @Test
    public void getAnimalById_returnsAnimalWithCorrespondingId() throws Exception {
        Animal animal = setupNewDog();
        Animal otherAnimal = setupNewCat();
        animalDao.add(animal);
        animalDao.add(otherAnimal);
        assertEquals("dog", animalDao.findAnimalById(1).getType());
    }
    @Test
    public void adoptChangesStatus_returnsAnimalWithTrueAdoptedProperty() throws Exception {
        Animal animal = setupNewDog();
        Animal secondAnimal = setupNewCat();
        animalDao.add(animal);
        animalDao.add(secondAnimal);
        animalDao.adopt(1, 1);
        assertEquals(true, animalDao.findAnimalById(1).getAdopted());
        assertEquals(false, animalDao.findAnimalById(2).getAdopted());
    }

    @Test
    public void deleteById_deletesCorrectly_true() throws Exception {
        Animal animal = setupNewDog();
        Animal secondAnimal = setupNewCat();
        animalDao.add(animal);
        animalDao.add(secondAnimal);
        animalDao.deleteById(1);
        assertEquals(1, animalDao.getAll().size());
    }

    @Test
    public void clearAllAnimals_removesAllAnimalEntries_true() throws Exception {
        Animal testAnimal = setupNewCat();
        Animal secondTest = setupNewDog();
        Animal thirdTest = setupNewDog();
        animalDao.add(testAnimal);
        animalDao.add(secondTest);
        animalDao.add(thirdTest);
        animalDao.clearAllAnimals();
        assertEquals(0, animalDao.getAll().size());
    }
}