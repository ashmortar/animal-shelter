import dao.Sql2oAnimalDao;
import dao.Sql2oPersonDao;
import models.Animal;
import models.Person;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oAnimalDao animalDao = new Sql2oAnimalDao(sql2o);
        Sql2oPersonDao personDao = new Sql2oPersonDao(sql2o);

        //================routes=======================//

        // /  - homepage
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        // /animals/deleteAll
        get("/animals/deleteAll", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> allAnimals = animalDao.getAll();
            model.put("animals", allAnimals);
            animalDao.clearAllAnimals();
            return new ModelAndView(model, "delete.hbs");
        }, new HandlebarsTemplateEngine());

        //  /persons/deleteAll
        get("/persons/deleteAll", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Person> allPersons = personDao.getAll();
            model.put("persons", allPersons);
            personDao.destroyAllHumans();
            return new ModelAndView(model, "delete.hbs");
        }, new HandlebarsTemplateEngine());

        // /animals/new
        //get
        get("/animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());
        //post
        post("/animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            String name = request.queryParams("name");
            String gender = request.queryParams("gender");
            String admittance = request.queryParams("admittance");
            String type = request.queryParams("type");
            String breed = request.queryParams("breed");

            Animal newAnimal = new Animal(name, gender, admittance, type, breed);
            animalDao.add(newAnimal);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        // /persons/new
        //get
        get("/persons/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "person-form.hbs");
        }, new HandlebarsTemplateEngine());
        //post
        post("/persons/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            String name = request.queryParams("name");
            String phone = request.queryParams("phone");
            String typePreference = request.queryParams("typePreference");
            String breedPreference = request.queryParams("breedPreference");

            Person newPerson = new Person(name, phone, typePreference, breedPreference);
            personDao.add(newPerson);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());


        // /animals = all animals
        get("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> allAnimals = animalDao.getAll();
            model.put("animals", allAnimals);
            return new ModelAndView(model, "animals.hbs");
        }, new HandlebarsTemplateEngine());

        // /animals/type = all animals of type
        get("/animals/type", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String type = request.queryParams("type");
            List<Animal> allAnimals = animalDao.getAllByType(type);
            model.put("animals", allAnimals);
            return new ModelAndView(model, "animals.hbs");
        }, new HandlebarsTemplateEngine());

        // /animals/breed = all of same breed
        get("/animals/breed", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String breed = request.queryParams("breed");
            List<Animal> allAnimals = animalDao.getAllByType(breed);
            model.put("animals", allAnimals);
            return new ModelAndView(model, "animals.hbs");
        }, new HandlebarsTemplateEngine());

        // /persons = show all potential owners
        get("/persons", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Person> allPersons = personDao.getAll();
            model.put("persons", allPersons);
            return new ModelAndView(model, "persons.hbs");
        }, new HandlebarsTemplateEngine());

        // /persons/typePreference = all persons w/ type preference
        get("/persons/typePreference", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String typePreference = request.queryParams("typePreference");
            List<Person> personsOfType = personDao.getAllPersonsByTypePreference(typePreference);
            model.put("persons", personsOfType);
            return new ModelAndView(model, "persons.hbs");
        }, new HandlebarsTemplateEngine());

        // /persons/breedPreference = all persons w/ breed pref.
        get("/persons/breedPreference", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String breedPreference = request.queryParams("breedPreference");
            List<Person> personsOfBreed = personDao.getAllPersonsByTypePreference(breedPreference);
            model.put("persons", personsOfBreed);
            return new ModelAndView(model, "persons.hbs");
        }, new HandlebarsTemplateEngine());

        //  /animals/:id = show animal detail and form to adopt
        get("/animals/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params("id"));
            Animal animal = animalDao.findAnimalById(id);
            model.put("animal", animal);
            return new ModelAndView(model, "animal-detail.hbs");
        }, new HandlebarsTemplateEngine());

//        post("/animals/:id", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//
//            return new ModelAndView(model, "success.hbs");
//
//        }, new HandlebarsTemplateEngine());

        // /persons/:id = show person detail and form to update
        get("/persons/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params("id"));
            Person person = personDao.findPersonById(id);
            model.put("person", person);
            return new ModelAndView(model, "person-detail.hbs");

        }, new HandlebarsTemplateEngine());

//        post("/persons/:id", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//
//
//            return new ModelAndView(model, "success.hbs");
//
//        }, new HandlebarsTemplateEngine());


        // /animals/:id/delete = remove specific animal

        // /persons/:id/delete = remove specific person


    }
}
