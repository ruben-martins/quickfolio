-- Table: account
CREATE TABLE account
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255),
    email    VARCHAR(255) UNIQUE
);

-- Table: portfolio
CREATE TABLE portfolio
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255),
    description TEXT,
    account_id  UUID REFERENCES account (id) ON DELETE CASCADE
);

-- Table: position
CREATE TABLE position
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    key          VARCHAR(255),
    size         DOUBLE PRECISION,
    portfolio_id UUID REFERENCES portfolio (id)
);

-- Table: price
CREATE TABLE price
(
    symbol       VARCHAR(10) PRIMARY KEY,
    price        DOUBLE PRECISION
);

-- Insert initial data
insert into
    public.price (symbol, price)
values
    ('TSLA', 426.86),
    ('META', 426.86),
    ('NFLX', 426.86),
    ('NVDA', 426.86),
    ('AAPL', 426.86),
    ('GOOG', 426.86),
    ('AMZN', 426.86),
    ('MSFT', 426.86);