USE plataforma_sge;

INSERT INTO roles (nombre, descripcion)
VALUES ("Administrador","Encargado de poder manipular las opciones dentro de la app");

INSERT INTO usuarios (nombre, apellidos,usuario, contraseña, rol_id)
VALUES ("prueba","prueba","prueba","prueba",1);

INSERT INTO roles (nombre, descripcion)
VALUES ("Administrador","Encargado de poder manipular las opciones dentro de la app"),
("Lectura","Usuario con permisos solo de lectura."),
("Escritura","Usuario con permisos de lectura y escritura.");
