package Classes;

public class Veterinarians {

    private int ID_vet;
    private String last_name;
    private String first_name;
    private String patronymic;
    private String special;
    private String phone;

    public Veterinarians(){}

    public int getID_vet() {
        return ID_vet;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpecial() {
        return special;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setID_vet(int ID_vet) {
        this.ID_vet = ID_vet;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

}
