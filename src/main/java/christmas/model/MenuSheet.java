package christmas.model;

import christmas.dto.MenuDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static christmas.config.ErrorMessage.*;
import static christmas.config.EventConfig.*;
import static christmas.model.FoodCategory.*;

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

    public List<MenuDto> toMenuDtoList() {
        return menuCount.entrySet()
                .stream()
                .map(MenuDto::fromMenuEntry)
                .toList();
    }

    public int getNumberOfMenu(FoodCategory foodCategory) {
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

    private static Map<Menu, Integer> parseMenuDtosToMap(List<MenuDto> menuDtos) {
        Map<Menu, Integer> menuCount = new HashMap<>();
        menuDtos.forEach(menuDto -> {
            Menu menu = Menu.of(menuDto.getMenuName());
            menuCount.put(menu, menuDto.getCount());
        });

        return menuCount;
    }

    private static void validate(List<MenuDto> menuDtos) {
        validateTotalNumber(menuDtos);
        validateDuplication(menuDtos);
        validateMenuCombination(menuDtos);
    }

    private static void validateMenuCombination(List<MenuDto> menuDtos) {
        long numberOfNonBeverage = menuDtos
                .stream()
                .filter(MenuSheet::isNotABeverage)
                .count();

        if (numberOfNonBeverage < MINIMUM_NUMBER_OF_NON_BEVERAGE) {
            throw new IllegalArgumentException(MENU_COMBINATION_ERROR_MESSAGE);
        }
    }

    private static void validateTotalNumber(List<MenuDto> menuDtos) {
        int totalMenuCount = menuDtos
                .stream()
                .map(MenuDto::getCount)
                .mapToInt(Integer::intValue)
                .sum();

        if (totalMenuCount > MAXIMUM_NUMBER_OF_MENU) {
            throw new IllegalArgumentException(MENU_NUMBER_ERROR_MESSAGE);
        }
    }

    private static void validateDuplication(List<MenuDto> menuDtos) {
        int distinctMenuCount = (int) menuDtos.stream()
                .map(MenuDto::getMenuName)
                .distinct()
                .count();

        if (distinctMenuCount != menuDtos.size()) {
            throw new IllegalArgumentException(MENU_INPUT_ERROR_MESSAGE);
        }
    }

    private static boolean isNotABeverage(MenuDto menuDto) {
        Menu menu = Menu.of(menuDto.getMenuName());
        return !menu.isKindOf(BEVERAGE);
    }
}
