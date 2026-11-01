package cr.ac.utn.proyecto.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import cr.ac.utn.proyecto.base.BaseTest;

public class SearchPage {
    private WebDriver driver;

    // Formulario de busqueda
    @FindBy(id = "Q")
    private WebElement campoTexto;

    @FindBy(id = "As")
    private WebElement casillaAvanzada;

    @FindBy(id = "Cid")
    private WebElement listaCategoria;

    @FindBy(id = "Isc")
    private WebElement casillaSubcategorias;

    @FindBy(id = "Pf")
    private WebElement campoPrecioDesde;

    @FindBy(id = "Pt")
    private WebElement campoPrecioHasta;

    @FindBy(css = ".search-button")
    private WebElement botonBuscar;

    // Resultados: son listas porque puede haber varios (o ninguno)
    @FindBy(css = ".product-item .product-title a")
    private List<WebElement> titulosProductos;

    @FindBy(css = ".product-item .actual-price")
    private List<WebElement> preciosProductos;

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

    /*
      Busqueda avanzada. Los filtros que no se quieran usar se mandan asi:
      categoria "All", incluirSubcategorias false, precios "".
     */
    public SearchPage buscarAvanzado(String texto, String categoria, boolean incluirSubcategorias,
                                     String precioDesde, String precioHasta) {
        campoTexto.clear();
        campoTexto.sendKeys(texto);

        // Los filtros avanzados solo se ven si la casilla esta marcada
        if (!casillaAvanzada.isSelected()) {
            casillaAvanzada.click();
        }
        new Select(listaCategoria).selectByVisibleText(categoria);
        if (casillaSubcategorias.isSelected() != incluirSubcategorias) {
            casillaSubcategorias.click();
        }
        campoPrecioDesde.clear();
        campoPrecioDesde.sendKeys(precioDesde);
        campoPrecioHasta.clear();
        campoPrecioHasta.sendKeys(precioHasta);

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

    public List<Double> obtenerPrecios() {
        List<Double> precios = new ArrayList<>();
        for (WebElement precio : preciosProductos) {
            // Algunos precios dicen "From 500.00", por eso se quita la palabra From
            String numero = precio.getText().replace("From", "").trim();
            precios.add(Double.parseDouble(numero));
        }
        return precios;
    }

    public String obtenerMensajeSinResultados() {
        return mensajeSinResultados.getText();
    }

    public String obtenerMensajeAdvertencia() {
        return mensajeAdvertencia.getText();
    }
}
