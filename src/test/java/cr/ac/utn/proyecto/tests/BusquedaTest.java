package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.SearchPage;

public class BusquedaTest extends BaseTest {

    private static final String SIN_RESULTADOS = "No products were found that matched your criteria.";

    @Test
    @DisplayName("CP-020 Búsqueda con resultados")
    public void busquedaConResultados() {
        SearchPage resultados = new HomePage(driver)
                .abrir()
                .buscar("computer");

        List<String> titulos = resultados.obtenerTitulos();
        assertFalse(titulos.isEmpty());

        // El catalogo puede cambiar, por eso no contamos productos:
        // solo revisamos que todos los resultados contengan el texto buscado
        for (String titulo : titulos) {
            assertTrue(titulo.toLowerCase().contains("computer"));
        }
    }

    @Test
    @DisplayName("CP-021 Búsqueda sin coincidencias")
    public void busquedaSinCoincidencias() {
        SearchPage resultados = new HomePage(driver)
                .abrir()
                .buscar("xyzxyz");

        assertEquals(SIN_RESULTADOS, resultados.obtenerMensajeSinResultados());
        assertTrue(resultados.obtenerTitulos().isEmpty());
    }

    @Test
    @DisplayName("CP-022 Búsqueda con 2 caracteres")
    public void busquedaConDosCaracteres() {
        SearchPage resultados = new HomePage(driver)
                .abrir()
                .buscar("ab");

        assertEquals("Search term minimum length is 3 characters", resultados.obtenerMensajeAdvertencia());
    }

    @Test
    @DisplayName("CP-023 Búsqueda con 3 caracteres")
    public void busquedaConTresCaracteres() {
        SearchPage resultados = new HomePage(driver)
                .abrir()
                .buscar("pho");

        // Con 3 caracteres ya se acepta la busqueda y salen productos
        List<String> titulos = resultados.obtenerTitulos();
        assertFalse(titulos.isEmpty());
        for (String titulo : titulos) {
            assertTrue(titulo.toLowerCase().contains("pho"));
        }
    }

    @Test
    @DisplayName("CP-024 Búsqueda con el campo vacío")
    public void busquedaConCampoVacio() {
        String alerta = new HomePage(driver)
                .abrir()
                .buscarSinTexto();

        assertEquals("Please enter some search keyword", alerta);
    }
}
