CREATE TABLE IF NOT EXISTS user_payment_preference (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT,
    payment_method_id INT,
    account_last4 VARCHAR(4),
    provider_token TEXT,
    is_primary BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)
