package cr.ac.utn.proyecto.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cr.ac.utn.proyecto.base.Esperas;

public class ComparePage {
    private WebDriver driver;

    // Enlaces con el nombre de cada producto en la tabla de comparacion
    @FindBy(css = ".compare-products-table .product-name a")
    private List<WebElement> nombresProductos;

    // Boton "Remove" del primer producto de la tabla
    @FindBy(css = ".compare-products-table .remove-button")
    @CacheLookup
    private WebElement botonQuitar;

    // Enlace "Clear list" para vaciar toda la tabla
    @FindBy(css = ".clear-list")
    @CacheLookup
    private WebElement enlaceLimpiar;

    // Cuando no hay productos, aqui sale "You have no items to compare."
    @FindBy(css = ".compare-products-page .page-body")
    private WebElement cuerpoPagina;

    public ComparePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> obtenerNombres() {
        List<String> nombres = new ArrayList<>();
        for (WebElement nombre : nombresProductos) {
            nombres.add(nombre.getText());
        }
        return nombres;
    }

    public int obtenerCantidadDeProductos() {
        return nombresProductos.size();
    }

    public ComparePage quitarProducto() {
        Esperas.clicYEsperarRecarga(driver, botonQuitar);
        return new ComparePage(driver);
    }

    public ComparePage limpiarLista() {
        Esperas.clicYEsperarRecarga(driver, enlaceLimpiar);
        return new ComparePage(driver);
    }

    public String obtenerMensajeListaVacia() {
        return cuerpoPagina.getText().trim();
    }
}
