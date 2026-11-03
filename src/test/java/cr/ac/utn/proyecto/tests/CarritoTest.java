package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CartPage;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.ProductPage;

public class CarritoTest extends BaseTest {

    @Test

    @DisplayName("CP-040 Agregar un producto desde su página")
    public void agregarDesdeLaPaginaDelProducto() {
        ProductPage producto = new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .abrirProducto("Health Book")
                .agregarAlCarrito();

        assertEquals("The product has been added to your shopping cart", producto.obtenerMensajeAviso());

        // El contador del encabezado sube a 1 y el producto aparece en el carrito
        HomePage inicio = new HomePage(driver);
        assertEquals(1, inicio.obtenerCantidadCarrito());
        assertTrue(inicio.irAlCarrito().obtenerNombres().contains("Health Book"));
    }
    
    @Test
    @DisplayName("CP-044 Carrito vacío muestra un mensaje")
    public void carritoVacio() {
        CartPage carrito = new HomePage(driver)
                .abrir()
                .irAlCarrito();

        assertEquals("Your Shopping Cart is empty!", carrito.obtenerMensajeCarritoVacio());
        assertEquals(0, carrito.obtenerCantidadDeFilas());
    }
}