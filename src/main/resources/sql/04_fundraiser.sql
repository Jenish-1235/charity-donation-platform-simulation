
CREATE TABLE IF NOT EXISTS fundraiser (
    fundraiser_id INT AUTO_INCREMENT PRIMARY KEY,
    charity_id INT NOT NULL,
    category_id INT,
    title TEXT NOT NULL,
    description TEXT,
    goal_amount DECIMAL(12,2),
    current_amount DECIMAL(12,2),
    rec_url TEXT,
    ack_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
