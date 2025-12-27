CREATE DATABASE IF NOT EXISTS trabajo_sge;
USE trabajo_sge;

CREATE TABLE roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(30),
  descripcion VARCHAR (60)
);

CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(30),
  apellidos VARCHAR (60),
  usuario VARCHAR (30),
  contraseña VARCHAR (60),
  rol_id INT,
  FOREIGN KEY (rol_id) REFERENCES roles(id)
);

CREATE TABLE proyectos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(30),
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_ej_inicio DATE,
  fecha_ej_final DATE,
  tipo_proyecto ENUM("A"),
  activo boolean,
  calificacion ENUM ("IT","I+D","I+D+i","I+D+IT"),
  bajada_calificacion boolean default false,
  en_cooperacion boolean,
  fases integer,
  usuarios_id INT,
  FOREIGN KEY (usuarios_id) REFERENCES usuarios(id)
);