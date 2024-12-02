
-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50),
    total DECIMAL(10, 2)
);

-- Create shipping_info table
CREATE TABLE IF NOT EXISTS shipping_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient VARCHAR(100),
    address VARCHAR(255),
    status VARCHAR(50),
    order_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);

-- Insert test data into orders
INSERT INTO orders (status, total) VALUES 
('Pending', 500.00),
('Completed', 1200.00);

-- Insert test data into shipping_info
INSERT INTO shipping_info (recipient, address, status, order_id) VALUES 
('John Doe', '123 Main Street', 'Pending', 1),
('Jane Smith', '456 Elm Street', 'Shipped', 2);
