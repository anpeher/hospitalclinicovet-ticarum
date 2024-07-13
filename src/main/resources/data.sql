
INSERT INTO mascota (especie, raza, fecha_nacimiento, codigo_identificacion, dni_responsable, activa)
VALUES
('Perro', 'Labrador', '2020-04-10', '123ABC', '12345678A', TRUE),
('Gato', 'Siamés', '2016-04-02', '123ERT', '87654321B', TRUE);

INSERT INTO ingreso (fecha_alta, fecha_finalizacion, estado, mascota_id, dni_registrador)
VALUES
('2024-07-05', '2024-07-07', 'FINALIZADO', 1, '12345678A'),
('2024-07-11', NULL, 'HOSPITALIZACION', 2, '87654321B'),
('2024-07-10', '2024-07-15', 'HOSPITALIZACION', 1, '12345678A');

