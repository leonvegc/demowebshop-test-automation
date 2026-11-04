package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cr.ac.utn.proyecto.base.Esperas;

public class OrderCompletedPage {
    private WebDriver driver;

    // Mensaje "Your order has been successfully processed!"
    @FindBy(css = ".order-completed .title")
    private WebElement tituloConfirmacion;

    // Primer elemento de la lista de detalles ("Order number: ...")
    @FindBy(css = ".order-completed .details li")
    private WebElement detalleOrden;

    public OrderCompletedPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String obtenerMensajeConfirmacion() {
        Esperas.esperarVisible(driver, tituloConfirmacion);
        return tituloConfirmacion.getText();
    }

    public String obtenerNumeroOrden() {
        return detalleOrden.getText();
    }
}
