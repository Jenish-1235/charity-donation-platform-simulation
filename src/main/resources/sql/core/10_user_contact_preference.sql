CREATE TABLE IF NOT EXISTS user_contact_preference (
    preference_id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT,
    method_id INT,
    is_enabled BOOLEAN,
    preferred_language VARCHAR(10)
);
