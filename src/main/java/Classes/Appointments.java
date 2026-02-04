package Classes;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointments {
    private int ID_appointments;
    private int ID_animal;
    private int ID_vet;
    private LocalDate appointments_date;
    private LocalDateTime appointments_time;
    private String reason;
    private String status;

    public Appointments(){}

    public void setID_vet(int ID_vet) {
        this.ID_vet = ID_vet;
    }

    public int getID_vet() {
        return ID_vet;
    }

    public void setID_animal(int ID_animal) {
        this.ID_animal = ID_animal;
    }

    public int getID_animal() {
        return ID_animal;
    }

    public int getID_appointments() {
        return ID_appointments;
    }

    public LocalDate getAppointments_date() {
        return appointments_date;
    }

    public LocalDateTime getAppointments_time() {
        return appointments_time;
    }

    public void setID_appointments(int ID_appointments) {
        this.ID_appointments = ID_appointments;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public void setAppointments_date(LocalDate appointments_date) {
        this.appointments_date = appointments_date;
    }

    public void setAppointments_time(LocalDateTime appointments_time) {
        this.appointments_time = appointments_time;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}