package DAO;

import Classes.Owners;
import Classes.Animals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAOImpl implements OwnerDAO {

    @Override
    public Owners findByPhone(String phone) {
        String sql = "SELECT * FROM Owners WHERE phone = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapOwner(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int save(Owners owner) {
        String sql = "INSERT INTO Owners (last_name, first_name, patronymic, phone, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, owner.getLast_name());
            stmt.setString(2, owner.getFirst_name());
            stmt.setString(3, owner.getPatronymic());
            stmt.setString(4, owner.getPhone());
            stmt.setString(5, owner.getAddress());
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


    private Owners mapOwner(ResultSet rs) throws SQLException {
        Owners owner = new Owners();
        owner.setID_owner(rs.getInt("ID_owner"));
        owner.setLast_name(rs.getString("last_name"));
        owner.setFirst_name(rs.getString("first_name"));
        owner.setPatronymic(rs.getString("patronymic"));
        owner.setPhone(rs.getString("phone"));
        owner.setAddress(rs.getString("address"));
        return owner;
    }
}