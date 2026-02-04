package DAO;

import Classes.Veterinarians;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarianDAOImpl implements VeterinarianDAO {
    @Override
    public Veterinarians findById(int id) {
        String sql = "SELECT * FROM Veterinarians WHERE ID_vet = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapVeterinarian(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Veterinarians findByPhone(String phone) {
        String sql = "SELECT * FROM Veterinarians WHERE phone = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapVeterinarian(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Veterinarians> findAll() {
        List<Veterinarians> vets = new ArrayList<>();
        String sql = "SELECT * FROM Veterinarians";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                vets.add(mapVeterinarian(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vets;
    }


    private Veterinarians mapVeterinarian(ResultSet rs) throws SQLException {
        Veterinarians vet = new Veterinarians();
        vet.setID_vet(rs.getInt("ID_vet"));
        vet.setLast_name(rs.getString("last_name"));
        vet.setFirst_name(rs.getString("first_name"));
        vet.setPatronymic(rs.getString("patronymic"));
        vet.setSpecial(rs.getString("specialization"));
        vet.setPhone(rs.getString("phone"));
        return vet;
    }
}