package cr.ac.utn.proyecto.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    private WebDriver driver;

    // Una fila por cada producto del carrito
    @FindBy(css = ".cart-item-row")
    private List<WebElement> filas;

    @FindBy(css = ".cart-item-row .product-name")
    private List<WebElement> nombresProductos;

    // Con el carrito vacio, aqui sale el mensaje
    @FindBy(css = ".order-summary-content")
    private WebElement contenidoCarrito;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int obtenerCantidadDeFilas() {
        return filas.size();
    }

    public List<String> obtenerNombres() {
        List<String> nombres = new ArrayList<>();
        for (WebElement nombre : nombresProductos) {
            nombres.add(nombre.getText());
        }
        return nombres;
    }

    public String obtenerMensajeCarritoVacio() {
        return contenidoCarrito.getText();
    }
}