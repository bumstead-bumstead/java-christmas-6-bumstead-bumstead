package christmas.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static christmas.config.EventConfig.*;

public class MenuSheet {
    private final Map<Menu, Integer> menuCount;

    private MenuSheet(Map<Menu, Integer> menuCount) {
        this.menuCount = menuCount;
    }

    public static MenuSheet of(List<String> menuEntries) {
        Map<Menu, Integer> menuCount = new HashMap<>();
        menuEntries
                .forEach(menuEntry -> {
                    String[] parts = menuEntry.split(MENU_COUNT_SEPARATOR);
                    Menu menu = Menu.of(parts[0]);
                    int count = Integer.parseInt(parts[1]);

                    validateDuplication(menuCount, menu);
                    menuCount.put(menu, count);
                });
        return new MenuSheet(menuCount);
    }

    private static void validateDuplication(Map<Menu, Integer> menuCount, Menu menu) {
        if (menuCount.containsKey(menu)) {
            throw new IllegalArgumentException("메뉴 이름이 중복되었습니다.");
        }
    }
}
