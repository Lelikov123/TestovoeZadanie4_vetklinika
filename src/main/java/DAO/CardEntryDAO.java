package DAO;

import Classes.Card_entries;
import java.util.List;

public interface CardEntryDAO {
    List<Card_entries> findByCardId(int cardId);
    int save(Card_entries entry);

}