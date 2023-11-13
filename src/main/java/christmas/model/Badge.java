package christmas.model;

import java.util.Arrays;

public enum Badge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NO_BADGE("배지 없음", 0);

    private final String label;
    private final int minimumBenefit;

    Badge(String label, int minimumBenefit) {
        this.label = label;
        this.minimumBenefit = minimumBenefit;
    }

    public String getLabel() {
        return label;
    }

    public int getMinimumBenefit() {
        return minimumBenefit;
    }

    public static Badge fromTotalBenefit(int totalBenefit) {
        return Arrays.stream(Badge.values())
                .filter(badge -> totalBenefit >= badge.getMinimumBenefit())
                .findFirst()
                .orElse(NO_BADGE);
    }
}
