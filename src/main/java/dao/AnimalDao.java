package dao;


import models.Animal;
import models.Person;

import java.util.List;

public interface AnimalDao {

    //create
    void add (Animal animal);

    //read
    List<Animal> getAll();
//    List<Animal> getAllByType();
//    List<Animal> getAllByBreed();
//    List<Animal> getAllByAdmittance();
//
//    //update
//    void adopt(int id, Person person);
//
//    //delete
//    void deleteById(int id);
//    void clearAllAnimals();
}
