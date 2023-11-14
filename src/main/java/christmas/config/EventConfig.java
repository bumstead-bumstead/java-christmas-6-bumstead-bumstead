package christmas.config;

import christmas.model.Menu;

import java.util.List;

public class EventConfig {
    public static final int EVENT_YEAR = 2023;
    public static final int EVENT_MONTH = 12;
    public static final int DAY_START = 1;
    public static final int DAY_END = 31;
    public static final int MINIMUM_NUMBER_OF_NON_BEVERAGE = 1;
    public static final int NUMBER_OF_DEFAULT_GIFT = 1;
    public static final int MINIMUM_ORDER_AMOUNT = 10_000;
    public static final int MAXIMUM_NUMBER_OF_MENU = 20;
    public static final int END_DAY_OF_D_DAY_EVENT = 25;
    public static final List<Integer> STARRED_DAYS = List.of(3, 10, 17, 24, 25, 31);
    public static final Menu DEFAULT_GIFT = Menu.CHAMPAGNE;
    public static final int SPECIAL_DISCOUNT = 1_000;
    public static final int D_DAY_DISCOUNT_LOWER_BOUND = 1_000;
    public static final int D_DAY_DISCOUNT_UNIT = 100;
    public static final int PURCHASE_AMOUNT_BASELINE = 120_000;
    public static final String MENU_SEPARATOR = ",";
    public static final String MENU_COUNT_SEPARATOR = "-";
    public static final String MENU_INPUT_REGEX = "^[^-]+-\\d+$";
}
