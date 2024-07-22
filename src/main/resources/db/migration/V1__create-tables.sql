
CREATE TABLE tbl_usuarios(
                         id SERIAL PRIMARY KEY,
                         gmail VARCHAR(100) NOT NULL UNIQUE,
                         username VARCHAR(100) NOT NULL UNIQUE,
                         password VARCHAR(100) NOT NULL,
                         role VARCHAR(5) NOT NULL CHECK (role IN ('ADMIN', 'USER'))
);
CREATE TABLE tbl_medicos(
                        id SERIAL PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        telefono VARCHAR(20) NOT NULL,
                        documento VARCHAR(16) NOT NULL UNIQUE,
                        especialidad VARCHAR(100) NOT NULL,
                        calle VARCHAR(100) NOT NULL,
                        numero VARCHAR(20),
                        complemento VARCHAR(100),
                        postal VARCHAR(20),
                        ciudad VARCHAR(100) NOT NULL,
                        estado VARCHAR(2) NOT NULL CHECK (estado IN ('PR', 'PS', 'MG')),
                        activo BOOLEAN
);

CREATE TABLE tbl_pacientes(
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          telefono VARCHAR(20) NOT NULL,
                          documento VARCHAR(16) NOT NULL UNIQUE,
                          calle VARCHAR(100) NOT NULL,
                          numero VARCHAR(20),
                          complemento VARCHAR(100),
                          postal VARCHAR(20),
                          ciudad VARCHAR(100) NOT NULL,
                          estado VARCHAR(2) NOT NULL CHECK (estado IN ('PR', 'PS', 'MG'))
);

CREATE TABLE tbl_consultas(
                          id SERIAL PRIMARY KEY,
                          fecha TIMESTAMP NOT NULL,
                          motivo_cancelamiento VARCHAR(20) CHECK (motivo_cancelamiento IN ('PACIENTE_DESISTIO', 'MEDICO_CANCELO', 'OTROS')),
                          estado BOOLEAN NOT NULL,
                          medico_id INTEGER,
                          paciente_id INTEGER,
                          FOREIGN KEY (medico_id) REFERENCES tbl_medicos(id),
                          FOREIGN KEY (paciente_id) REFERENCES tbl_pacientes(id)
);


