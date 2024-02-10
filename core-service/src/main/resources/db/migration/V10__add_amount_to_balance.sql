-- Заполнение таблицы types
ALTER TABLE balances ADD amount NUMERIC(15, 2) CONSTRAINT chk_balances_amount CHECK (amount >= 0);