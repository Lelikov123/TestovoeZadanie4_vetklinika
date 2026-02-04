package Classes;

import java.time.LocalDate;

public class Medical_cards {

    private int ID_card;
    private int ID_animal;
    private LocalDate date;
    private String chip_number;
    private String blood_type;
    private String chronic_diseases;
    private String allergies;

    public Medical_cards(){}
    public int getID_animal() {
        return ID_animal;
    }

    public void setID_animal(int ID_animal) {
        this.ID_animal = ID_animal;
    }

    public int getID_card() {
        return ID_card;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public String getChip_number() {
        return chip_number;
    }

    public String getChronic_diseases() {
        return chronic_diseases;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void setID_card(int ID_card) {
        this.ID_card = ID_card;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public void setChip_number(String chip_number) {
        this.chip_number = chip_number;
    }

    public void setChronic_diseases(String chronic_diseases) {
        this.chronic_diseases = chronic_diseases;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
