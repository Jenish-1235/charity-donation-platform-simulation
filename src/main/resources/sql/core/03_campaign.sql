CREATE TABLE IF NOT EXISTS campaign (
    campaign_id INT AUTO_INCREMENT PRIMARY KEY,
    charity_id INT NOT NULL,
    category_id INT,
    title TEXT NOT NULL,
    description TEXT,
    rec_url TEXT,
    ack_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    start_date DATE,
    end_date DATE
);
