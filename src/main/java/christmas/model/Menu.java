package christmas.model;

import static christmas.model.FoodCategory.*;

public enum Menu {
    MUSHROOM_SOUP("양송이수프", 6_000, APPETIZER),
    TAPAS("타파스", 5_500, APPETIZER),
    CAESAR_SALAD("시저샐러드", 8_000, APPETIZER),
    T_BONE_STEAK("티본스테이크", 55_000, MAIN),
    BARBECUE_LIP("바비큐립", 54_000, MAIN),
    SEAFOOD_PASTA("해산물파스타", 35_000, MAIN),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000, MAIN),
    CHOCOLATE_CAKE("초코케이크", 15_000, DESSERT),
    ICE_CREAM("아이스크림", 5_000, DESSERT),
    ZERO_COKE("제로콜라", 3_000, BEVERAGE),
    RED_WINE("레드와인", 60_000, BEVERAGE),
    CHAMPAGNE("샴페인", 25_000, BEVERAGE);

    public int getPrice() {
        return price;
    }

    private final String label;
    private final int price;
    private final FoodCategory foodCategory;

    Menu(String label, int price, FoodCategory foodCategory) {
        this.label = label;
        this.price = price;
        this.foodCategory = foodCategory;
    }

    public boolean isKindOf(FoodCategory foodCategory) {
        return this.foodCategory.equals(foodCategory);
    }

    public static Menu of(String label) {
        for (Menu menu : Menu.values()) {
            if (menu.label.equals(label)) {
                return menu;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 메뉴가 있습니다.");
    }
}
