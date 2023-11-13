package christmas.dto;

import christmas.model.Menu;

import java.util.Objects;

import static christmas.config.EventConfig.MENU_COUNT_SEPARATOR;

public class MenuDto {
    private final Menu menu;
    private final int count;

    private MenuDto(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getCount() {
        return count;
    }

    public static MenuDto fromConsoleInputFormat(String consoleInput) {
        String[] parts = consoleInput.split(MENU_COUNT_SEPARATOR);
        Menu menu = Menu.of(parts[0]);
        int count = Integer.parseInt(parts[1]);

        return new MenuDto(menu, count);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MenuDto menuDto = (MenuDto) object;
        return count == menuDto.count && menu == menuDto.menu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(menu, count);
    }
}
