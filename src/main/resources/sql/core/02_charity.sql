CREATE TABLE IF NOT EXISTS charity (
    charity_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    name TEXT NOT NULL,
    email TEXT,
    password TEXT,
    description TEXT,
    website_url TEXT,
    ack_url TEXT,
    receipt_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
