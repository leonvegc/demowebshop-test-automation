package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.ProductPage;

public class CantidadesTest extends BaseTest {

    /*
      Entra a la pagina de un libro, escribe la cantidad y trata de agregarlo.
      Las cuatro pruebas de abajo usan este mismo recorrido.
     */
    private ProductPage agregarConCantidad(String cantidad) {
        return new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .abrirProducto("Health Book")
                .escribirCantidad(cantidad)
                .agregarAlCarrito();
    }

    @Test
    @DisplayName("CP-045 Agregar un producto con cantidad cero")
    public void agregarConCantidadCero() {
        ProductPage producto = agregarConCantidad("0");

        assertEquals("Quantity should be positive", producto.obtenerMensajeAviso());
        assertEquals(0, new HomePage(driver).obtenerCantidadCarrito());
    }

    @Test
    @DisplayName("CP-046 Agregar un producto con cantidad negativa")
    public void agregarConCantidadNegativa() {
        ProductPage producto = agregarConCantidad("-1");

        assertEquals("Quantity should be positive", producto.obtenerMensajeAviso());
        assertEquals(0, new HomePage(driver).obtenerCantidadCarrito());
    }

    @Test
    @DisplayName("CP-047 Agregar un producto con cantidad en texto")
    public void agregarConCantidadEnTexto() {
        ProductPage producto = agregarConCantidad("abc");

        assertEquals("Quantity should be positive", producto.obtenerMensajeAviso());
        assertEquals(0, new HomePage(driver).obtenerCantidadCarrito());
    }

    @Test
    @DisplayName("CP-048 Agregar un producto con cantidad mayor al máximo")
    public void agregarConCantidadMayorAlMaximo() {
        // El maximo permitido es 10000, entonces 10001 es el primer valor no valido
        ProductPage producto = agregarConCantidad("10001");

        assertEquals("The maximum quantity allowed for purchase is 10000.", producto.obtenerMensajeAviso());
        assertEquals(0, new HomePage(driver).obtenerCantidadCarrito());
    }
}
