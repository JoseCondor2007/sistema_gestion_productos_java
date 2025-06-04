-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS dbVulcanizadoraJosesito
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;

-- Seleccionar la base de datos para trabajar en ella
USE dbVulcanizadoraJosesito;

-- Creación de las tablas
CREATE TABLE Cliente (
    id_cliente INTEGER PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(60) NOT NULL,
    direccion VARCHAR(255),
    celular VARCHAR(9),
    correo_electronico VARCHAR(255),
    tipo_de_documento VARCHAR(3),
    numero_de_documento VARCHAR(20) UNIQUE,
    fecha DATE,
    estado BOOLEAN DEFAULT TRUE
);
ALTER TABLE Cliente
DROP COLUMN fecha_registro;

CREATE TABLE Vehiculo (
    id_vehiculo INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_cliente INTEGER,
    marca VARCHAR(50),
    tipo VARCHAR(50),
    placa VARCHAR(20) UNIQUE
);

CREATE TABLE Servicio (
    id_servicio INTEGER PRIMARY KEY AUTO_INCREMENT,
    tipo_servicio VARCHAR(50),
    descripcion TEXT(100),
    estado_vehiculo BOOLEAN,
    fecha DATE,
    price NUMERIC(10, 2)
);

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

CREATE TABLE Orden_de_Servicio (
    id_orden INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_cliente INTEGER,
    id_vehiculo INTEGER,
    id_servicio INTEGER,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado CHAR(1) DEFAULT 'P' -- P: Pendiente, C: Completada, etc.
);

CREATE TABLE Venta (
    id_venta INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_cliente INTEGER,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2)
);

CREATE TABLE Detalle_de_venta (
    id_detalle_venta INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_venta INTEGER,
    id_producto INTEGER,
    cantidad INTEGER NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(10, 2) NOT NULL
);

-- Definición de las relaciones (claves foráneas)

ALTER TABLE Vehiculo
    ADD CONSTRAINT FK_Vehiculo_Cliente FOREIGN KEY (id_cliente)
    REFERENCES Cliente (id_cliente)
    ON UPDATE CASCADE
    ON DELETE CASCADE;

ALTER TABLE Orden_de_Servicio
    ADD CONSTRAINT FK_OrdenServicio_Cliente FOREIGN KEY (id_cliente)
    REFERENCES Cliente (id_cliente)
    ON UPDATE CASCADE
    ON DELETE CASCADE;

ALTER TABLE Orden_de_Servicio
    ADD CONSTRAINT FK_OrdenServicio_Vehiculo FOREIGN KEY (id_vehiculo)
    REFERENCES Vehiculo (id_vehiculo)
    ON UPDATE CASCADE
    ON DELETE CASCADE;

ALTER TABLE Orden_de_Servicio
    ADD CONSTRAINT FK_OrdenServicio_Servicio FOREIGN KEY (id_servicio)
    REFERENCES Servicio (id_servicio)
    ON UPDATE CASCADE
    ON DELETE CASCADE;

ALTER TABLE Venta
    ADD CONSTRAINT FK_Venta_Cliente FOREIGN KEY (id_cliente)
    REFERENCES Cliente (id_cliente)
    ON UPDATE CASCADE
    ON DELETE CASCADE;

ALTER TABLE Detalle_de_venta
    ADD CONSTRAINT FK_DetalleVenta_Venta FOREIGN KEY (id_venta)
    REFERENCES Venta (id_venta)
    ON UPDATE CASCADE
    ON DELETE CASCADE;

ALTER TABLE Detalle_de_venta
     ADD CONSTRAINT FK_DetalleVenta_Producto FOREIGN KEY (id_producto)
    REFERENCES Producto (id_producto)
    ON UPDATE CASCADE
    ON DELETE CASCADE;

-- Insertar datos 
INSERT INTO Cliente (nombre, apellido, tipo_de_documento, numero_de_documento, correo_electronico, celular, fecha_nacimiento, direccion)
VALUES ('Ricardo', 'Gómez', 'DNI', '87654321', 'ricardo.gomez@email.com', '912345678', '1988-07-20', 'Quilmaná'),
       ('Ana', 'Pérez', 'DNI', '12345678', 'ana.perez@email.com', '987654321', '1995-03-15', 'Imperial'),
       ('Carlos', 'Pérez', 'DNI', '55554444', 'carlos.perez@email.com', '96663333', '2002-11-01', 'Quilmaná'),
       ('Elena', 'Vargas', 'CE', '11223344', 'elena.vargas@email.com', '94445555', '1985-12-28', 'Lima'),
       ('Javier', 'Soto', 'DNI', '22334455', 'javier.soto@email.com', '93332222', '1991-06-05', 'Roldan'),
       ('Mariana', 'Rojas', 'PAS', '66778899', 'mariana.rojas@email.com', '97778888', '1998-09-22', 'Lima'),
       ('Luis', 'Castro', 'DNI', '99001122', 'luis.castro@email.com', '92221111', '1982-04-10', 'La Huerta'),
       ('Sofía', 'Díaz', 'CE', '33445566', 'sofia.diaz@email.com', '91110000', '2000-01-30', 'Quilmaná');
       
