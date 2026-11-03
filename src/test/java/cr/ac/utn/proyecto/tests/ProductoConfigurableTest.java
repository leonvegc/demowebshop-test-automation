package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CartPage;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.ProductPage;

public class ProductoConfigurableTest extends BaseTest {

    // Entra a la pagina de "Build your own computer" (esta en Computers > Desktops)
    private ProductPage abrirComputadora() {
        return new HomePage(driver)
                .abrir()
                .irACategoria("Computers")
                .irASubcategoria("Desktops")
                .abrirProducto("Build your own computer");
    }

    @Test
    @DisplayName("CP-051 Agregar la computadora sin elegir el disco duro")
    public void agregarSinElegirDisco() {
        // Procesador y RAM ya vienen elegidos, pero el disco duro (HDD) es obligatorio y no
        ProductPage producto = abrirComputadora()
                .agregarAlCarrito();

        assertEquals("Please select HDD", producto.obtenerMensajeAviso());
        assertEquals(0, new HomePage(driver).obtenerCantidadCarrito());
    }

    @Test
    @DisplayName("CP-052 Agregar la computadora con las opciones obligatorias")
    public void agregarConOpcionesObligatorias() {
        ProductPage producto = abrirComputadora()
                .elegirDisco("320 GB")
                .agregarAlCarrito();

        assertEquals("The product has been added to your shopping cart", producto.obtenerMensajeAviso());

        HomePage inicio = new HomePage(driver);
        assertEquals(1, inicio.obtenerCantidadCarrito());
        assertTrue(inicio.irAlCarrito().obtenerNombres().contains("Build your own computer"));
    }

    @Test
    @DisplayName("CP-053 El precio del carrito refleja las opciones elegidas")
    public void precioDelCarritoConOpciones() {
        ProductPage producto = abrirComputadora()
                .elegirRam("8GB [+60.00]")
                .elegirDisco("400 GB");

        // El precio que muestra la pagina es el precio base, sin opciones
        double precioBase = producto.obtenerPrecio();
        producto.agregarAlCarrito();

        CartPage carrito = new HomePage(driver).irAlCarrito();

        // En el carrito el precio ya incluye lo que cuestan la RAM y el disco elegidos
        assertTrue(carrito.obtenerPrecioUnitario() > precioBase);
    }
}
