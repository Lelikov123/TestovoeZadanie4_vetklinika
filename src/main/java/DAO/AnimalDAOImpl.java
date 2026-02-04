package DAO;

import Classes.Animals;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAOImpl implements AnimalDAO {
    @Override
    public Animals findById(int id) {
        String sql = "SELECT * FROM Animals WHERE ID_animal = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapAnimal(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Animals> findByOwnerId(int ownerId) {
        List<Animals> animals = new ArrayList<>();
        String sql = "SELECT * FROM Animals WHERE ID_owner = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                animals.add(mapAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    @Override
    public int save(Animals animal) {
        String sql = "INSERT INTO Animals (ID_owner, name, species, breed, birth_date, gender, color) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, animal.getID_owner());
            stmt.setString(2, animal.getName());
            stmt.setString(3, animal.getSpecies());
            stmt.setString(4, animal.getBreed());
            stmt.setDate(5, Date.valueOf(animal.getBirth_date()));
            stmt.setString(6, animal.getGender());
            stmt.setString(7, animal.getColor());
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
    public boolean delete(int id) {
        String sql = "DELETE FROM Animals WHERE ID_animal = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private Animals mapAnimal(ResultSet rs) throws SQLException {
        Animals animal = new Animals();
        animal.setID_animal(rs.getInt("ID_animal"));
        animal.setID_owner(rs.getInt("ID_owner"));
        animal.setName(rs.getString("name"));
        animal.setSpecies(rs.getString("species"));
        animal.setBreed(rs.getString("breed"));
        animal.setBirth_date(rs.getDate("birth_date").toLocalDate());
        animal.setGender(rs.getString("gender"));
        animal.setColor(rs.getString("color"));
        return animal;
    }
}