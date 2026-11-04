package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import cr.ac.utn.proyecto.base.Esperas;

public class CheckoutPage {
    private WebDriver driver;

    // Campos de la direccion de facturacion (Billing Address)
    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement listaPais;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement campoCiudad;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement campoDireccion;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement campoCodigoPostal;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement campoTelefono;

    // Botones Continue de cada paso del checkout
    @FindBy(css = "#billing-buttons-container .new-address-next-step-button")
    private WebElement botonContinuarFacturacion;

    @FindBy(css = "#shipping-buttons-container .new-address-next-step-button")
    private WebElement botonContinuarEnvio;

    @FindBy(css = ".shipping-method-next-step-button")
    private WebElement botonContinuarMetodoEnvio;

    @FindBy(css = ".payment-method-next-step-button")
    private WebElement botonContinuarMetodoPago;

    @FindBy(css = ".payment-info-next-step-button")
    private WebElement botonContinuarInfoPago;

    @FindBy(css = ".confirm-order-next-step-button")
    private WebElement botonConfirmarOrden;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /*
      Llena los datos de la direccion de facturacion y avanza al paso de envio.
      Con usuario registrado el nombre, apellido y correo ya vienen llenos.
     */
    public CheckoutPage completarDireccionFacturacion(String pais, String ciudad, String direccion,
                                                      String codigoPostal, String telefono) {
        Esperas.esperarVisible(driver, listaPais);
        new Select(listaPais).selectByVisibleText(pais);
        campoCiudad.clear();
        campoCiudad.sendKeys(ciudad);
        campoDireccion.clear();
        campoDireccion.sendKeys(direccion);
        campoCodigoPostal.clear();
        campoCodigoPostal.sendKeys(codigoPostal);
        campoTelefono.clear();
        campoTelefono.sendKeys(telefono);
        botonContinuarFacturacion.click();
        return this;
    }

    // Cada paso del checkout se carga con ajax, por eso se espera a que el boton sea visible
    public CheckoutPage continuarDireccionEnvio() {
        Esperas.esperarVisible(driver, botonContinuarEnvio);
        botonContinuarEnvio.click();
        return this;
    }

    public CheckoutPage continuarMetodoEnvio() {
        Esperas.esperarVisible(driver, botonContinuarMetodoEnvio);
        botonContinuarMetodoEnvio.click();
        return this;
    }

    public CheckoutPage continuarMetodoPago() {
        Esperas.esperarVisible(driver, botonContinuarMetodoPago);
        botonContinuarMetodoPago.click();
        return this;
    }

    public CheckoutPage continuarInformacionPago() {
        Esperas.esperarVisible(driver, botonContinuarInfoPago);
        botonContinuarInfoPago.click();
        return this;
    }

    public OrderCompletedPage confirmarOrden() {
        Esperas.esperarVisible(driver, botonConfirmarOrden);
        botonConfirmarOrden.click();
        return new OrderCompletedPage(driver);
    }
}
