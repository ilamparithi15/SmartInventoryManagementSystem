import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SmartInventoryJDBC {

    // Step 1: Oracle DB credentials
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "password";

    // Step 2: Get connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Step 3: Update product stock in PRODUCTS table
    public static void updateProductStock(int productId, int newQuantity) {
        String sql = "UPDATE PRODUCTS SET CURRENT_STOCK_QUANTITY = ? WHERE PRODUCT_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, productId);
            int rows = ps.executeUpdate();
            System.out.println("Updated stock for Product ID: " + productId + " | Rows affected: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Step 4: Insert into STOCK_MOVEMENTS table
    public static void logStockMovement(int productId, String type, int qty, String src, String dest, String user) {
        String sql = "INSERT INTO STOCK_MOVEMENTS (MOVEMENT_ID, PRODUCT_ID, MOVEMENT_TYPE, QUANTITY, MOVEMENT_DATE, SOURCE_LOCATION, DESTINATION_LOCATION, RECORDED_BY) " +
                "VALUES (movement_seq.NEXTVAL, ?, ?, ?, SYSTIMESTAMP, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setString(2, type); // "IN" or "OUT"
            ps.setInt(3, qty);
            ps.setString(4, src);
            ps.setString(5, dest);
            ps.setString(6, user);
            ps.executeUpdate();
            System.out.println("Logged " + type + " movement for Product ID: " + productId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Step 5: Get list of low stock items
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
            e.printStackTrace();
        }
        return lowStockList;
    }

    // Step 6: Main testing
    public static void main(String[] args) {
        int testProductId = 101;

        // 1. Update product stock
        updateProductStock(testProductId, 50);

        // 2. Log stock movement
        logStockMovement(testProductId, "IN", 20, "SupplierA", "WarehouseA", "admin");

        // 3. Query low stock items
        System.out.println("\nLow Stock Items:");
        List<Product> lowStock = getLowStockProducts(60);
        for (Product p : lowStock) {
            System.out.println(p.productName() + " (Qty: " + p.currentStockQuantity() + ")");
        }
    }
}
