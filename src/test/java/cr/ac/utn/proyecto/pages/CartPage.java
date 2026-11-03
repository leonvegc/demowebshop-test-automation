package cr.ac.utn.proyecto.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cr.ac.utn.proyecto.base.Esperas;

public class CartPage {
    private WebDriver driver;

    // Una fila por cada producto del carrito
    @FindBy(css = ".cart-item-row")
    private List<WebElement> filas;

    @FindBy(css = ".cart-item-row .product-name")
    private List<WebElement> nombresProductos;

    // Datos de la primera fila (las pruebas usan un solo producto)
    @FindBy(css = ".cart-item-row .qty-input")
    private WebElement campoCantidad;

    @FindBy(css = ".cart-item-row .product-unit-price")
    private WebElement precioUnitario;

    @FindBy(css = ".cart-item-row .product-subtotal")
    private WebElement subtotalProducto;

    // Sub-Total de toda la compra. El "Total" no sirve: en los productos con envio
    // dice "Calculated during checkout" y el numero solo sale al final de la compra.
    @FindBy(css = ".cart-total .product-price")
    private WebElement subtotalCarrito;

    // @CacheLookup: se guarda el boton para saber cuando la pagina se recargo (ver Esperas)
    @FindBy(name = "updatecart")
    @CacheLookup
    private WebElement botonActualizar;

    // Con el carrito vacio, aqui sale el mensaje
    @FindBy(css = ".order-summary-content")
    private WebElement contenidoCarrito;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /*
      Escribe la nueva cantidad y hace clic en "Update shopping cart".
      La pagina se recarga, por eso se devuelve una CartPage nueva.
     */
    public CartPage cambiarCantidad(String cantidad) {
        campoCantidad.clear();
        campoCantidad.sendKeys(cantidad);
        Esperas.clicYEsperarRecarga(driver, botonActualizar);
        return new CartPage(driver);
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

    public double obtenerPrecioUnitario() {
        return Double.parseDouble(precioUnitario.getText());
    }

    public double obtenerSubtotalProducto() {
        return Double.parseDouble(subtotalProducto.getText());
    }

    public double obtenerSubtotalCarrito() {
        return Double.parseDouble(subtotalCarrito.getText());
    }

    public String obtenerMensajeCarritoVacio() {
        return contenidoCarrito.getText();
    }
}
