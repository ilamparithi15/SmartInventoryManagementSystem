package com.example;
import java.sql.*;

// Sealed Interface
public sealed interface Entity permits Product, Stock, RestockRequest {}

// Product record
record Product(int productId, String productName, String description, String category, double unitPrice, String supplier, int currentStockQuantity) implements Entity {}

// Stock Movement record
record Stock(int movementId, int productId, String movementType, int quantity, Timestamp movementDate, String sourceLocation, String destinationLocation, String recordedBy) implements Entity {}

// Restock Request record
record RestockRequest(int requestId, int productId, int quantityRequested, Timestamp requestDate, String status, String priority, String requestedBy, Timestamp fulfilledDate) implements Entity {}

