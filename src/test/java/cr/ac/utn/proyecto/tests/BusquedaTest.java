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
}
