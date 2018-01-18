package dao;


import models.Person;

import java.util.List;

public interface PersonDao {

//    //create
    void add(Person person);

//    //read
    List<Person> getAll();
    List<Person> getAllPersonsByTypePreference(String typePreference);
    List<Person> getAllPersonsByBreedPreference(String breedPreference);
    Person findPersonById(int id);

//    //update
    void updatePerson(int id, String newName, String newPhone, String newTypePreference, String newBreedPreference);
//
//    //delete
    void deleteById(int id);
    void destroyAllHumans();
}
