package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CartPage;
import cr.ac.utn.proyecto.pages.HomePage;

public class CheckoutTest extends BaseTest {

    @Test
    @DisplayName("CP-063 Intentar ir al checkout sin aceptar los términos de servicio")
    public void intentarCheckoutSinTerminos() {
        new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .agregarAlCarrito("Health Book");

        CartPage carrito = new HomePage(driver)
                .irAlCarrito()
                .intentarCheckoutSinTerminos();

        assertEquals("Please accept the terms of service before the next step.", carrito.obtenerAvisoTerminos());
    }
}
