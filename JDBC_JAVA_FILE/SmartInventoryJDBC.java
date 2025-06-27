import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SmartInventoryJDBC {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "password";

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
            System.out.println("Updated stock for Product ID: " + productId + " | Rows affected: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
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
            System.out.println("Logged " + type + " movement for Product ID: " + productId);
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return list;
    }

    // Test all methods
    public static void main(String[] args) {
        int testProductId = 101;

        updateProductStock(testProductId, 50);
        logStockMovement(testProductId, "IN", 20, "SupplierA", "WarehouseA", "admin");

        System.out.println("\nLow Stock Items:");
        for (Product p : getLowStockProducts(60)) {
            System.out.println(p.productName() + " (Qty: " + p.currentStockQuantity() + ")");
        }

        System.out.println("\nAll Stock Movements:");
        for (Stock s : getAllStockMovements()) {
            System.out.println("Product ID: " + s.productId() + ", Type: " + s.movementType() + ", Qty: " + s.quantity());
        }

        System.out.println("\nAll Restock Requests:");
        for (RestockRequest r : getAllRestockRequests()) {
            System.out.println("Product ID: " + r.productId() + ", Qty: " + r.quantityRequested() + ", Status: " + r.status());
        }
    }
}
