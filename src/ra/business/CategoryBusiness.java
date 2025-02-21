package ra.business;

import ra.entity.Category;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryBusiness {
    // Lấy danh sách danh mục
    public static List<Category> getListCategory() {
        List<Category> listCategory = new ArrayList<>();
        String sql = "{call Proc_GetAllCategories()}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                listCategory.add(new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getBoolean("category_status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách danh mục: " + e.getMessage());
        }
        return listCategory;
    }

    // Thêm danh mục mới
    public static boolean save(Category category) {
        String sql = "{call Proc_AddCategory(?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, category.getName());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm danh mục: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật danh mục
    public static boolean update(Category category) {
        String sql = "{call Proc_UpdateCategory(?,?,?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, category.getId());
            cs.setString(2, category.getName());
            cs.setBoolean(3, category.isStatus());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật danh mục: " + e.getMessage());
            return false;
        }
    }

    // Xóa danh mục
    public static boolean delete(int id) {
        String sql = "{call Proc_DeleteCategory(?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa danh mục: " + e.getMessage());
            return false;
        }
    }

    // Thống kê số lượng sản phẩm theo danh mục
    public static Map<String, Integer> statisticProduct() {
        Map<String, Integer> statistics = new HashMap<>();
        String sql = "{call Proc_GetCategoryStatistics()}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                statistics.put(rs.getString("category_name"), rs.getInt("product_count"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thống kê sản phẩm: " + e.getMessage());
        }
        return statistics;
    }
}