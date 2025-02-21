package ra.presentation;

import ra.business.CategoryBusiness;
import ra.entity.Category;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CategoryManagement {

    public static void getList() {
        List<Category> list = CategoryBusiness.getListCategory();
        list.forEach(System.out::println);
    }

    public static void addCategory(Scanner sc) {
        System.out.print("Nhập tên danh mục: ");
        Category category = new Category();
        category.setName(sc.nextLine());

        if (CategoryBusiness.save(category)) {
            System.out.println("Thêm danh mục thành công!");
        } else {
            System.err.println("Thêm danh mục thất bại!");
        }
    }

    public static void editCategory(Scanner sc) {
        List<Category> list = CategoryBusiness.getListCategory();
        if (list.isEmpty()) {
            System.err.println("Không có danh mục nào để cập nhật!");
            return;
        }

        int choice = chooseCategory(sc, list, "Chọn danh mục cần cập nhật");
        if (choice == -1) return;

        Category category = list.get(choice);
        while (true) {
            System.out.println("Chọn thông tin cần cập nhật:\n1. Tên danh mục\n2. Trạng thái\n3. Thoát");
            System.out.print("Lựa chọn: ");

            try {
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1:
                        System.out.print("Nhập tên mới: ");
                        category.setName(sc.nextLine());
                        break;
                    case 2:
                        category.setStatus(!category.isStatus());
                        System.out.println("Trạng thái cập nhật: " + (category.isStatus() ? "Hoạt động" : "Không hoạt động"));
                        break;
                    case 3:
                        if (CategoryBusiness.update(category)) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        return;
                    default:
                        System.err.println("Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    public static void deleteCategory(Scanner sc) {
        List<Category> list = CategoryBusiness.getListCategory();
        if (list.isEmpty()) {
            System.err.println("Không có danh mục nào để xoá!");
            return;
        }

        int choice = chooseCategory(sc, list, "Chọn danh mục cần xoá");
        if (choice == -1) return;

        Category category = list.get(choice);
        System.out.printf("Bạn có chắc chắn muốn xoá danh mục '%s'? (1. Đồng ý, 2. Không): ", category.getName());

        try {
            int confirm = Integer.parseInt(sc.nextLine());
            if (confirm == 1) {
                if (CategoryBusiness.delete(category.getId())) {
                    System.out.println("Xoá thành công!");
                } else {
                    System.err.println("Xoá thất bại!");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số hợp lệ!");
        }
    }

    public static void statisticProduct() {
        Map<String, Integer> statistic = CategoryBusiness.statisticProduct();
        statistic.forEach((name, count) -> System.out.printf("%s: %d sản phẩm\n", name, count));
    }

    private static int chooseCategory(Scanner sc, List<Category> list, String message) {
        System.out.println(message);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, list.get(i).getName());
        }

        while (true) {
            try {
                System.out.print("Lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine()) - 1;
                if (choice >= 0 && choice < list.size()) {
                    return choice;
                }
                System.err.println("Lựa chọn không hợp lệ!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }
}