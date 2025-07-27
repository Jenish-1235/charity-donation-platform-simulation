CREATE TABLE IF NOT EXISTS donor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT,
    email TEXT,
    password TEXT,
    city TEXT,
    state TEXT,
    country TEXT,
    age INT,
    gender VARCHAR(10),
    income_range VARCHAR(50),
    referrer_source VARCHAR(100),
    last_donated_at DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
