public abstract class ProductCalculations implements IProductCalculations {

    private static final double VAT_PERCENTAGE = 15;
    private static final double COMMISSION_PERCENTAGE = 5;

    private final double price;

    public ProductCalculations(double price) {
        this.price = price;
    }

    // VAT is 15% of the product price
    @Override
    public double GetVat() {
        return price * VAT_PERCENTAGE / 100;
    }

    // Commission is 5% of the product price
    @Override
    public double GetCommission() {
        return price * COMMISSION_PERCENTAGE / 100;
    }

    // Total = price + VAT + commission
    @Override
    public double GetTotal() {
        return price + GetVat() + GetCommission();
    }
}
