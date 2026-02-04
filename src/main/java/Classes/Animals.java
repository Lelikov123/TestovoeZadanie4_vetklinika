package Classes;

import java.time.LocalDate;

public class Animals {
    private int ID_animal;
    private int ID_owner;
    private String name;
    private String species;
    private String breed;
    private LocalDate birth_date;
    private String gender;
    private String color;

    public Animals(){}

    public int getID_animal() {
        return ID_animal;
    }

    public void setID_animal(int ID_animal) {
        this.ID_animal = ID_animal;
    }

    public int getID_owner() {
        return ID_owner;
    }

    public void setID_owner(int ID_owner) {
        this.ID_owner = ID_owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public String getBreed() {
        return breed;
    }

    public String getColor() {
        return color;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
