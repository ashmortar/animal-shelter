package dao;


import models.Animal;
import models.Person;

import java.util.List;

public interface AnimalDao {

    //create
    void add (Animal animal);

    //read
    List<Animal> getAll();
    List<Animal> getAllByType(String type);
    List<Animal> getAllByBreed(String breed);
    List<Animal> getAllByAdmittance();
    Animal findAnimalById(int id);

    //update
    void adopt(int animalId, int ownerId);

//    //delete
//    void deleteById(int id);
//    void clearAllAnimals();
}
