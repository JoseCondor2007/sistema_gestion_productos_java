-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS dbVulcanizadoraJosesito
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

-- Seleccionar la base de datos para trabajar en ella
USE dbVulcanizadoraJosesito;

CREATE TABLE Producto (
                          id_producto INTEGER PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(60) NOT NULL,
                          tipo VARCHAR(50),
                          marca VARCHAR(50),
                          precio DECIMAL(10, 2),
                          stock INTEGER DEFAULT 0
);
ALTER TABLE Producto
    ADD COLUMN hay_stock BOOLEAN DEFAULT FALSE;
ALTER TABLE Producto
    ADD COLUMN fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE Producto
    ADD COLUMN hay_stock BOOLEAN DEFAULT FALSE;

INSERT INTO Producto (nombre, tipo, marca, precio, stock, hay_stock, fecha_registro)
VALUES ('Llanta Rin 15', 'Llanta', 'Michelin', 120.50, 10, TRUE, DATE('2025-01-05')),
       ('Llanta Rin 17', 'Llanta', 'Goodyear', 150.75, 5, TRUE, DATE('2025-02-12')),
       ('Batería 12V', 'Batería', 'Bosch', 95.00, 8, TRUE, DATE('2025-03-20')),
       ('Filtro de aceite', 'Filtro', 'Mann', 12.99, 20, TRUE, DATE('2025-04-01')),
       ('Bujías (set de 4)', 'Bujía', 'NGK', 35.50, 15, TRUE, DATE('2025-05-17')),
       ('Líquido de frenos', 'Fluido', 'Bosch', 18.75, 12, TRUE, DATE('2025-06-23')),
       ('Filtro de aire', 'Filtro', 'Fram', 9.99, 25, TRUE, DATE('2025-07-08')),
       ('Amortiguador delantero', 'Suspensión', 'Monroe', 85.20, 7, TRUE, DATE('2025-08-19')),
       ('Llanta Rin 16', 'Llanta', 'Pirelli', 140.00, 0, FALSE, DATE('2025-09-27')),
       ('Aceite de motor', 'Aceite', 'Castrol', 25.00, 0, FALSE, DATE('2025-10-30'));

SELECT * FROM Producto;

-- Opcional: Si quieres actualizar los datos existentes para reflejar el stock
UPDATE Producto
SET hay_stock = stock > 0
WHERE id_producto > 0;

SELECT
    nombre,
    tipo,
    marca,
    precio,
    stock,
    hay_stock,
    DATE_FORMAT(fecha_registro, '%d-%m-%Y') AS fecha_registro_formateada
FROM
    Producto;