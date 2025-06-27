import java.sql.Timestamp;

public record RestockRequest(
        int requestId,
        int productId,
        int quantityRequested,
        Timestamp requestDate,
        String status,
        String priority,
        String requestedBy,
        Timestamp fulfilledDate
) implements Entity { }
