import java.sql.Timestamp;

public record Stock(
        int movementId,
        int productId,
        String movementType,
        int quantity,
        Timestamp movementDate,
        String sourceLocation,
        String destinationLocation,
        String recordedBy
) implements Entity { }
