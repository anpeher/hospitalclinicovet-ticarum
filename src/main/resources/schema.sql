CREATE TABLE IF NOT EXISTS mascota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    especie VARCHAR(100) NOT NULL,
    raza VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    codigo_identificacion VARCHAR(100) NOT NULL UNIQUE,
    dni_responsable VARCHAR(100) NOT NULL,
    activa BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS ingreso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_alta DATE NOT NULL,
    fecha_finalizacion DATE,
    estado VARCHAR(100) NOT NULL,
    mascota_id INT NOT NULL,
    dni_registrador VARCHAR(100) NOT NULL,
    FOREIGN KEY (mascota_id) REFERENCES mascota(id)
);