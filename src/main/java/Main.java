import Services.MenuService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Запуск ветеринарной клиники...");
        MenuService menuService = new MenuService();
        menuService.showMainMenu();
    }
}