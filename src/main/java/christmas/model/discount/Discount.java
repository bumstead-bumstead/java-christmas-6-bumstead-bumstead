package christmas.model.discount;

public abstract class Discount {
    private final int amount;
    private final String label;

    public int getAmount() {
        return amount;
    }

    public String getLabel() {
        return label;
    }

    protected Discount(int amount, String label) {
        this.amount = amount;
        this.label = label;
    }

    public boolean hasDiscount() {
        return amount > 0;
    }
}
