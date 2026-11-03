package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CartPage;
import cr.ac.utn.proyecto.pages.HomePage;

public class CarritoTest extends BaseTest {

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