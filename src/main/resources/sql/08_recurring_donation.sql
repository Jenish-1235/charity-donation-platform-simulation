CREATE TABLE IF NOT EXISTS recurring_donation (
    recurring_donation_id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT,
    entity_type_id INT,
    entity_id INT,
    charity_id INT,
    recurring_rate VARCHAR(20),
    amount DECIMAL(10,2),
    last_installment DATE,
    next_installment DATE,
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN,
    primary_payment_method_id INT
)
