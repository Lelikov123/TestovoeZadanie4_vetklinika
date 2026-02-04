package Classes;

import java.time.LocalDate;

public class Card_entries {
    private int ID_entry;
    private int ID_card;
    private int ID_vet;
    private Integer ID_appointment;
    private LocalDate entry_date;
    private String entry_type;
    private String diagnosis;
    private String treadment;
    private String vaccine;
    private LocalDate next_date;

    public Card_entries(){}

    public void setID_card(int ID_card) {
        this.ID_card = ID_card;
    }

    public int getID_card() {
        return ID_card;
    }

    public int getID_vet() {
        return ID_vet;
    }

    public void setID_vet(int ID_vet) {
        this.ID_vet = ID_vet;
    }

    public int getID_entry() {
        return ID_entry;
    }

    public LocalDate getEntry_date() {
        return entry_date;
    }

    public Integer getID_appointment() {
        return ID_appointment;
    }

    public void setID_appointment(Integer ID_appointment) {
        this.ID_appointment = ID_appointment;
    }

    public LocalDate getNext_date() {
        return next_date;
    }

    public String getEntry_type() {
        return entry_type;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreadment() {
        return treadment;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setID_entry(int ID_entry) {
        this.ID_entry = ID_entry;
    }

    public void setEntry_date(LocalDate entry_date) {
        this.entry_date = entry_date;
    }

    public void setEntry_type(String entry_type) {
        this.entry_type = entry_type;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setNext_date(LocalDate next_date) {
        this.next_date = next_date;
    }

    public void setTreadment(String treadment) {
        this.treadment = treadment;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }
}
