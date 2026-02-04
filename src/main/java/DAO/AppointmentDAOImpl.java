package DAO;

import Classes.Appointments;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public List<Appointments> findByOwnerId(int ownerId) {
        List<Appointments> appointments = new ArrayList<>();
        String sql = "SELECT a.* FROM Appointments a JOIN Animals an ON a.ID_animal = an.ID_animal WHERE an.ID_owner = ? ORDER BY a.appointment_date DESC, a.appointment_time DESC";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointments.add(mapAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<Appointments> findByVetIdAndDate(int vetId, LocalDate date) {
        List<Appointments> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE ID_vet = ? AND appointment_date = ? ORDER BY appointment_time";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vetId);
            stmt.setDate(2, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointments.add(mapAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<LocalDateTime> getAvailableSlots(int vetId, LocalDate date) {
        List<LocalDateTime> availableSlots = new ArrayList<>();
        List<Appointments> bookedAppointments = findByVetIdAndDate(vetId, date);

        List<LocalDateTime> allSlots = generateTimeSlots(date);

        for (LocalDateTime slot : allSlots) {
            boolean isBooked = false;
            for (Appointments appointment : bookedAppointments) {
                if (appointment.getAppointments_time().equals(slot)) {
                    isBooked = true;
                    break;
                }
            }
            if (!isBooked) {
                availableSlots.add(slot);
            }
        }

        return availableSlots;
    }

    @Override
    public int save(Appointments appointment) {
        String sql = "INSERT INTO Appointments (ID_animal, ID_vet, appointment_date, appointment_time, reason, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, appointment.getID_animal());
            stmt.setInt(2, appointment.getID_vet());
            stmt.setDate(3, Date.valueOf(appointment.getAppointments_date()));
            stmt.setTimestamp(4, Timestamp.valueOf(appointment.getAppointments_time()));
            stmt.setString(5, appointment.getReason());
            stmt.setString(6, appointment.getStatus());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void updateStatus(int appointmentId, String status) {
        String sql = "UPDATE Appointments SET status = ? WHERE ID_appointment = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, appointmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Appointments mapAppointment(ResultSet rs) throws SQLException {
        Appointments appointment = new Appointments();
        appointment.setID_appointments(rs.getInt("ID_appointment"));
        appointment.setID_animal(rs.getInt("ID_animal"));
        appointment.setID_vet(rs.getInt("ID_vet"));
        appointment.setAppointments_date(rs.getDate("appointment_date").toLocalDate());
        appointment.setAppointments_time(rs.getTimestamp("appointment_time").toLocalDateTime());
        appointment.setReason(rs.getString("reason"));
        appointment.setStatus(rs.getString("status"));
        return appointment;
    }

    private List<LocalDateTime> generateTimeSlots(LocalDate date) {
        List<LocalDateTime> slots = new ArrayList<>();
        LocalDateTime startTime = date.atTime(9, 0);
        LocalDateTime endTime = date.atTime(18, 0);

        while (startTime.isBefore(endTime)) {
            slots.add(startTime);
            startTime = startTime.plusMinutes(30);
        }

        return slots;
    }
}