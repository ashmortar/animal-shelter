package dao;


import javafx.scene.AmbientLight;
import models.Animal;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oAnimalDao implements AnimalDao {

    private final Sql2o sql2o;
    public Sql2oAnimalDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Animal animal) {
        String sql = "INSERT INTO animals(name, gender, admittance, type, breed) VALUES (:name, :gender, :admittance, :type, :breed)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(animal)
                    .executeUpdate()
                    .getKey();
            animal.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Animal> getAll() {
        String sql = "SELECT * FROM animals";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Animal.class);
        }
    }

    @Override
    public List<Animal> getAllByType(String type) {
        String sql = "SELECT * FROM animals WHERE type = :type";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("type", type)
                    .executeAndFetch(Animal.class);
        }
    }

    @Override
    public List<Animal> getAllByBreed(String breed) {
        String sql = "SELECT * FROM animals WHERE breed = :breed";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("breed", breed)
                    .executeAndFetch(Animal.class);
        }
    }

    @Override
    public List<Animal> getAllByAdmittance() {
        String sql = "SELECT * FROM animals ORDER BY admittance ASC";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Animal.class);
        }
    }

    @Override
    public Animal findAnimalById(int id) {
        String sql = "SELECT * FROM animals WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animal.class);
        }

    }

    @Override
    public void adopt(int animalId, int ownerId) {
        String sql = "UPDATE animals SET adopted = true, ownerId = :ownerId WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", animalId)
                    .addParameter("ownerId", ownerId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


}