INSERT INTO Vehiculo (id_cliente, marca, tipo, placa)
VALUES (1, 'Toyota', 'Sedan', 'ABC-123'),
       (2, 'Nissan', 'SUV', 'DEF-456'),
       (1, 'Ford', 'Pickup', 'GHI-789'),
       (3, 'Mazda', 'Hatchback', 'JKL-012'),
       (4, 'BMW', 'Sedan', 'MNO-345'),
       (5, 'Chevrolet', 'Camioneta', 'PQR-678'),
       (6, 'Hyundai', 'Sedan', 'STU-901'),
       (7, 'Kia', 'SUV', 'VWX-234'),
       (8, 'Audi', 'Deportivo', 'YZA-567');

INSERT INTO Servicio (tipo_servicio, descripcion, price, fecha)
VALUES ('Cambio de llanta', 'Reemplazo de una llanta dañada', 50.00, '2025-05-10'),
       ('Alineación y balanceo', 'Ajuste de la dirección y equilibrio de las ruedas', 80.00, '2025-05-10'),
       ('Revisión de frenos', 'Inspección y ajuste del sistema de frenos', 60.00, '2025-05-09'),
       ('Cambio de aceite', 'Reemplazo del aceite del motor y filtro', 40.00, '2025-05-09'),
       ('Rotación de neumáticos', 'Cambio de posición de las llantas', 30.00, '2025-05-11'),
       ('Diagnóstico electrónico', 'Revisión de la computadora del vehículo', 75.00, '2025-05-11'),
       ('Reparación de suspensión', 'Arreglo de amortiguadores y otros componentes', 120.00, '2025-05-12'),
       ('Mantenimiento preventivo', 'Revisión general del vehículo', 90.00, '2025-05-12');

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
       
INSERT INTO Orden_de_Servicio (id_cliente, id_vehiculo, id_servicio)
VALUES (1, 1, 1),
       (2, 2, 2),
       (1, 3, 3),
       (3, 4, 1),
       (4, 5, 4),
       (5, 6, 5),
       (6, 7, 6),
       (7, 8, 7),
       (8, 9, 8);

INSERT INTO Venta (id_cliente, total)
VALUES (1, 170.50),
       (2, 230.75),
       (1, 100.00),
       (3, 55.00),
       (4, 95.00),
       (5, 30.00),
       (6, 120.00),
       (7, 85.20),
       (8, 250.00);

INSERT INTO Detalle_de_venta (id_venta, id_producto, cantidad, precio_unitario)
VALUES (1, 1, 1, 120.50),
       (1, 4, 2, 15.00),
       (2, 2, 1, 150.75),
       (3, 3, 1, 95.00),
       (4, 5, 4, 35.50),
       (5, 6, 1, 18.75),
       (6, 1, 1, 120.50),
       (6, 7, 2, 9.99),
       (7, 8, 2, 85.20),
       (8, 2, 1, 150.75),
       (8, 3, 1, 95.00);

-- Ejemplos de modificación de datos de clientes

-- Cambiar el número de celular 
UPDATE Cliente
SET celular = '911223344'
WHERE nombre = 'Ricardo' AND apellido = 'Gómez';

-- Actualizar la dirección y el correo electrónico 
UPDATE Cliente
SET direccion = 'Avenida Principal 123, Callao',
    correo_electronico = 'ana.perez.nueva@email.com'
WHERE nombre = 'Ana' AND apellido = 'Pérez';

-- Cambiar el estado a inactivo (estado = 0) del cliente 
UPDATE Cliente
SET estado = FALSE
WHERE nombre = 'Carlos' AND apellido = 'Pérez';

-- Modificar el tipo de documento y la fecha 
UPDATE Cliente
SET tipo_de_documento = 'DNI',
    fecha = '2025-05-15'
WHERE id_cliente = 6;

-- Ejemplos de eliminación de clientes

-- Eliminar a Javier Soto
DELETE FROM Cliente
WHERE nombre = 'Javier' AND apellido = 'Soto';

-- Eliminar usando su ID (asumiendo que su ID es 6, verifica con SELECT *)
DELETE FROM Cliente
WHERE id_cliente = 6;

-- Consultas para ver los datos después de las modificaciones y eliminaciones
SELECT * FROM Cliente;
SELECT * FROM Vehiculo;
SELECT * FROM Servicio;
SELECT * FROM Producto;
SELECT * FROM Orden_de_Servicio;
SELECT * FROM Venta;
SELECT * FROM Detalle_de_venta;
-- Opcional: Si quieres actualizar los datos existentes para reflejar el stock
UPDATE Producto
SET hay_stock = stock > 0
WHERE id_producto > 0;

ALTER TABLE Producto
MODIFY COLUMN fecha_registro DATE;

SELECT
    nombre,
    tipo,
    marca,
    precio,
    stock,
    hay_stock,
    DATE_FORMAT(fecha_registro, '%d-%m-%Y') AS fecha_registro
FROM
    Producto;