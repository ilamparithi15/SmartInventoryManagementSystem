package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartInventoryJDBC {

    private static final Logger logger = LoggerFactory.getLogger(SmartInventoryJDBC.class);

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "admin123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // update stock
    public static void updateProductStock(int productId, int newQuantity) {
        String sql = "UPDATE PRODUCTS SET CURRENT_STOCK_QUANTITY = ? WHERE PRODUCT_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, productId);
            int rows = ps.executeUpdate();
            logger.info("Updated stock for Product ID: " + productId + " | Rows affected: " + rows);
        } catch (SQLException e) {
            logger.error("The error occurred while updating the products ",e);
        }
    }

    // log stock movement
    public static void logStockMovement(int productId, String type, int qty, String src, String dest, String user) {
        String sql = "INSERT INTO STOCK_MOVEMENTS (MOVEMENT_ID, PRODUCT_ID, MOVEMENT_TYPE, QUANTITY, MOVEMENT_DATE, SOURCE_LOCATION, DESTINATION_LOCATION, RECORDED_BY) " +
                "VALUES (movement_seq.NEXTVAL, ?, ?, ?, SYSTIMESTAMP, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setString(2, type);
            ps.setInt(3, qty);
            ps.setString(4, src);
            ps.setString(5, dest);
            ps.setString(6, user);
            ps.executeUpdate();
            logger.info("Logged " + type + " movement for Product ID: " + productId);
        } catch (SQLException e) {
            logger.error("The error occurred while inserting the products in STOCK_MOVEMENTS table ",e);
        }
    }

    // Existing method to get low stock
    public static List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStockList = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTS WHERE CURRENT_STOCK_QUANTITY < ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("PRODUCT_ID"),
                        rs.getString("PRODUCT_NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("CATEGORY"),
                        rs.getDouble("UNIT_PRICE"),
                        rs.getString("SUPPLIER"),
                        rs.getInt("CURRENT_STOCK_QUANTITY")
                );
                lowStockList.add(p);
            }
        } catch (SQLException e) {
            logger.error("The error occurred while selecting the products from PRODUCTS Table",e);
        }
        return lowStockList;
    }

    //  Fetch all stock movements
    public static List<Stock> getAllStockMovements() {
        List<Stock> list = new ArrayList<>();
        String sql = "SELECT * FROM STOCK_MOVEMENTS";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Stock s = new Stock(
                        rs.getInt("MOVEMENT_ID"),
                        rs.getInt("PRODUCT_ID"),
                        rs.getString("MOVEMENT_TYPE"),
                        rs.getInt("QUANTITY"),
                        rs.getTimestamp("MOVEMENT_DATE"),
                        rs.getString("SOURCE_LOCATION"),
                        rs.getString("DESTINATION_LOCATION"),
                        rs.getString("RECORDED_BY")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            logger.error("The error occurred while selecting the products from STOCK_MOVEMENTS table ",e);
        }
        return list;
    }

    //  Fetch all restock requests
    public static List<RestockRequest> getAllRestockRequests() {
        List<RestockRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM RESTOCK_REQUESTS";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RestockRequest r = new RestockRequest(
                        rs.getInt("REQUEST_ID"),
                        rs.getInt("PRODUCT_ID"),
                        rs.getInt("QUANTITY_REQUESTED"),
                        rs.getTimestamp("REQUEST_DATE"),
                        rs.getString("STATUS"),
                        rs.getString("PRIORITY"),
                        rs.getString("REQUESTED_BY"),
                        rs.getTimestamp("FULFILLED_DATE")
                );
                list.add(r);
            }
        } catch (SQLException e) {
            logger.error("The error occurred while selecting the products from RESTOCK_REQUESTS table ",e);
        }
        return list;
    }

    // Test all methods
    public static void main(String[] args) {
        int testProductId = 101;

        updateProductStock(testProductId, 50);
        logStockMovement(testProductId, "IN", 20, "SupplierA", "WarehouseA", "admin");

        logger.info("\nLow Stock Items:");
        for (Product p : getLowStockProducts(60)) {
            logger.warn("The quantity of "+p.productName() + " (Qty: " + p.currentStockQuantity() + ") is low");
        }

        logger.info("\nAll Stock Movements:");
        for (Stock s : getAllStockMovements()) {
            logger.info("Product ID: " + s.productId() + ", Type: " + s.movementType() + ", Qty: " + s.quantity());
        }

        logger.info("\nAll Restock Requests:");
        for (RestockRequest r : getAllRestockRequests()) {
            logger.info("Product ID: " + r.productId() + ", Qty: " + r.quantityRequested() + ", Status: " + r.status());
        }

        System.exit(0);
    }
}
