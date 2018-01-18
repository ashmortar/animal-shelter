package dao;


import models.Animal;
import models.Person;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oPersonDao implements PersonDao {
    private final Sql2o sql2o;
    public Sql2oPersonDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public void add(Person person) {
        String sql = "INSERT INTO persons (name, phone, typePreference, breedPreference) VALUES (:name, :phone, :typePreference, :breedPreference)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(person)
                    .executeUpdate()
                    .getKey();
            person.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Person> getAll() {
        String sql = "SELECT * FROM persons";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Person.class);
        }
    }

    @Override
    public List<Person> getAllPersonsByTypePreference(String typePreference) {
        String sql = "SELECT * FROM persons WHERE typePreference = :typePreference";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("typePreference", typePreference)
                    .executeAndFetch(Person.class);
        }
    }

    @Override
    public List<Person> getAllPersonsByBreedPreference(String breedPreference) {
        String sql = "SELECT * FROM persons WHERE breedPreference = :breedPreference";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("breedPreference", breedPreference)
                    .executeAndFetch(Person.class);
        }
    }

    @Override
    public Person findPersonById(int id) {
        String sql = "SELECT * FROM persons WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Person.class);
        }

    }

    @Override
    public void updatePerson(int id, String name, String phone, String typePreference, String breedPreference) {
        String sql = "UPDATE persons SET name = :name, phone = :phone, typePreference = :typePreference, breedPreference = :breedPreference WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("phone", phone)
                    .addParameter("typePreference", typePreference)
                    .addParameter("breedPreference", breedPreference)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from persons WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void destroyAllHumans() {
        String sql = "DELETE FROM persons";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}
