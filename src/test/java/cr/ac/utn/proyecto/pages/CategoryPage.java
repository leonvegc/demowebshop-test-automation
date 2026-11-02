package cr.ac.utn.proyecto.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cr.ac.utn.proyecto.base.Esperas;

public class CategoryPage {
    private WebDriver driver;

    @FindBy(css = ".page-title h1")
    private WebElement titulo;

    // Cuadro con los enlaces a las subcategorias (solo existe en categorias como Computers)
    @FindBy(css = ".sub-category-grid")
    private WebElement subcategorias;

    // Las tres listas desplegables del listado
    @FindBy(id = "products-orderby")
    private WebElement listaOrden;

    @FindBy(id = "products-pagesize")
    private WebElement listaCantidad;

    @FindBy(id = "products-viewmode")
    private WebElement listaVista;

    // Productos del listado
    @FindBy(css = ".product-item .product-title a")
    private List<WebElement> titulosProductos;

    @FindBy(css = ".product-item .actual-price")
    private List<WebElement> preciosProductos;

    // Cada vista usa un contenedor distinto. Son listas para saber si existen o no.
    @FindBy(css = ".product-grid")
    private List<WebElement> vistaCuadricula;

    @FindBy(css = ".product-list")
    private List<WebElement> vistaLista;

    // Enlace "Next" del paginador (no existe si todo cabe en una pagina)
    @FindBy(css = ".pager .next-page a")
    private List<WebElement> enlaceSiguiente;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String obtenerTitulo() {
        return titulo.getText();
    }

    // Se usa By porque el nombre de la subcategoria cambia en cada prueba
    // y @FindBy no acepta parametros.
    public CategoryPage irASubcategoria(String nombre) {
        WebElement enlace = subcategorias.findElement(By.linkText(nombre));
        Esperas.clicYEsperarRecarga(driver, enlace);
        return new CategoryPage(driver);
    }

    // Ejemplo de opcion: "Name: A to Z" o "Price: Low to High"
    public CategoryPage ordenarPor(String opcion) {
        elegirOpcion(listaOrden, opcion);
        return this;
    }

    // Ejemplo de opcion: "4", "8" o "12"
    public CategoryPage mostrarPorPagina(String cantidad) {
        elegirOpcion(listaCantidad, cantidad);
        return this;
    }

    // Ejemplo de opcion: "Grid" o "List"
    public CategoryPage cambiarVista(String vista) {
        elegirOpcion(listaVista, vista);
        return this;
    }

    public boolean hayPaginaSiguiente() {
        return !enlaceSiguiente.isEmpty();
    }

    public CategoryPage irAPaginaSiguiente() {
        Esperas.clicYEsperarRecarga(driver, enlaceSiguiente.get(0));
        return new CategoryPage(driver);
    }

    public boolean estaEnVistaLista() {
        return !vistaLista.isEmpty();
    }

    public boolean estaEnVistaCuadricula() {
        return !vistaCuadricula.isEmpty();
    }

    public List<String> obtenerTitulos() {
        List<String> titulos = new ArrayList<>();
        for (WebElement producto : titulosProductos) {
            titulos.add(producto.getText());
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

    /*
      Elige una opcion de una lista desplegable.
      Al elegirla, el sitio recarga la pagina yendo a la direccion que trae la opcion.
      Esperamos a llegar a esa direccion; si no, se leeria la pagina vieja.
      Ojo: la opcion que se elija debe ser distinta a la que ya esta marcada.
     */
    private void elegirOpcion(WebElement lista, String opcion) {
        Select select = new Select(lista);

        String direccion = "";
        for (WebElement o : select.getOptions()) {
            if (o.getText().equals(opcion)) {
                direccion = o.getAttribute("value");
            }
        }

        select.selectByVisibleText(opcion);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(direccion));
    }
}
