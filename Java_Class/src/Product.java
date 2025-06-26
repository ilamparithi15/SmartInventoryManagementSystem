public record Product(
    int productId,
    String productName,
    String description,
    String category,
    double unitPrice,
    String supplier,
    int currentStockQuantity
) implements Entity{}
