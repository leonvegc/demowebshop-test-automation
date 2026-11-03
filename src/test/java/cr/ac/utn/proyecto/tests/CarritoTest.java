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

    /*
      Agrega el libro Health Book al carrito desde el listado.
      Asi cada prueba empieza con un producto en el carrito y no depende de otras pruebas.
     */
    private void agregarLibroAlCarrito() {
        new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .agregarAlCarrito("Health Book");
    }

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
    @DisplayName("CP-041 Agregar un producto desde el listado")
    public void agregarDesdeElListado() {
        new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .agregarAlCarrito("Health Book");

        HomePage inicio = new HomePage(driver);
        assertEquals(1, inicio.obtenerCantidadCarrito());
        assertTrue(inicio.irAlCarrito().obtenerNombres().contains("Health Book"));
    }

    @Test
    @DisplayName("CP-042 Cambiar la cantidad y recalcular el total")
    public void cambiarCantidadYRecalcular() {
        agregarLibroAlCarrito();

        // Se abre el carrito y se cambia la cantidad de 1 a 3
        CartPage carrito = new HomePage(driver)
                .irAlCarrito()
                .cambiarCantidad("3");

        // El subtotal tiene que ser 3 veces el precio de un producto
        double esperado = carrito.obtenerPrecioUnitario() * 3;
        assertEquals(esperado, carrito.obtenerSubtotalProducto(), 0.01);
        assertEquals(esperado, carrito.obtenerSubtotalCarrito(), 0.01);
        assertEquals(3, new HomePage(driver).obtenerCantidadCarrito());
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
