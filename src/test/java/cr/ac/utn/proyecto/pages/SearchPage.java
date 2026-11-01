package cr.ac.utn.proyecto.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import cr.ac.utn.proyecto.base.BaseTest;

public class SearchPage {
    private WebDriver driver;

    // Formulario de busqueda
    @FindBy(id = "Q")
    private WebElement campoTexto;

    @FindBy(css = ".search-button")
    private WebElement botonBuscar;

    // Resultados: son listas porque puede haber varios (o ninguno)
    @FindBy(css = ".product-item .product-title a")
    private List<WebElement> titulosProductos;

    // Mensaje cuando no hay productos que coincidan
    @FindBy(css = ".search-results .result")
    private WebElement mensajeSinResultados;

    // Aviso cuando el texto es demasiado corto
    @FindBy(css = ".search-results .warning")
    private WebElement mensajeAdvertencia;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SearchPage abrir() {
        driver.get(BaseTest.URL_BASE + "search");
        return this;
    }

    public SearchPage buscar(String texto) {
        campoTexto.clear();
        campoTexto.sendKeys(texto);
        botonBuscar.click();
        return new SearchPage(driver);
    }

    public List<String> obtenerTitulos() {
        List<String> titulos = new ArrayList<>();
        for (WebElement titulo : titulosProductos) {
            titulos.add(titulo.getText());
        }
        return titulos;
    }

    public String obtenerMensajeSinResultados() {
        return mensajeSinResultados.getText();
    }

    public String obtenerMensajeAdvertencia() {
        return mensajeAdvertencia.getText();
    }
}
