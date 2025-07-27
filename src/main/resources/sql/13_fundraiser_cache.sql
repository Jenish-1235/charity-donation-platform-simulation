CREATE TABLE IF NOT EXISTS fundraiser_cache (
    fundraiser_id INT PRIMARY KEY,
    total_amount DECIMAL(12,2),
    donor_count INT,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)
