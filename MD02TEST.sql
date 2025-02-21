CREATE DATABASE quanlybanhang;
USE quanlybanhang;

CREATE TABLE Categories (
                            category_id INT PRIMARY KEY AUTO_INCREMENT,
                            category_name VARCHAR(50) NOT NULL UNIQUE,
                            category_status BIT DEFAULT 1
);

CREATE TABLE Products (
                          product_id INT PRIMARY KEY AUTO_INCREMENT,
                          product_name VARCHAR(20) NOT NULL UNIQUE,
                          stock INT NOT NULL,
                          cost_price DOUBLE NOT NULL CHECK (cost_price >= 0),
                          selling_price DOUBLE NOT NULL CHECK (selling_price >= 0),
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          category_id INT NOT NULL,
                          FOREIGN KEY (category_id) REFERENCES Categories(category_id) ON DELETE CASCADE
);

DELIMITER //
CREATE PROCEDURE Proc_GetAllCategories()
BEGIN
    SELECT * FROM Categories;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE Proc_AddCategory(
    IN cat_name VARCHAR(50)
)
BEGIN
    INSERT INTO Categories (category_name, category_status)
    VALUES (cat_name, 1);
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE Proc_UpdateCategory(
    IN cat_id INT,
    IN cat_name VARCHAR(50),
    IN cat_status BIT
)
BEGIN
    UPDATE Categories
    SET category_name = cat_name, category_status = cat_status
    WHERE category_id = cat_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE Proc_DeleteCategory(
    IN cat_id INT
)
BEGIN
    -- Kiểm tra nếu danh mục có sản phẩm thì không xóa
    IF EXISTS (SELECT 1 FROM Products WHERE category_id = cat_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Không thể xóa danh mục có sản phẩm!';
    ELSE
        DELETE FROM Categories WHERE category_id = cat_id;
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE Proc_GetCategoryStatistics()
BEGIN
    SELECT c.category_name, COUNT(p.product_id) AS product_count
    FROM Categories c
             LEFT JOIN Products p ON c.category_id = p.category_id
    GROUP BY c.category_name;
END //
DELIMITER ;


DELIMITER //

-- 1. Lấy danh sách sản phẩm
CREATE PROCEDURE Proc_GetAllProducts()
BEGIN
    SELECT * FROM Products;
END //

-- 2. Thêm mới sản phẩm
CREATE PROCEDURE Proc_AddProduct(
    IN p_name VARCHAR(20),
    IN p_stock INT,
    IN p_cost_price DOUBLE,
    IN p_selling_price DOUBLE,
    IN p_category_id INT
)
BEGIN
    INSERT INTO Products (product_name, stock, cost_price, selling_price, category_id)
    VALUES (p_name, p_stock, p_cost_price, p_selling_price, p_category_id);
END //

-- 3. Cập nhật sản phẩm
CREATE PROCEDURE Proc_UpdateProduct(
    IN p_id INT,
    IN p_name VARCHAR(20),
    IN p_stock INT,
    IN p_cost_price DOUBLE,
    IN p_selling_price DOUBLE,
    IN p_category_id INT
)
BEGIN
    UPDATE Products
    SET product_name = p_name,
        stock = p_stock,
        cost_price = p_cost_price,
        selling_price = p_selling_price,
        category_id = p_category_id
    WHERE product_id = p_id;
END //

-- 4. Xóa sản phẩm
CREATE PROCEDURE Proc_DeleteProduct(
    IN p_id INT
)
BEGIN
    DELETE FROM Products WHERE product_id = p_id;
END //

-- 5. Hiển thị danh sách sản phẩm theo ngày tạo giảm dần
CREATE PROCEDURE Proc_GetProductsSortedByDate()
BEGIN
    SELECT * FROM Products ORDER BY created_at DESC;
END //

-- 6. Tìm kiếm sản phẩm theo khoảng giá bán
CREATE PROCEDURE Proc_FindProductsByPriceRange(
    IN min_price DOUBLE,
    IN max_price DOUBLE
)
BEGIN
    SELECT * FROM Products
    WHERE selling_price BETWEEN min_price AND max_price;
END //

-- 7. Hiển thị top 3 sản phẩm có lợi nhuận cao nhất (lợi nhuận = giá bán - giá nhập)
CREATE PROCEDURE Proc_GetTop3ProfitableProducts()
BEGIN
    SELECT *, (selling_price - cost_price) AS profit
    FROM Products
    ORDER BY profit DESC
    LIMIT 3;
END //

DELIMITER ;
