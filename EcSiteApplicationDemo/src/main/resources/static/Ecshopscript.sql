USE shop_tracker;


CREATE TABLE users (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password CHAR(68) NOT NULL UNIQUE,
    active TINYINT NOT NULL,
    joined_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    user_id VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    UNIQUE KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE shops (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shop_name VARCHAR(255) NOT NULL,
    description TEXT,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE products (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    description TEXT,
    price int NOT NULL,
    inventory int NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    shop_id int NOT NULL,
    FOREIGN KEY (shop_id) REFERENCES shops(id) ON DELETE CASCADE
);


SELECT*FROM users;
SELECT*FROM roles;
SELECT*FROM shops;
SELECT*FROM products;

DESCRIBE users;
DESCRIBE roles;
DESCRIBE shops;
DESCRIBE products;

SHOW TABLES;

-- ユーザーレコードの生成
-- パスワードはユーザー名+123(例:TanakaTaro123)
INSERT INTO users (id, email, password, active) VALUES
('TanakaTaro', 'tanaka_taro@example.com', '{bcrypt}$2a$12$PYTDx24Fo4g2fWCAFJpiGOWWgRaEq6TyngIq9lvxGBAtHssFFmZvy', 1),
('SuzukiIchiro', 'suzuki_ichiro@example.com', '{bcrypt}$2a$12$sOEh10TG3MboCmSlbKdfzOM4uBsGmUJnjQhkUR1lWKbpAvhw5.Bqi', 1),
('TakahashiJiro', 'takahashi_jiro@example.com', '{bcrypt}$2a$12$VEGZPy0sbOlj55nlbEQXTu.wMqDkPHFPFIxGhDu592w9Yek1Fw6Qm', 1),
('WatanabeSaburo', 'watanabe_saburo@example.com', '{bcrypt}$2a$12$piV4yqx/ZJxdFVFdeHyiYuXInRUEaNKm6M7i09T/tzrFuME5jLXuq', 1),
('YamamotoShiro', 'yamamoto_shiro@example.com', '{bcrypt}$2a$12$J3GFVRbNSFryU/MK3pelpOBixiBf6aIeickaJwSLVU2vO62Ye91qK', 1),
('NakamuraGoro', 'nakamura_goro@example.com', '{bcrypt}$2a$12$ax13T3vqjZO12ZCm2/V1MOfRL65vSfDEGcfTHlt47bVlgWTgOBkSS', 1),
('KobayashiRoku', 'kobayashi_roku@example.com', '{bcrypt}$2a$12$/ibLTcWBuyGSNVuBiflnTeuIFSJWHAooOtdTGCemrXG0s4VIvMWd.', 1),
('KatoShichi', 'kato_shichi@example.com', '{bcrypt}$2a$12$RzY2Zgcscq8BfLdWaKVRberEXEKUiW0f3.CD..1qS5pQ8GyJvmGN2', 1),
('YoshidaHachi', 'yoshida_hachi@example.com', '{bcrypt}$2a$12$UBalLSad6Rn7BRCMCBNhzuxomXA/lZ3KvFmUT5nBDesxbw8jFDOiC', 1),
('YamadaKyu', 'yamada_kyu@example.com', '{bcrypt}$2a$12$fVuDVN4OpiMsyd8G2x.X0.Q8eeGZ1zA2lGg44KLsV30rIuwK5zQpm', 1);

-- ロールレコードの生成
INSERT INTO roles (user_id, role) VALUES
('TanakaTaro', 'ROLE_USER'),
('TanakaTaro', 'ROLE_OWNER'),
('SuzukiIchiro', 'ROLE_USER'),
('SuzukiIchiro', 'ROLE_OWNER'),
('TakahashiJiro', 'ROLE_USER'),
('WatanabeSaburo', 'ROLE_USER'),
('YamamotoShiro', 'ROLE_USER'),
('NakamuraGoro', 'ROLE_USER'),
('KobayashiRoku', 'ROLE_USER'),
('KobayashiRoku', 'ROLE_OWNER'),
('KatoShichi', 'ROLE_USER'),
('YoshidaHachi', 'ROLE_USER'),
('YamadaKyu', 'ROLE_USER');

-- ショップレコードの生成
INSERT INTO shops (shop_name, description, user_id) VALUES
('田中太郎の本屋', '田中太郎が運営する本屋です。ここでは、田中太郎が厳選した本を取り扱っています。', 'TanakaTaro'),
('田中太郎のカフェ', '田中太郎が運営するカフェです。ここでは、田中太郎が自慢するコーヒーを提供しています。', 'TanakaTaro'),
('田中太郎の雑貨店', '田中太郎が運営する雑貨店です。ここでは、田中太郎が集めたユニークな雑貨を販売しています。', 'TanakaTaro'),
('鈴木一郎のレストラン', '鈴木一郎が運営するレストランです。ここでは、鈴木一郎が作る美味しい料理を楽しむことができます。', 'SuzukiIchiro'),
('鈴木一郎のバー', '鈴木一郎が運営するバーです。ここでは、鈴木一郎が選んだ美味しいお酒を提供しています。', 'SuzukiIchiro'),
('鈴木一郎のギャラリー', '鈴木一郎が運営するギャラリーです。ここでは、鈴木一郎が集めた美術作品を展示しています。', 'SuzukiIchiro'),
('小林六の工房', '小林六が運営する工房です。ここでは、小林六が作った手作りの商品を販売しています。', 'KobayashiRoku'),
('小林六の音楽スタジオ', '小林六が運営する音楽スタジオです。ここでは、小林六が作った音楽を楽しむことができます。', 'KobayashiRoku'),
('小林六のアトリエ', '小林六が運営するアトリエです。ここでは、小林六が描いた絵画を展示しています。', 'KobayashiRoku');

-- 商品レコードの生成
INSERT INTO products (product_name, description, price, inventory, shop_id) VALUES
('田中太郎の本1', '商品説明', 5000, 50, 1),
('田中太郎の本2', '商品説明', 3000, 30, 1),
('田中太郎の本3', '商品説明', 7000, 70, 1),
('田中太郎のコーヒー1', '商品説明', 2500, 25, 2),
('田中太郎のコーヒー2', '商品説明', 3500, 35, 2),
('田中太郎のコーヒー3', '商品説明', 4500, 45, 2),
('田中太郎の雑貨1', '商品説明', 6000, 60, 3),
('田中太郎の雑貨2', '商品説明', 4000, 40, 3),
('田中太郎の雑貨3', '商品説明', 2000, 20, 3),
('鈴木一郎の料理1', '商品説明', 8000, 80, 4),
('鈴木一郎の料理2', '商品説明', 9000, 90, 4),
('鈴木一郎の料理3', '商品説明', 10000, 100, 4),
('鈴木一郎のお酒1', '商品説明', 8500, 85, 5),
('鈴木一郎のお酒2', '商品説明', 9500, 95, 5),
('鈴木一郎のお酒3', '商品説明', 7500, 75, 5),
('鈴木一郎の美術作品1', '商品説明', 5000, 50, 6),
('鈴木一郎の美術作品2', '商品説明', 3000, 30, 6),
('鈴木一郎の美術作品3', '商品説明', 7000, 70, 6),
('小林六の手作り商品1', '商品説明', 2500, 25, 7),
('小林六の手作り商品2', '商品説明', 3500, 35, 7),
('小林六の手作り商品3', '商品説明', 4500, 45, 7),
('小林六の音楽1', '商品説明', 6000, 60, 8),
('小林六の音楽2', '商品説明', 4000, 40, 8),
('小林六の音楽3', '商品説明', 2000, 20, 8),
('小林六の絵画1', '商品説明', 8000, 80, 9),
('小林六の絵画2', '商品説明', 9000, 90, 9),
('小林六の絵画3', '商品説明', 10000, 100, 9);




















