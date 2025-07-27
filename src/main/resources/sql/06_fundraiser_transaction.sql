CREATE TABLE IF NOT EXISTS fundraiser_transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT,
    fundraiser_id INT,
    amount DECIMAL(12,2),
    receipt_status VARCHAR(50),
    receipt_url TEXT,
    ack_url TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)
