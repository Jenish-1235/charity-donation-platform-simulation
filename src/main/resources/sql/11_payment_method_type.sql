CREATE TABLE IF NOT EXISTS payment_method_type (
  id INT AUTO_INCREMENT PRIMARY KEY,
  method VARCHAR(50),
  provider VARCHAR(100),
  requires_token BOOLEAN,
  callback_url TEXT
);
