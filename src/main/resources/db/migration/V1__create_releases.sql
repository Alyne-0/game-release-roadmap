CREATE TABLE releases (
    version VARCHAR(32) PRIMARY KEY,
    title VARCHAR(120) NOT NULL,
    planned_date DATE NOT NULL,
    status VARCHAR(16) NOT NULL CHECK (status IN ('PLANNED', 'IN_PROGRESS', 'RELEASED'))
);

INSERT INTO releases (version, title, planned_date, status) VALUES
    ('0.1.0', 'Закрытая альфа', DATE '2027-02-15', 'IN_PROGRESS'),
    ('0.2.0', 'Открытая бета', DATE '2027-04-20', 'PLANNED');
