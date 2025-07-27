CREATE TABLE IF NOT EXISTS donor_donation_summary (
    donor_id INT NOT NULL,
    donation_type VARCHAR(20) NOT NULL,  -- e.g., 'CAMPAIGN', 'FUNDRAISER', 'RECURRING'
    total_amount DECIMAL(12,2) DEFAULT 0.00,
    donation_count INT DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
