# ISW-622 · Proyecto de Automatización de Pruebas (Demo Web Shop)

Sitio bajo prueba: https://demowebshop.tricentis.com

## Integrantes del grupo

| Integrante | Nombre | Requerimientos asignados | Casos de prueba |
|---|---|---|---|
| 1 | Christopher | RF-01 Registro de usuario, RF-02 Inicio y cierre de sesión | CP-001 a CP-012 |
| 2 | Jeremy Mendoza Ramírez | RF-03 Búsqueda de productos, RF-04 Catálogo (categorías, orden y paginación) | CP-020 a CP-035 |
| 3 | Stef | RF-05 Carrito de compras, RF-06 Productos con opciones | CP-040 a CP-054 |
| 4 | Josue Vargas | RF-08 Comparación de productos, RF-09 Proceso de compra (checkout) | CP-060 a CP-067 |

## Estructura del proyecto

```
src/test/java/cr/ac/utn/proyecto/
├── base/
│   ├── BaseTest.java      # Abre un Chrome limpio antes de cada prueba y lo cierra al terminar
│   ├── DatosPrueba.java   # Genera correos únicos con la hora en milisegundos
│   ├── Esperas.java       # Esperas explícitas (WebDriverWait) para usar dentro de las páginas
│   └── Visual.java        # Pausa opcional solo para observar (-Dritmo)
├── pages/
│   ├── HomePage.java           # Encabezado, buscador, menú de categorías y contador del carrito
│   ├── RegisterPage.java       # Formulario de registro de usuario (RF-01)
│   ├── LoginPage.java          # Formulario de inicio de sesión (RF-02)
│   ├── SearchPage.java         # Búsqueda simple y búsqueda avanzada (RF-03)
│   ├── CategoryPage.java       # Listado por categoría, subcategorías, orden, cantidad y vista (RF-04)
│   ├── ProductPage.java        # Página de producto, opciones configurables y agregar a comparar/carrito (RF-05, RF-06, RF-08)
│   ├── CartPage.java           # Carrito de compras, cantidades, subtotal y términos de servicio (RF-05, RF-09)
│   ├── ComparePage.java        # Tabla de comparación de productos (RF-08)
│   ├── CheckoutPage.java       # Flujo de compra de varios pasos con usuario registrado o invitado (RF-09)
│   └── OrderCompletedPage.java # Confirmación de orden procesada (RF-09)
└── tests/
    ├── RegistroTest.java             # Pruebas de RF-01 (CP-001, CP-003, CP-004, CP-006)
    ├── LoginTest.java                # Pruebas de RF-02 (CP-007, CP-008, CP-009)
    ├── BusquedaTest.java             # Pruebas de RF-03 (CP-020 a CP-024, CP-026 a CP-029)
    ├── CatalogoTest.java             # Pruebas de RF-04 (CP-030 a CP-035)
    ├── CarritoTest.java              # Pruebas de RF-05 (CP-040 a CP-044, CP-049, CP-050)
    ├── CantidadesTest.java           # Pruebas negativas y de límite de RF-05 (CP-045 a CP-048)
    ├── ProductoConfigurableTest.java # Pruebas de RF-06 (CP-051 a CP-053)
    ├── ComparacionTest.java          # Pruebas de RF-08 (CP-060 a CP-062)
    └── CheckoutTest.java             # Pruebas de RF-09 (CP-063 a CP-066)
```

## Requisitos previos

- **Java 17** (o superior)
- **Apache Maven 3.8+**
- **Google Chrome / Chromium**

## Cómo ejecutar las pruebas

Desde la carpeta raíz del proyecto (donde está `pom.xml`):

```bash
# Ejecutar todas las pruebas automatizadas (43 pruebas)
mvn test

# Ejecutar sin pausas visuales
mvn test -Dritmo=0

# Ejecutar en modo sin ventana (headless)
mvn test -Dheadless=true -Dritmo=0

# Ejecutar una clase de prueba específica
mvn test -Dtest=CheckoutTest -Dritmo=0
mvn test -Dtest=ComparacionTest -Dritmo=0
```

Los reportes de ejecución de Surefire se generan automáticamente en `target/surefire-reports/`.
