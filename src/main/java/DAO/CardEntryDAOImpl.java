package DAO;

import Classes.Card_entries;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardEntryDAOImpl implements CardEntryDAO {


    @Override
    public List<Card_entries> findByCardId(int cardId) {
        List<Card_entries> entries = new ArrayList<>();
        String sql = "SELECT * FROM Card_entries WHERE ID_card = ? ORDER BY entry_date DESC";
        try (Connection conn = new Database().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cardId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                entries.add(mapCardEntry(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    @Override
    public int save(Card_entries entry) {
        String sql = "INSERT INTO Card_entries (ID_card, ID_vet, entry_date, entry_type, diagnosis, treatment, vaccine) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, entry.getID_card());
            stmt.setInt(2, entry.getID_vet());
            stmt.setDate(3, Date.valueOf(entry.getEntry_date()));
            stmt.setString(4, entry.getEntry_type());
            stmt.setString(5, entry.getDiagnosis());
            stmt.setString(6, entry.getTreadment());
            stmt.setString(7, entry.getVaccine());

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

    private Card_entries mapCardEntry(ResultSet rs) throws SQLException {
        Card_entries entry = new Card_entries();
        entry.setID_entry(rs.getInt("ID_entry"));
        entry.setID_card(rs.getInt("ID_card"));
        entry.setID_vet(rs.getInt("ID_vet"));
        entry.setID_appointment(rs.getInt("ID_appointment"));
        entry.setEntry_date(rs.getDate("entry_date").toLocalDate());
        entry.setEntry_type(rs.getString("entry_type"));
        entry.setDiagnosis(rs.getString("diagnosis"));
        entry.setTreadment(rs.getString("treatment"));
        entry.setVaccine(rs.getString("vaccine"));
        Date nextDate = rs.getDate("next_date");
        if (nextDate != null) {
            entry.setNext_date(nextDate.toLocalDate());
        }
        return entry;
    }
}