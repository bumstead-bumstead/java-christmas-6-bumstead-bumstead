package christmas.model;

import christmas.dto.MenuDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static christmas.config.EventConfig.*;
import static christmas.model.FoodCategory.*;
import static java.awt.SystemColor.menu;

public class MenuSheet {
    private final Map<Menu, Integer> menuCount;

    private MenuSheet(Map<Menu, Integer> menuCount) {
        this.menuCount = menuCount;
    }

    public static MenuSheet fromMenuDtoList(List<MenuDto> menuDtos) {
        validate(menuDtos);

        Map<Menu, Integer> menuCount = parseMenuDtosToMap(menuDtos);
        return new MenuSheet(menuCount);
    }

    private static Map<Menu, Integer> parseMenuDtosToMap(List<MenuDto> menuDtos) {
        Map<Menu, Integer> menuCount = new HashMap<>();
        menuDtos.forEach(menuDto -> menuCount.put(menuDto.getMenu(), menuDto.getCount()));

        return menuCount;
    }

    private static void validateMenuCombination(List<MenuDto> menuDtos) {
        long numberOfNonBeverage = menuDtos
                .stream()
                .filter(menuDto -> !menuDto.getMenu().isKindOf(BEVERAGE))
                .count();

        if (numberOfNonBeverage < MINIMUM_NUMBER_OF_NON_BEVERAGE) {
            throw new IllegalArgumentException("음료가 아닌 메뉴를 최소 한 개 이상 주문해주세요.");
        }
    }

    private static void validate(List<MenuDto> menuDtos) {
        validateTotalNumber(menuDtos);
        validateDuplication(menuDtos);
        validateMenuCombination(menuDtos);
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

    private static void validateTotalNumber(List<MenuDto> menuDtos) {
        int totalMenuCont = menuDtos
                .stream()
                .map(MenuDto::getCount)
                .mapToInt(Integer::intValue)
                .sum();

        if (totalMenuCont > MAXIMUM_NUMBER_OF_MENU) {
            throw new IllegalArgumentException("메뉴는 최대 20개까지 주문할 수 있습니다.");
        }
    }

    private static void validateDuplication(List<MenuDto> menuDtos) {
        int distinctMenuCount = (int) menuDtos.stream()
                .map(MenuDto::getMenu)
                .distinct()
                .count();

        if (distinctMenuCount != menuDtos.size()) {
            throw new IllegalArgumentException("메뉴 이름이 중복되었습니다.");
        }
    }
}
