package models;


public class Person {
    private String name;
    private String phone;
    private String typePreference;
    private String breedPreference;
    private int id;

    public Person(String name, String phone, String typePreference, String breedPreference) {
        this.name = name;
        this.phone = phone;
        this.typePreference = typePreference;
        this.breedPreference = breedPreference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTypePreference() {
        return typePreference;
    }

    public void setTypePreference(String typePreference) {
        this.typePreference = typePreference;
    }

    public String getBreedPreference() {
        return breedPreference;
    }

    public void setBreedPreference(String breedPreference) {
        this.breedPreference = breedPreference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (!name.equals(person.name)) return false;
        if (phone != null ? !phone.equals(person.phone) : person.phone != null) return false;
        if (typePreference != null ? !typePreference.equals(person.typePreference) : person.typePreference != null)
            return false;
        return breedPreference != null ? breedPreference.equals(person.breedPreference) : person.breedPreference == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (typePreference != null ? typePreference.hashCode() : 0);
        result = 31 * result + (breedPreference != null ? breedPreference.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
