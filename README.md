# Tienda UTNG

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-JDBC-blue?logo=mysql)
![License](https://img.shields.io/badge/license-MIT-green)
![Status](https://img.shields.io/badge/status-en%20desarrollo-yellow)

Proyecto integrador de la Unidad III de **Programacion Orientada a Objetos**
(UTNG). Implementa un CRUD de productos sobre MySQL usando **JDBC**, con
arquitectura por capas (modelo, conexion, DAO, servicio) aplicando
**Clean Code**, principios **SOLID** y documentacion **Javadoc**.

## Arquitectura

```
mx.utng.tienda
├── modelo      -> Producto (entidad de dominio)
├── conexion    -> ConexionDB (manejo de la conexion JDBC)
├── dao         -> ProductoDAO (contrato) y su implementacion JDBC
├── servicio    -> ProductoService (logica de negocio y validaciones)
└── Main        -> demo del CRUD completo
```

## Requisitos

- Java 17+
- MySQL 8+
- Driver JDBC de MySQL (`mysql-connector-j`) en el classpath

## Configuracion

1. Ejecuta `db/schema.sql` en tu servidor MySQL para crear la base
   `utng_tienda` y la tabla `producto`.
2. Copia `src/main/resources/db.properties.example` como
   `src/main/resources/db.properties` y coloca tus credenciales locales.
   Este archivo **no se sube a GitHub** (ver `.gitignore`).
3. Compila y ejecuta `Main.java` para ver la demo de registrar, listar,
   actualizar y eliminar un producto.

## Principios aplicados

- **SOLID**: `ProductoService` depende de la interface `ProductoDAO`
  (inversion de dependencias), no de una implementacion concreta.
- **Clean Code**: nombres descriptivos en español consistente, metodos
  cortos con una sola responsabilidad, uso de `try-with-resources`.
- **Seguridad**: todas las consultas usan `PreparedStatement`; ninguna
  credencial esta escrita en el codigo fuente.

## Flujo de control de versiones

Este repositorio se versiono siguiendo **Conventional Commits**, con una
rama `feature/documentacion` integrada mediante Pull Request.

## Autor

Estudiante UTNG — 3er Cuatrimestre — Programacion Orientada a Objetos
