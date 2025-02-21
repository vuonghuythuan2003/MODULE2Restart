package ra.business;

import ra.entity.Product;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductBusiness {
    // Lấy danh sách sản phẩm
    public static List<Product> getListProduct() {
        List<Product> listProduct = new ArrayList<>();
        String sql = "{call Proc_GetAllProducts()}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                listProduct.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("stock"),
                        rs.getDouble("cost_price"),
                        rs.getDouble("selling_price"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("category_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }
        return listProduct;
    }

    // Thêm sản phẩm mới
    public static boolean save(Product product) {
        String sql = "{call Proc_AddProduct(?,?,?,?,?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, product.getName());
            cs.setInt(2, product.getStock());
            cs.setDouble(3, product.getCostPrice());
            cs.setDouble(4, product.getSellingPrice());
            cs.setInt(5, product.getCategoryId());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật sản phẩm
    public static boolean update(Product product) {
        String sql = "{call Proc_UpdateProduct(?,?,?,?,?,?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, product.getId());
            cs.setString(2, product.getName());
            cs.setInt(3, product.getStock());
            cs.setDouble(4, product.getCostPrice());
            cs.setDouble(5, product.getSellingPrice());
            cs.setInt(6, product.getCategoryId());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Xóa sản phẩm
    public static boolean delete(int id) {
        String sql = "{call Proc_DeleteProduct(?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    // Hiển thị danh sách sản phẩm theo ngày tạo giảm dần
    public static List<Product> getProductsSortedByDate() {
        List<Product> listProduct = new ArrayList<>();
        String sql = "{call Proc_GetProductsSortedByDate()}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                listProduct.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("stock"),
                        rs.getDouble("cost_price"),
                        rs.getDouble("selling_price"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("category_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm theo ngày tạo: " + e.getMessage());
        }
        return listProduct;
    }

    // Tìm kiếm sản phẩm theo khoảng giá bán
    public static List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        List<Product> listProduct = new ArrayList<>();
        String sql = "{call Proc_SearchProductByPrice(?,?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setDouble(1, minPrice);
            cs.setDouble(2, maxPrice);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    listProduct.add(new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getInt("stock"),
                            rs.getDouble("cost_price"),
                            rs.getDouble("selling_price"),
                            rs.getTimestamp("created_at"),
                            rs.getInt("category_id")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm sản phẩm theo giá bán: " + e.getMessage());
        }
        return listProduct;
    }

    // Hiển thị top 3 sản phẩm có lợi nhuận cao nhất
    public static List<Product> getTop3MostProfitableProducts() {
        List<Product> listProduct = new ArrayList<>();
        String sql = "{call Proc_GetTop3ProfitableProducts()}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                listProduct.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("stock"),
                        rs.getDouble("cost_price"),
                        rs.getDouble("selling_price"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("category_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy top 3 sản phẩm có lợi nhuận cao nhất: " + e.getMessage());
        }
        return listProduct;
    }
}
