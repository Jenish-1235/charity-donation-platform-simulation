CREATE TABLE IF NOT EXISTS direct_donations (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT NOT NULL,
    charity_id INT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    receipt_status VARCHAR(50),
    receipt_url TEXT,
    ack_url TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
