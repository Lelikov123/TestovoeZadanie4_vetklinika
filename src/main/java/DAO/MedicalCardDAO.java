package DAO;

import Classes.Medical_cards;

public interface MedicalCardDAO {
    Medical_cards findByAnimalId(int animalId);
    Medical_cards findById(int cardId);
    int save(Medical_cards card);
    void update(Medical_cards card);

}