# Tienda UTNG

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-JDBC-blue?logo=mysql)
![JUnit5](https://img.shields.io/badge/tests-JUnit%205-25A162?logo=junit5)
![Coverage](https://img.shields.io/badge/coverage-%3E%3D70%25-brightgreen)
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

## Pruebas

El proyecto cuenta con una suite de **12 pruebas unitarias con JUnit 5**
(4 en `ProductoTest` y 8 en `ProductoServiceTest`), aplicando el patron
**AAA** (Arrange-Act-Assert) y un **doble de prueba** (`ProductoDAOMemoria`,
un stub en memoria con `ArrayList`) para probar `ProductoService` sin
depender de MySQL.

El metodo `calcularTotalConDescuento` se desarrollo con **TDD**
(RED → GREEN → REFACTOR): primero se escribio el test que exige que un
precio de 100 con 10% de descuento resulte en 90.0, luego se implemento
el metodo minimo para pasarlo, y despues se agregaron las validaciones
(producto nulo, descuento fuera del rango 0–1) con sus respectivos tests.

**Cobertura alcanzada:** >= 70% de líneas en `ProductoService` (ver
`cobertura.png` en la raíz del repositorio, generado con JaCoCo).

### Como ejecutar las pruebas

```bash
mvn test
```

Esto compila el proyecto, corre las 12 pruebas con JUnit 5 y genera el
reporte de cobertura con JaCoCo en `target/site/jacoco/index.html`
(ábrelo en el navegador para ver el detalle linea por linea).

## Flujo de control de versiones

Este repositorio se versiono siguiendo **Conventional Commits**, con una
rama `feature/documentacion` integrada mediante Pull Request.

## Autor

Estudiante UTNG — 3er Cuatrimestre — Programacion Orientada a Objetos
