package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.DatosPrueba;
import cr.ac.utn.proyecto.pages.CartPage;
import cr.ac.utn.proyecto.pages.CheckoutPage;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.OrderCompletedPage;

public class CheckoutTest extends BaseTest {

    // Crea un usuario nuevo con correo unico y agrega Health Book al carrito
    private HomePage registrarYAgregarLibro() {
        String correo = DatosPrueba.correoUnico();
        new HomePage(driver)
                .abrir()
                .irARegistro()
                .registrar("Test", "QA", correo, "123abc", "123abc");

        new HomePage(driver)
                .irACategoria("Books")
                .agregarAlCarrito("Health Book");

        return new HomePage(driver);
    }

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

    @Test
    @DisplayName("CP-064 Compra completa con usuario registrado")
    public void compraCompletaConUsuarioRegistrado() {
        OrderCompletedPage orden = registrarYAgregarLibro()
                .irAlCarrito()
                .aceptarTerminos()
                .irAlCheckout()
                .completarDireccionFacturacion("Costa Rica", "Alajuela", "Calle Central", "20101", "88888888")
                .continuarDireccionEnvio()
                .continuarMetodoEnvio()
                .continuarMetodoPago()
                .continuarInformacionPago()
                .confirmarOrden();

        assertEquals("Your order has been successfully processed!", orden.obtenerMensajeConfirmacion());
        assertTrue(orden.obtenerNumeroOrden().contains("Order number:"));
    }

    @Test
    @DisplayName("CP-065 Continuar en facturación con campos obligatorios vacíos")
    public void continuarFacturacionConCamposVacios() {
        CheckoutPage checkout = registrarYAgregarLibro()
                .irAlCarrito()
                .aceptarTerminos()
                .irAlCheckout()
                .intentarContinuarFacturacionVacia();

        assertEquals("Country is required.", checkout.obtenerErrorPais());
        assertEquals("City is required", checkout.obtenerErrorCiudad());
        assertEquals("Street address is required", checkout.obtenerErrorDireccion());
        assertEquals("Zip / postal code is required", checkout.obtenerErrorCodigoPostal());
        assertEquals("Phone is required", checkout.obtenerErrorTelefono());
    }

    @Test
    @DisplayName("CP-066 Compra completa como invitado")
    public void compraCompletaComoInvitado() {
        new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .agregarAlCarrito("Health Book");

        OrderCompletedPage orden = new HomePage(driver)
                .irAlCarrito()
                .aceptarTerminos()
                .irAlCheckout()
                .continuarComoInvitado()
                .completarDireccionInvitado("Test", "QA", DatosPrueba.correoUnico(),
                        "Costa Rica", "Alajuela", "Calle Central", "20101", "88888888")
                .continuarDireccionEnvio()
                .continuarMetodoEnvio()
                .continuarMetodoPago()
                .continuarInformacionPago()
                .confirmarOrden();

        assertEquals("Your order has been successfully processed!", orden.obtenerMensajeConfirmacion());
        assertTrue(orden.obtenerNumeroOrden().contains("Order number:"));
    }
}
