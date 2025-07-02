-- BaseDatos.sql
-- Script de creación de esquema y datos para microservicios DEVSU

-- Tabla Persona (servicio cliente_persona)
CREATE TABLE IF NOT EXISTS persona (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(50),
    age INTEGER,
    identification VARCHAR(50) UNIQUE NOT NULL,
    address VARCHAR(500),
    telephone VARCHAR(20)
);

-- Tabla Cliente (hereda de Persona - servicio cliente_persona)
CREATE TABLE IF NOT EXISTS cliente (
    id BIGSERIAL PRIMARY KEY,
    person_id BIGINT NOT NULL,
    password VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (person_id) REFERENCES persona(id) ON DELETE CASCADE,
    UNIQUE(person_id)
);

-- Tabla Cuenta (servicio cuenta_movimiento)
CREATE TABLE IF NOT EXISTS cuenta (
    id BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(50) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    cliente_id BIGINT NOT NULL
);

-- Tabla Movimiento (servicio cuenta_movimiento)
CREATE TABLE IF NOT EXISTS movimiento (
    id BIGSERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    cuenta_id BIGINT NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(id) ON DELETE CASCADE
);

-- Insertar Personas
INSERT INTO persona (name, gender, age, identification, address, telephone) VALUES
('Jose Lema', 'Masculino', 35, '1234567890', 'Otavalo sn y principal', '098254785'),
('Marianela Montalvo', 'Femenino', 28, '0987654321', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'Masculino', 42, '1122334455', '13 junio y Equinoccial', '098874587')
ON CONFLICT (identification) DO NOTHING;

-- Insertar Clientes
INSERT INTO cliente (person_id, password, status) VALUES
((SELECT id FROM persona WHERE identification = '1234567890'), '1234', TRUE),
((SELECT id FROM persona WHERE identification = '0987654321'), '5678', TRUE),
((SELECT id FROM persona WHERE identification = '1122334455'), '1245', TRUE)
ON CONFLICT (person_id) DO NOTHING;

-- Insertar Cuentas
INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES
('478758', 'Ahorros', 2000.00, TRUE, (SELECT c.id FROM cliente c JOIN persona p ON c.person_id = p.id WHERE p.identification = '1234567890')),
('225487', 'Corriente', 100.00, TRUE, (SELECT c.id FROM cliente c JOIN persona p ON c.person_id = p.id WHERE p.identification = '0987654321')),
('495878', 'Ahorros', 0.00, TRUE, (SELECT c.id FROM cliente c JOIN persona p ON c.person_id = p.id WHERE p.identification = '1122334455')),
('496825', 'Ahorros', 540.00, TRUE, (SELECT c.id FROM cliente c JOIN persona p ON c.person_id = p.id WHERE p.identification = '0987654321')),
('585545', 'Corriente', 1000.00, TRUE, (SELECT c.id FROM cliente c JOIN persona p ON c.person_id = p.id WHERE p.identification = '1234567890'))
ON CONFLICT (numero_cuenta) DO NOTHING;

-- Insertar Movimientos según los casos de uso de la prueba
INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES
-- Retiro de 575 en cuenta 478758 (Jose Lema - Ahorros)
('2022-02-10', 'Retiro', -575.00, 1425.00, (SELECT id FROM cuenta WHERE numero_cuenta = '478758')),

-- Deposito de 600 en cuenta 225487 (Marianela Montalvo - Corriente)
('2022-02-10', 'Deposito', 600.00, 700.00, (SELECT id FROM cuenta WHERE numero_cuenta = '225487')),

-- Deposito de 150 en cuenta 495878 (Juan Osorio - Ahorros)
('2022-02-08', 'Deposito', 150.00, 150.00, (SELECT id FROM cuenta WHERE numero_cuenta = '495878')),

-- Retiro de 540 en cuenta 496825 (Marianela Montalvo - Ahorros)
('2022-02-08', 'Retiro', -540.00, 0.00, (SELECT id FROM cuenta WHERE numero_cuenta = '496825'))
ON CONFLICT DO NOTHING;


CREATE INDEX IF NOT EXISTS idx_persona_identification ON persona(identification);
CREATE INDEX IF NOT EXISTS idx_cliente_person_id ON cliente(person_id);
CREATE INDEX IF NOT EXISTS idx_cuenta_numero ON cuenta(numero_cuenta);
CREATE INDEX IF NOT EXISTS idx_cuenta_cliente_id ON cuenta(cliente_id);
CREATE INDEX IF NOT EXISTS idx_movimiento_cuenta_id ON movimiento(cuenta_id);
CREATE INDEX IF NOT EXISTS idx_movimiento_fecha ON movimiento(fecha);

-- Verificar personas
SELECT 'PERSONAS INSERTADAS:' as info;
SELECT * FROM persona;

-- Verificar clientes
SELECT 'CLIENTES INSERTADOS:' as info;
SELECT c.id, p.name, c.password, c.status 
FROM cliente c 
JOIN persona p ON c.person_id = p.id;

-- Verificar cuentas
SELECT 'CUENTAS INSERTADAS:' as info;
SELECT cu.numero_cuenta, cu.tipo_cuenta, cu.saldo_inicial, cu.estado, p.name as cliente
FROM cuenta cu
JOIN cliente c ON cu.cliente_id = c.id
JOIN persona p ON c.person_id = p.id;

-- Verificar movimientos
SELECT 'MOVIMIENTOS INSERTADOS:' as info;
SELECT m.fecha, p.name as cliente, cu.numero_cuenta, cu.tipo_cuenta, 
       cu.saldo_inicial, m.tipo_movimiento, m.valor, m.saldo as saldo_disponible
FROM movimiento m
JOIN cuenta cu ON m.cuenta_id = cu.id
JOIN cliente c ON cu.cliente_id = c.id
JOIN persona p ON c.person_id = p.id
ORDER BY m.fecha DESC, p.name;