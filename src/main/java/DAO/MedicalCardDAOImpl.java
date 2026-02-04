package DAO;

import Classes.Medical_cards;
import java.sql.*;

public class MedicalCardDAOImpl implements MedicalCardDAO {
    @Override
    public Medical_cards findByAnimalId(int animalId) {
        String sql = "SELECT * FROM Medical_cards WHERE ID_animal = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, animalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapMedicalCard(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Medical_cards findById(int cardId) {
        String sql = "SELECT * FROM Medical_cards WHERE ID_card = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cardId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapMedicalCard(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int save(Medical_cards card) {
        String sql = "INSERT INTO Medical_cards (ID_animal, created_date, chip_number, blood_type, chronic_diseases, allergies) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, card.getID_animal());
            stmt.setDate(2, Date.valueOf(card.getDate()));
            stmt.setString(3, card.getChip_number());
            stmt.setString(4, card.getBlood_type());
            stmt.setString(5, card.getChronic_diseases());
            stmt.setString(6, card.getAllergies());
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
    public void update(Medical_cards card) {
        String sql = "UPDATE Medical_cards SET chip_number = ?, blood_type = ?, chronic_diseases = ?, allergies = ? WHERE ID_card = ?";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, card.getChip_number());
            stmt.setString(2, card.getBlood_type());
            stmt.setString(3, card.getChronic_diseases());
            stmt.setString(4, card.getAllergies());
            stmt.setInt(5, card.getID_card());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Medical_cards mapMedicalCard(ResultSet rs) throws SQLException {
        Medical_cards card = new Medical_cards();
        card.setID_card(rs.getInt("ID_card"));
        card.setID_animal(rs.getInt("ID_animal"));
        card.setDate(rs.getDate("created_date").toLocalDate());
        card.setChip_number(rs.getString("chip_number"));
        card.setBlood_type(rs.getString("blood_type"));
        card.setChronic_diseases(rs.getString("chronic_diseases"));
        card.setAllergies(rs.getString("allergies"));
        return card;
    }
}