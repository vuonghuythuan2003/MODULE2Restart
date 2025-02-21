package ra.presentation;

import ra.business.ProductBusiness;
import ra.entity.Product;

import java.util.List;
import java.util.Scanner;

public class ProductManagement {
    public static void manageProducts(Scanner sc) {
        while (true) {
            System.out.println("****************** PRODUCT MENU ******************\n" +
                    "1. Danh sách sản phẩm\n" +
                    "2. Tạo mới sản phẩm\n" +
                    "3. Cập nhật sản phẩm\n" +
                    "4. Xóa sản phẩm\n" +
                    "5. Hiển thị danh sách sản phẩm theo ngày tạo giảm dần\n" +
                    "6. Tìm kiếm sản phẩm theo khoản giá bán\n" +
                    "7. Hiển thị top 3 sản phẩm có lợi nhuận cao nhất\n" +
                    "8. Quay lại\n");
            System.out.print("Lựa chọn của bạn: ");

            int choice = getUserChoice(sc);
            switch (choice) {
                case 1:
                    listProducts();
                    break;
                case 2:
                    addProduct(sc);
                    break;
                case 3:
                    updateProduct(sc);
                    break;
                case 4:
                    deleteProduct(sc);
                    break;
                case 5:
                    listProductsByDateDesc();
                    break;
                case 6:
                    searchProductsByPrice(sc);
                    break;
                case 7:
                    top3MostProfitableProducts();
                    break;
                case 8:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ! Vui lòng nhập lại.");
            }
        }
    }

    private static void listProducts() {
        List<Product> products = ProductBusiness.getListProduct();
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
        } else {
            for (int i = 0; i < products.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, products.get(i));
            }
        }
    }

    private static void addProduct(Scanner sc) {
        System.out.print("Nhập tên sản phẩm: ");
        String name = sc.nextLine().trim();
        System.out.print("Nhập số lượng: ");
        int stock = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Nhập giá nhập: ");
        double costPrice = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Nhập giá bán: ");
        double sellingPrice = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Nhập mã danh mục: ");
        int categoryId = Integer.parseInt(sc.nextLine().trim());

        Product product = new Product(0, name, stock, costPrice, sellingPrice, null, categoryId);
        if (ProductBusiness.save(product)) {
            System.out.println("Thêm sản phẩm thành công!");
        } else {
            System.err.println("Thêm sản phẩm thất bại!");
        }
    }

    private static void updateProduct(Scanner sc) {
        List<Product> products = ProductBusiness.getListProduct();
        if (products.isEmpty()) {
            System.err.println("Không có sản phẩm nào để cập nhật!");
            return;
        }

        int choice = chooseProduct(sc, products, "Chọn sản phẩm cần cập nhật");
        if (choice == -1) return;

        Product product = products.get(choice);
        System.out.print("Nhập tên mới (bỏ trống để giữ nguyên): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) product.setName(name);

        System.out.print("Nhập số lượng mới: ");
        product.setStock(Integer.parseInt(sc.nextLine().trim()));
        System.out.print("Nhập giá nhập mới: ");
        product.setCostPrice(Double.parseDouble(sc.nextLine().trim()));
        System.out.print("Nhập giá bán mới: ");
        product.setSellingPrice(Double.parseDouble(sc.nextLine().trim()));
        System.out.print("Nhập mã danh mục mới: ");
        product.setCategoryId(Integer.parseInt(sc.nextLine().trim()));

        if (ProductBusiness.update(product)) {
            System.out.println("Cập nhật sản phẩm thành công!");
        } else {
            System.err.println("Cập nhật sản phẩm thất bại!");
        }
    }

    private static void deleteProduct(Scanner sc) {
        List<Product> products = ProductBusiness.getListProduct();
        if (products.isEmpty()) {
            System.err.println("Không có sản phẩm nào để xoá!");
            return;
        }

        int choice = chooseProduct(sc, products, "Chọn sản phẩm cần xoá");
        if (choice == -1) return;

        Product product = products.get(choice);
        System.out.printf("Bạn có chắc chắn muốn xoá sản phẩm '%s'? (1. Đồng ý, 2. Không): ", product.getName());

        try {
            int confirm = Integer.parseInt(sc.nextLine());
            if (confirm == 1) {
                if (ProductBusiness.delete(product.getId())) {
                    System.out.println("Xoá thành công!");
                } else {
                    System.err.println("Xoá thất bại!");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số hợp lệ!");
        }
    }

    private static int chooseProduct(Scanner sc, List<Product> products, String message) {
        System.out.println(message);
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, products.get(i).getName());
        }

        while (true) {
            try {
                System.out.print("Lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine()) - 1;
                if (choice >= 0 && choice < products.size()) {
                    return choice;
                }
                System.err.println("Lựa chọn không hợp lệ!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    private static int getUserChoice(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Lựa chọn phải là số! Vui lòng nhập lại.");
            return -1;
        }
    }
    public static void listProductsByDateDesc() {
        List<Product> products = ProductBusiness.getProductsSortedByDate();
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
        } else {
            System.out.println("Danh sách sản phẩm theo ngày tạo giảm dần:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }
    public static void searchProductsByPrice(Scanner sc) {
        System.out.print("Nhập giá thấp nhất: ");
        double minPrice = sc.nextDouble();
        System.out.print("Nhập giá cao nhất: ");
        double maxPrice = sc.nextDouble();

        List<Product> products = ProductBusiness.searchByPriceRange(minPrice, maxPrice);
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào trong khoảng giá này.");
        } else {
            System.out.println("Danh sách sản phẩm trong khoảng giá từ " + minPrice + " đến " + maxPrice + ":");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }
    public static void top3MostProfitableProducts() {
        List<Product> products = ProductBusiness.getTop3MostProfitableProducts();
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
        } else {
            System.out.println("Top 3 sản phẩm có lợi nhuận cao nhất:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }
}