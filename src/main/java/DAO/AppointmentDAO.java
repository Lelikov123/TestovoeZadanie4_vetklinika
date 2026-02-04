package DAO;

import Classes.Appointments;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentDAO {

    List<Appointments> findByOwnerId(int ownerId);
    List<Appointments> findByVetIdAndDate(int vetId, LocalDate date);
    List<LocalDateTime> getAvailableSlots(int vetId, LocalDate date);
    int save(Appointments appointment);
    void updateStatus(int appointmentId, String status);
}