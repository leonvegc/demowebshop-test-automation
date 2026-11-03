package cr.ac.utn.proyecto.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Esperas explicitas para usar dentro de las paginas (nunca en las pruebas).
 */
public final class Esperas {
    private Esperas() {}

    /*
      Hace clic y espera a que la pagina se recargue.
      Cuando la pagina cambia, el elemento de la pagina vieja ya no existe ("stale").
      Si el elemento viene de un @FindBy hay que ponerle tambien @CacheLookup;
      si no, Selenium lo vuelve a buscar en la pagina nueva y nunca queda viejo.
     */
    public static void clicYEsperarRecarga(WebDriver driver, WebElement elemento) {
        elemento.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.stalenessOf(elemento));
    }

    
    /*
      Espera a que un elemento se vea en la pantalla.
      Sirve para los avisos que aparecen despues de un clic (por ejemplo la barra
      verde de "added to your shopping cart"), que se cargan con ajax.
     */
    public static void esperarVisible(WebDriver driver, WebElement elemento) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(elemento));
    }
}
