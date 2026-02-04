package Services;

import Classes.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    private Scanner scanner;
    private AuthService authService;
    private ClinicService clinicService;
    private Owners currentOwner;
    private Veterinarians currentVet;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public MenuService() {
        scanner = new Scanner(System.in);
        authService = new AuthService();
        clinicService = new ClinicService();
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== ВЕТЕРИНАРНАЯ КЛИНИКА ===");
            System.out.println("1. Войти как владелец");
            System.out.println("2. Войти как ветеринар");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    ownerLogin();
                    break;
                case "2":
                    vetLogin();
                    break;
                case "0":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private void ownerLogin() {
        System.out.println("\n=== ВХОД ВЛАДЕЛЬЦА ===");
        System.out.print("Введите телефон: ");
        String phone = scanner.nextLine();

        currentOwner = authService.loginOwner(phone);

        if (currentOwner != null) {
            System.out.println("Добро пожаловать, " + currentOwner.getLast_name() + " " + currentOwner.getFirst_name() + " " + currentOwner.getPatronymic() + "!");
            showOwnerMenu();
        } else {
            System.out.println("Владелец не найден.");
            System.out.print("Зарегистрироваться? (да/нет): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("да")) {
                registerOwner();
            }
        }
    }

    private void registerOwner() {
        System.out.println("\n=== РЕГИСТРАЦИЯ ===");
        System.out.print("Фамилия: ");
        String lastName = scanner.nextLine();
        System.out.print("Имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Отчество: ");
        String patronymic = scanner.nextLine();
        System.out.print("Телефон: ");
        String phone = scanner.nextLine();

        currentOwner = authService.registerOwner(lastName, firstName, patronymic, phone);
        if (currentOwner != null) {
            System.out.println("Регистрация успешна!");
            showOwnerMenu();
        } else {
            System.out.println("Ошибка регистрации!");
        }
    }

    private void showOwnerMenu() {
        while (true) {
            System.out.println("\n=== МЕНЮ ВЛАДЕЛЬЦА ===");
            System.out.println("1. Мои животные");
            System.out.println("2. Добавить животное");
            System.out.println("3. Записать к врачу");
            System.out.println("4. Мои записи");
            System.out.println("5. Удалить животное");
            System.out.println("0. Выйти");
            System.out.print("Выбор: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showMyAnimals();
                    break;
                case "2":
                    addAnimal();
                    break;
                case "3":
                    bookAppointment();
                    break;
                case "4":
                    showMyAppointments();
                    break;
                case "5":
                    deleteAnimal();
                    break;
                case "0":
                    currentOwner = null;
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private void showMyAnimals() {
        System.out.println("\n=== МОИ ЖИВОТНЫЕ ===");
        List<Animals> animals = clinicService.getOwnerAnimals(currentOwner.getId());

        if (animals.isEmpty()) {
            System.out.println("У вас нет животных.");
            return;
        }

        for (int i = 0; i < animals.size(); i++) {
            Animals animal = animals.get(i);
            // Получаем карту для животного
            Medical_cards card = clinicService.getAnimalMedicalCard(animal.getID_animal());
            String cardNumber = (card != null) ? "Карта №" + card.getID_card() : "Карта не создана";

            System.out.println((i + 1) + ". " + animal.getName() +
                    " (" + animal.getSpecies() + ") - " + cardNumber);
        }

        System.out.print("\nНажмите Enter для продолжения...");
        scanner.nextLine();
    }

    private void addAnimal() {
        System.out.println("\n=== ДОБАВЛЕНИЕ ЖИВОТНОГО ===");

        System.out.print("Кличка: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Ошибка: кличка не может быть пустой!");
            return;
        }

        System.out.print("Вид (кошка, собака и т.д.): ");
        String species = scanner.nextLine();

        System.out.print("Порода (Enter чтобы пропустить): ");
        String breed = scanner.nextLine();

        System.out.print("Дата рождения (дд.мм.гггг или Enter для сегодня): ");
        String birthDateStr = scanner.nextLine();
        LocalDate birthDate;
        if (birthDateStr.trim().isEmpty()) {
            birthDate = LocalDate.now();
        } else {
            try {
                birthDate = LocalDate.parse(birthDateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты! Используется сегодняшняя дата.");
                birthDate = LocalDate.now();
            }
        }

        String gender;
        while (true) {
            System.out.print("Пол (М/Ж): ");
            gender = scanner.nextLine().toUpperCase();
            if (gender.equals("М") || gender.equals("Ж")) {
                break;
            }
            System.out.println("Ошибка: введите 'М' или 'Ж'!");
        }

        System.out.print("Окрас (Enter чтобы пропустить): ");
        String color = scanner.nextLine();

        Animals animal = clinicService.addAnimal(currentOwner.getId(), name, species,
                breed, birthDate, gender, color);
        if (animal != null) {
            System.out.println("Животное успешно добавлено!");
            Medical_cards card = clinicService.getAnimalMedicalCard(animal.getID_animal());
            if (card != null) {
                System.out.println("Создана медицинская карта №" + card.getID_card());
            }
        } else {
            System.out.println("Ошибка при добавлении животного!");
        }
    }

    private void bookAppointment() {
        System.out.println("\n=== ЗАПИСЬ К ВРАЧУ ===");

        // 1. Выбор животного
        List<Animals> animals = clinicService.getOwnerAnimals(currentOwner.getId());
        if (animals.isEmpty()) {
            System.out.println("У вас нет животных для записи!");
            return;
        }

        System.out.println("Выберите животное:");
        for (int i = 0; i < animals.size(); i++) {
            System.out.println((i + 1) + ". " + animals.get(i).getName());
        }
        System.out.print("Ваш выбор: ");
        int animalChoice = Integer.parseInt(scanner.nextLine()) - 1;
        Animals selectedAnimal = animals.get(animalChoice);

        // 2. Выбор врача
        System.out.println("\nДоступные врачи:");
        List<Veterinarians> vets = clinicService.getAllVets();
        for (int i = 0; i < vets.size(); i++) {
            Veterinarians vet = vets.get(i);
            System.out.println((i + 1) + ". " + vet.getLast_name() + " " + vet.getFirst_name() + " (" + vet.getSpecial() + ")");
        }
        System.out.print("Выберите врача: ");
        int vetChoice = Integer.parseInt(scanner.nextLine()) - 1;
        Veterinarians selectedVet = vets.get(vetChoice);

        // 3. Выбор даты
        System.out.print("\nВведите дату (дд.мм.гггг): ");
        String dateStr = scanner.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты!");
            return;
        }

        // 4. Показать свободные слоты
        System.out.println("\nСвободные время на " + date.format(dateFormatter) + ":");
        List<LocalDateTime> slots = clinicService.getAvailableSlots(selectedVet.getID_vet(), date);

        if (slots.isEmpty()) {
            System.out.println("Нет свободных слотов на эту дату.");
            return;
        }

        for (int i = 0; i < slots.size(); i++) {
            System.out.println((i + 1) + ". " + slots.get(i).format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        System.out.print("Выберите время: ");
        int timeChoice = Integer.parseInt(scanner.nextLine()) - 1;
        LocalDateTime selectedDateTime = slots.get(timeChoice);

        // 5. Причина визита
        System.out.print("Причина визита: ");
        String reason = scanner.nextLine();

        // 6. Создание записи
        Appointments appointment = clinicService.bookAppointment(
                selectedAnimal.getID_animal(),
                selectedVet.getID_vet(),
                selectedDateTime,
                reason
        );

        if (appointment != null) {
            System.out.println("\nЗапись успешно создана!");
            System.out.println("Номер записи: " + appointment.getID_appointments());
            System.out.println("Дата: " + appointment.getAppointments_date().format(dateFormatter));
            System.out.println("Время: " + appointment.getAppointments_time().format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            System.out.println("Ошибка при создании записи!");
        }
    }

    private void showMyAppointments() {
        System.out.println("\n=== МОИ ЗАПИСИ ===");
        List<Appointments> appointments = clinicService.getOwnerAppointments(currentOwner.getId());

        if (appointments.isEmpty()) {
            System.out.println("У вас нет записей.");
            return;
        }

        for (Appointments app : appointments) {
            System.out.println("ID: " + app.getID_appointments());
            System.out.println("Дата: " + app.getAppointments_date().format(dateFormatter));
            System.out.println("Время: " + app.getAppointments_time().format(DateTimeFormatter.ofPattern("HH:mm")));
            System.out.println("Статус: " + app.getStatus());
            System.out.println("Причина: " + app.getReason());
            System.out.println("---");
        }

        System.out.print("Нажмите Enter для продолжения...");
        scanner.nextLine();
    }

    private void deleteAnimal() {
        System.out.println("\n=== УДАЛЕНИЕ ЖИВОТНОГО ===");
        List<Animals> animals = clinicService.getOwnerAnimals(currentOwner.getId());

        if (animals.isEmpty()) {
            System.out.println("У вас нет животных.");
            return;
        }

        System.out.println("Выберите животное для удаления:");
        for (int i = 0; i < animals.size(); i++) {
            System.out.println((i + 1) + ". " + animals.get(i).getName());
        }
        System.out.print("Ваш выбор: ");
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        Animals animalToDelete = animals.get(choice);
        System.out.print("Удалить " + animalToDelete.getName() + "? (да/нет): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("да")) {
            boolean success = clinicService.deleteAnimal(animalToDelete.getID_animal());
            if (success) {
                System.out.println("Животное удалено!");
            } else {
                System.out.println("Ошибка при удалении!");
            }
        }
    }

    private void vetLogin() {
        System.out.println("\n=== ВХОД ВЕТЕРИНАРА ===");
        System.out.print("Введите телефон: ");
        String phone = scanner.nextLine();

        currentVet = authService.loginVeterinarian(phone);

        if (currentVet != null) {
            System.out.println("Добро пожаловать, доктор " + currentVet.getLast_name() + "!");
            showVeterinarianMenu();
        } else {
            System.out.println("Ветеринар не найден!");
        }
    }

    private void showVeterinarianMenu() {
        while (true) {
            System.out.println("\n=== МЕНЮ ВЕТЕРИНАРА ===");
            System.out.println("1. Приемы на сегодня");
            System.out.println("2. Завершить прием");
            System.out.println("3. Найти медкарту");
            System.out.println("4. Добавить запись в карту");
            System.out.println("5. История болезни (по карте)");
            System.out.println("6. Редактировать медкарту");
            System.out.println("0. Выйти");
            System.out.print("Выбор: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showTodayAppointments();
                    break;
                case "2":
                    completeAppointment();
                    break;
                case "3":
                    findMedicalCard();
                    break;
                case "4":
                    addMedicalRecord();
                    break;
                case "5":
                    showMedicalHistory();
                    break;
                case "6":
                    updateMedicalCardInfo();
                    break;
                case "0":
                    currentVet = null;
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private void showTodayAppointments() {
        System.out.println("\n=== ПРИЕМЫ НА СЕГОДНЯ ===");
        List<Appointments> appointments = clinicService.getTodayAppointments(currentVet.getID_vet());

        if (appointments.isEmpty()) {
            System.out.println("На сегодня приемов нет.");
            return;
        }

        for (Appointments app : appointments) {
            System.out.println("ID: " + app.getID_appointments());
            System.out.println("Время: " + app.getAppointments_time().format(DateTimeFormatter.ofPattern("HH:mm")));
            System.out.println("Причина: " + app.getReason());
            System.out.println("Статус: " + app.getStatus());
            System.out.println("---");
        }
    }

    private void completeAppointment() {
        System.out.println("\n=== ЗАВЕРШЕНИЕ ПРИЕМА ===");
        System.out.print("Введите ID приема: ");
        int appointmentId = Integer.parseInt(scanner.nextLine());

        clinicService.completeAppointment(appointmentId);
        System.out.println("Прием завершен!");
    }

    private void findMedicalCard() {
        System.out.println("\n=== ПОИСК МЕДКАРТЫ ===");
        System.out.print("Введите номер медкарты: ");
        int cardId = Integer.parseInt(scanner.nextLine());

        Medical_cards card = clinicService.getMedicalCardById(cardId);
        if (card == null) {
            System.out.println("Медкарта не найдена!");
            return;
        }

        // Находим животное по ID животного из карты
        Animals animal = clinicService.getAnimalById(card.getID_animal());
        if (animal != null) {
            System.out.println("\n=== ИНФОРМАЦИЯ О ЖИВОТНОМ ===");
            System.out.println("Кличка: " + animal.getName());
            System.out.println("Вид: " + animal.getSpecies());
            System.out.println("Порода: " + (animal.getBreed() != null && !animal.getBreed().isEmpty() ? animal.getBreed() : "не указана"));
            if (animal.getBirth_date() != null) {
                System.out.println("Дата рождения: " + animal.getBirth_date().format(dateFormatter));
            }
            System.out.println("Пол: " + (animal.getGender() != null ? animal.getGender() : "не указан"));
            System.out.println("Окрас: " + (animal.getColor() != null && !animal.getColor().isEmpty() ? animal.getColor() : "не указан"));
        }

        System.out.println("\n=== МЕДИЦИНСКАЯ КАРТА №" + card.getID_card() + " ===");
        System.out.println("Дата создания: " + card.getDate().format(dateFormatter));
        System.out.println("Чип: " + (card.getChip_number() != null && !card.getChip_number().isEmpty() ? card.getChip_number() : "не установлен"));
        System.out.println("Группа крови: " + (card.getBlood_type() != null && !card.getBlood_type().isEmpty() ? card.getBlood_type() : "не указана"));
        System.out.println("Хронические заболевания: " + (card.getChronic_diseases() != null && !card.getChronic_diseases().isEmpty() ? card.getChronic_diseases() : "нет"));
        System.out.println("Аллергии: " + (card.getAllergies() != null && !card.getAllergies().isEmpty() ? card.getAllergies() : "нет"));

        System.out.print("\nПоказать последние записи? (да/нет): ");
        String showHistory = scanner.nextLine();
        if (showHistory.equalsIgnoreCase("да")) {
            showCardHistory(cardId);
        }
    }

    private void addMedicalRecord() {
        System.out.println("\n=== ДОБАВЛЕНИЕ ЗАПИСИ В КАРТУ ===");
        System.out.print("Введите номер медкарты: ");
        int cardId = Integer.parseInt(scanner.nextLine());

        Medical_cards card = clinicService.getMedicalCardById(cardId);
        if (card == null) {
            System.out.println("Медкарта не найдена!");
            return;
        }

        // Показываем информацию о животном
        Animals animal = clinicService.getAnimalById(card.getID_animal());
        if (animal != null) {
            System.out.println("\nЖивотное: " + animal.getName() + " (" + animal.getSpecies() + ")");
        }

        System.out.print("Тип записи (осмотр/вакцинация/операция/анализы): ");
        String entryType = scanner.nextLine();

        System.out.print("Диагноз: ");
        String diagnosis = scanner.nextLine();

        System.out.print("Лечение/назначения: ");
        String treatment = scanner.nextLine();

        System.out.print("Вакцина (если применимо, Enter чтобы пропустить): ");
        String vaccine = scanner.nextLine();

        System.out.print("Дата следующего визита (дд.мм.гггг или Enter чтобы пропустить): ");
        String nextDateStr = scanner.nextLine();
        LocalDate nextDate = null;
        if (!nextDateStr.trim().isEmpty()) {
            try {
                nextDate = LocalDate.parse(nextDateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты! Пропускаем...");
            }
        }

        clinicService.addMedicalRecord(cardId, currentVet.getID_vet(), entryType,
                diagnosis, treatment, vaccine, nextDate);
        System.out.println("Запись добавлена в карту №" + cardId);
    }

    private void showMedicalHistory() {
        System.out.println("\n=== ИСТОРИЯ БОЛЕЗНИ ===");
        System.out.print("Введите номер медкарты: ");
        int cardId = Integer.parseInt(scanner.nextLine());

        Medical_cards card = clinicService.getMedicalCardById(cardId);
        if (card == null) {
            System.out.println("Медкарта не найдена!");
            return;
        }

        // Показываем информацию о животном
        Animals animal = clinicService.getAnimalById(card.getID_animal());
        if (animal != null) {
            System.out.println("Животное: " + animal.getName() + " (" + animal.getSpecies() + ")");
        }

        showCardHistory(cardId);
    }

    private void showCardHistory(int cardId) {
        List<Card_entries> history = clinicService.getCardEntries(cardId);

        if (history.isEmpty()) {
            System.out.println("Записей в истории нет.");
            return;
        }

        System.out.println("\n=== ИСТОРИЯ ЗАПИСЕЙ КАРТЫ №" + cardId + " ===");
        for (Card_entries entry : history) {
            System.out.println("\nДата: " + entry.getEntry_date().format(dateFormatter));
            System.out.println("Тип: " + entry.getEntry_type());

            Veterinarians vet = clinicService.getVeterinarianById(entry.getID_vet());
            if (vet != null) {
                System.out.println("Врач: " + vet.getLast_name() + " " + vet.getFirst_name().charAt(0) + ".");
            }

            System.out.println("Диагноз: " + entry.getDiagnosis());
            System.out.println("Лечение: " + entry.getTreadment());

            if (entry.getVaccine() != null && !entry.getVaccine().isEmpty()) {
                System.out.println("Вакцина: " + entry.getVaccine());
            }

            if (entry.getNext_date() != null) {
                System.out.println("Следующий визит: " + entry.getNext_date().format(dateFormatter));
            }
            System.out.println("---");
        }

        System.out.print("\nНажмите Enter для продолжения...");
        scanner.nextLine();
    }

    private void updateMedicalCardInfo() {
        System.out.println("\n=== РЕДАКТИРОВАНИЕ МЕДКАРТЫ ===");
        System.out.print("Введите номер медкарты: ");
        int cardId = Integer.parseInt(scanner.nextLine());

        Medical_cards card = clinicService.getMedicalCardById(cardId);
        if (card == null) {
            System.out.println("Медкарта не найдена!");
            return;
        }

        Animals animal = clinicService.getAnimalById(card.getID_animal());
        if (animal != null) {
            System.out.println("Животное: " + animal.getName());
        }

        System.out.println("\nТекущие данные карты:");
        System.out.println("Чип: " + (card.getChip_number() != null && !card.getChip_number().isEmpty() ? card.getChip_number() : "не установлен"));
        System.out.println("Группа крови: " + (card.getBlood_type() != null && !card.getBlood_type().isEmpty() ? card.getBlood_type() : "не указана"));
        System.out.println("Хронические заболевания: " + (card.getChronic_diseases() != null && !card.getChronic_diseases().isEmpty() ? card.getChronic_diseases() : "нет"));
        System.out.println("Аллергии: " + (card.getAllergies() != null && !card.getAllergies().isEmpty() ? card.getAllergies() : "нет"));

        System.out.println("\nВведите новые данные (Enter чтобы оставить без изменений):");

        System.out.print("Номер чипа: ");
        String chip = scanner.nextLine();
        if (!chip.trim().isEmpty()) {
            card.setChip_number(chip);
        }

        System.out.print("Группа крови: ");
        String bloodType = scanner.nextLine();
        if (!bloodType.trim().isEmpty()) {
            card.setBlood_type(bloodType);
        }

        System.out.print("Хронические заболевания: ");
        String diseases = scanner.nextLine();
        if (!diseases.trim().isEmpty()) {
            card.setChronic_diseases(diseases);
        }

        System.out.print("Аллергии: ");
        String allergies = scanner.nextLine();
        if (!allergies.trim().isEmpty()) {
            card.setAllergies(allergies);
        }

        clinicService.updateMedicalCard(card);
        System.out.println("Карта обновлена!");
    }
}