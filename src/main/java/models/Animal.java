package models;


import java.util.Date;

public class Animal {
    private String name;
    private String gender;
    private String admittance;
    private String type;
    private String breed;
    private int ownerId;
    private Boolean adopted;
    private int id;

    public Animal(String name, String gender, String admittance, String type, String breed) {
        this.name = name;
        this.gender = gender;
        this.admittance = admittance;
        this.type = type;
        this.breed = breed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdmittance() {
        return admittance;
    }

    public void setAdmittance(String admittance) {
        this.admittance = admittance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getAdopted() {
        return adopted;
    }

    public void setAdopted(Boolean adopted) {
        this.adopted = adopted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal animal = (Animal) o;

        if (ownerId != animal.ownerId) return false;
        if (id != animal.id) return false;
        if (!name.equals(animal.name)) return false;
        if (gender != null ? !gender.equals(animal.gender) : animal.gender != null) return false;
        if (admittance != null ? !admittance.equals(animal.admittance) : animal.admittance != null) return false;
        if (type != null ? !type.equals(animal.type) : animal.type != null) return false;
        if (breed != null ? !breed.equals(animal.breed) : animal.breed != null) return false;
        return adopted != null ? adopted.equals(animal.adopted) : animal.adopted == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (admittance != null ? admittance.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (breed != null ? breed.hashCode() : 0);
        result = 31 * result + ownerId;
        result = 31 * result + (adopted != null ? adopted.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
