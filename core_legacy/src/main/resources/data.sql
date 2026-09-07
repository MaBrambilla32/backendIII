INSERT INTO cuentas (cuenta_id, nombre, saldo, edad, tipo) VALUES
(101, 'John Doe', 5000, 30, 'ahorro'),
(102, 'Jane Smith', 8000, 25, 'prestamo'),
(103, 'Bob Johnson', 12000, 30, 'prestamo'),
(104, 'Alice Brown', 0, 45, 'ahorro'),
(105, 'Charlie Green', 7000, 35, 'hipoteca'),
(106, 'John Doe', 5000, 30, 'ahorro'),
(107, 'Diana Prince', 15000, 40, 'prestamo'),
(108, 'Steve Rogers', 10000, 80, 'ahorro');

INSERT INTO transacciones (id, fecha, monto, tipo) VALUES
(1, '2024-01-01', 1000, 'debito'),
(2, '2024-01-02', 1500, 'credito'),
(3, '2024-01-03', -200, 'debito'),
(4, '2024-01-03', 0, 'debito'),
(5, '2024-01-04', 800, 'credito'),
(6, '2024-01-05', 700, 'debito'),
(7, '2024-01-06', 1200, 'credito'),
(8, '2024-01-05', 700, 'debito'),
(9, '2024-01-07', 3000, 'debito'),
(10, '2024-01-08', 1000, 'credito');