package Classes;

public class Owners {
    private int ID_owner;
    private String first_name;
    private String last_name;
    private String patronymic;
    private String phone;
    private String address;

    public Owners() {};

    public int getId() { return ID_owner;}
    public void setID_owner(int ID_owner){
        this.ID_owner = ID_owner;
    }
    public String getFirst_name(){ return first_name;}
    public void setFirst_name(String first_name){ this.first_name = first_name;}

    public String getLast_name(){return last_name;}
    public void setLast_name(String last_name){ this.last_name = last_name;}

    public String getPatronymic(){return patronymic;}
    public void setPatronymic(String patronymic) {this.patronymic = patronymic;}

    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone = phone;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}
}