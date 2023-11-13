package christmas.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static christmas.config.EventConfig.*;
import static christmas.model.FoodCategory.*;

public class MenuSheet {
    private final Map<Menu, Integer> menuCount;

    private MenuSheet(Map<Menu, Integer> menuCount) {
        this.menuCount = menuCount;
    }

    public static MenuSheet fromConsoleInputFormat(List<String> menuEntries) {
        Map<Menu, Integer> menuCount = parseMenuEntriesToMap(menuEntries);
        validateMenuCombination(menuCount);
        validateTotalNumber(menuCount);
        return new MenuSheet(menuCount);
    }

    public int getNumberOfMenu(Menu menu) {
        return menuCount.getOrDefault(menu, 0);
    }

    public int getNumberOfMenuCategory(FoodCategory foodCategory) {
        return menuCount.keySet()
                .stream()
                .filter(menu -> menu.isKindOf(foodCategory))
                .map(menuCount::get)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int calculateTotalPrice() {
        return menuCount.keySet()
                .stream()
                .map(menu -> menuCount.get(menu) * menu.getPrice())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static Map<Menu, Integer> parseMenuEntriesToMap(List<String> menuEntries) {
        Map<Menu, Integer> menuCount = new HashMap<>();
        menuEntries
                .forEach(menuEntry -> {
                    String[] parts = menuEntry.split(MENU_COUNT_SEPARATOR);
                    Menu menu = Menu.of(parts[0]);
                    int count = Integer.parseInt(parts[1]);
                    validateDuplication(menuCount, menu);
                    menuCount.put(menu, count);
                });
        return menuCount;
    }

    private static void validateTotalNumber(Map<Menu, Integer> menuCount) {
        int totalMenuCont = menuCount.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (totalMenuCont > MAXIMUM_NUMBER_OF_MENU) {
            throw new IllegalArgumentException("메뉴는 최대 20개까지 주문할 수 있습니다.");
        }
    }

    private static void validateMenuCombination(Map<Menu, Integer> menuCount) {
        long numberOfNonBeverage = menuCount.keySet()
                .stream()
                .filter(menu -> !menu.isKindOf(BEVERAGE))
                .count();

        if (numberOfNonBeverage < MINIMUM_NUMBER_OF_NON_BEVERAGE) {
            throw new IllegalArgumentException("음료가 아닌 메뉴를 최소 한 개 이상 주문해주세요.");
        }
    }

    private static void validateDuplication(Map<Menu, Integer> menuCount, Menu menu) {
        if (menuCount.containsKey(menu)) {
            throw new IllegalArgumentException("메뉴 이름이 중복되었습니다.");
        }
    }
}
