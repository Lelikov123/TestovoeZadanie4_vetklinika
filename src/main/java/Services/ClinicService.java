package Services;

import DAO.*;
import Classes.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ClinicService {
    private OwnerDAOImpl ownerDAO;
    private AnimalDAOImpl animalDAO;
    private VeterinarianDAOImpl veterinarianDAO;
    private MedicalCardDAOImpl medicalCardDAO;
    private CardEntryDAOImpl cardEntryDAO;
    private AppointmentDAOImpl appointmentDAO;

    public ClinicService() {
        ownerDAO = new OwnerDAOImpl();
        animalDAO = new AnimalDAOImpl();
        veterinarianDAO = new VeterinarianDAOImpl();
        medicalCardDAO = new MedicalCardDAOImpl();
        cardEntryDAO = new CardEntryDAOImpl();
        appointmentDAO = new AppointmentDAOImpl();
    }

    // === ДЛЯ ВЛАДЕЛЬЦА ===

    public Animals addAnimal(int ownerId, String name, String species, String breed,
                             LocalDate birthDate, String gender, String color) {
        Animals animal = new Animals();
        animal.setID_owner(ownerId);
        animal.setName(name);
        animal.setSpecies(species);
        animal.setBreed(breed != null ? breed : "");
        animal.setBirth_date(birthDate);
        animal.setGender(gender);
        animal.setColor(color != null ? color : "");

        int animalId = animalDAO.save(animal);
        if (animalId > 0) {
            animal.setID_animal(animalId);

            Medical_cards card = new Medical_cards();
            card.setID_animal(animalId);
            card.setDate(LocalDate.now());
            card.setChip_number("");
            card.setBlood_type("");
            card.setChronic_diseases("");
            card.setAllergies("");
            medicalCardDAO.save(card);

            return animal;
        }
        return null;
    }

    public List<Animals> getOwnerAnimals(int ownerId) {
        return animalDAO.findByOwnerId(ownerId);
    }

    public boolean deleteAnimal(int animalId) {
        return animalDAO.delete(animalId);
    }

    public Appointments bookAppointment(int animalId, int vetId, LocalDateTime dateTime, String reason) {
        Appointments appointment = new Appointments();
        appointment.setID_animal(animalId);
        appointment.setID_vet(vetId);
        appointment.setAppointments_date(dateTime.toLocalDate());
        appointment.setAppointments_time(dateTime);
        appointment.setReason(reason);
        appointment.setStatus("запланирован");

        int appointmentId = appointmentDAO.save(appointment);
        if (appointmentId > 0) {
            appointment.setID_appointments(appointmentId);
            return appointment;
        }
        return null;
    }

    public List<Veterinarians> getAllVets() {
        return veterinarianDAO.findAll();
    }

    public List<LocalDateTime> getAvailableSlots(int vetId, LocalDate date) {
        return appointmentDAO.getAvailableSlots(vetId, date);
    }

    public List<Appointments> getOwnerAppointments(int ownerId) {
        return appointmentDAO.findByOwnerId(ownerId);
    }

    // === ДЛЯ ВЕТЕРИНАРА ===

    public List<Appointments> getTodayAppointments(int vetId) {
        return appointmentDAO.findByVetIdAndDate(vetId, LocalDate.now());
    }

    public Medical_cards getMedicalCardById(int cardId) {
        return medicalCardDAO.findById(cardId);
    }

    public Medical_cards getAnimalMedicalCard(int animalId) {
        return medicalCardDAO.findByAnimalId(animalId);
    }

    public void completeAppointment(int appointmentId) {
        appointmentDAO.updateStatus(appointmentId, "завершен");
    }

    public Animals getAnimalById(int animalId) {
        return animalDAO.findById(animalId);
    }

    public void updateMedicalCard(Medical_cards card) {
        medicalCardDAO.update(card);
    }

    public Veterinarians getVeterinarianById(int vetId) {
        return veterinarianDAO.findById(vetId);
    }

    public List<Card_entries> getCardEntries(int cardId) {
        return cardEntryDAO.findByCardId(cardId);
    }

    public void addMedicalRecord(int cardId, int vetId, String entryType,
                                 String diagnosis, String treatment,
                                 String vaccine, LocalDate nextDate) {
        Card_entries entry = new Card_entries();
        entry.setID_card(cardId);
        entry.setID_vet(vetId);
        entry.setEntry_date(LocalDate.now());
        entry.setEntry_type(entryType);
        entry.setDiagnosis(diagnosis);
        entry.setTreadment(treatment);
        entry.setVaccine(vaccine != null ? vaccine : "");
        entry.setNext_date(nextDate);

        cardEntryDAO.save(entry);
    }

}