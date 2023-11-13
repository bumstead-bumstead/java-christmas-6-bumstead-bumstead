package christmas.dto;

import christmas.model.Menu;

import java.util.Objects;

import static christmas.config.EventConfig.MENU_COUNT_SEPARATOR;

public class MenuDto {
    private final String menuName;
    private final int count;

    private MenuDto(String menuName, int count) {
        this.menuName = menuName;
        this.count = count;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getCount() {
        return count;
    }

    public static MenuDto fromConsoleInputFormat(String consoleInput) {
        String[] parts = consoleInput.split(MENU_COUNT_SEPARATOR);
        String menuName = parts[0];
        int count = Integer.parseInt(parts[1]);

        return new MenuDto(menuName, count);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MenuDto menuDto = (MenuDto) object;
        return count == menuDto.count && Objects.equals(menuName, menuDto.menuName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuName, count);
    }
}
