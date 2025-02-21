package ra.presentation;

import ra.business.ProductBusiness;
import ra.entity.Product;

import java.util.List;
import java.util.Scanner;

import static ra.presentation.ProductManagement.manageProducts;

public class StoreManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("****************** STORE MANAGEMENT ******************\n" +
                    "1. Quản lý danh mục\n" +
                    "2. Quản lý sản phẩm\n" +
                    "3. Thoát\n");
            System.out.print("Lựa chọn của bạn: ");

            int choice = getUserChoice(sc);
            switch (choice) {
                case 1:
                    categoryManagement(sc);
                    break;
                case 2:
                    manageProducts(sc);
                    break;
                case 3:
                    System.out.println("Thoát chương trình...");
                    System.exit(0);
                default:
                    System.err.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }

    private static void categoryManagement(Scanner sc) {
        while (true) {
            System.out.println("********************** CATEGORY MENU ********************\n" +
                    "1. Danh sách danh mục\n" +
                    "2. Tạo mới danh mục\n" +
                    "3. Cập nhật danh mục\n" +
                    "4. Xóa danh mục\n" +
                    "5. Thống kê số lượng sản phẩm theo danh mục\n" +
                    "6. Quay lại\n");
            System.out.print("Lựa chọn của bạn: ");

            int choice = getUserChoice(sc);
            switch (choice) {
                case 1:
                    CategoryManagement.getList();
                    break;
                case 2:
                    CategoryManagement.addCategory(sc);
                    break;
                case 3:
                    CategoryManagement.editCategory(sc);
                    break;
                case 4:
                    CategoryManagement.deleteCategory(sc);
                    break;
                case 5:
                    CategoryManagement.statisticProduct();
                    break;
                case 6:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }

    List<Product> products = ProductBusiness.getTop3MostProfitableProducts();


    private static int getUserChoice(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Lựa chọn phải là số! Vui lòng nhập lại.");
            return -1;
        }
    }
}
