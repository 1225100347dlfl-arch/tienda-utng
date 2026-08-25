-- Esquema de la base de datos utng_tienda
-- Ejecutar en MySQL antes de correr la aplicacion.

CREATE DATABASE IF NOT EXISTS utng_tienda
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE utng_tienda;

CREATE TABLE IF NOT EXISTS producto (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(120)   NOT NULL,
    descripcion VARCHAR(255),
    precio      DECIMAL(10,2)  NOT NULL,
    stock       INT            NOT NULL DEFAULT 0
);
