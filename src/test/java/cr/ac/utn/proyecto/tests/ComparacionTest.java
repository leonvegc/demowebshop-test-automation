package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.ComparePage;
import cr.ac.utn.proyecto.pages.HomePage;

public class ComparacionTest extends BaseTest {

    // Agrega un libro de la categoria Books a la lista de comparacion
    private ComparePage agregarLibroAComparar(String nombre) {
        return new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .abrirProducto(nombre)
                .agregarAComparar();
    }

    @Test
    @DisplayName("CP-060 Agregar productos a comparar y ver la tabla")
    public void agregarProductosAComparar() {
        agregarLibroAComparar("Computing and Internet");
        ComparePage comparacion = agregarLibroAComparar("Health Book");

        List<String> nombres = comparacion.obtenerNombres();
        assertEquals(2, nombres.size());
        assertTrue(nombres.contains("Computing and Internet"));
        assertTrue(nombres.contains("Health Book"));
    }

    @Test
    @DisplayName("CP-061 Quitar un producto de la comparación")
    public void quitarProductoDeLaComparacion() {
        agregarLibroAComparar("Computing and Internet");
        ComparePage comparacion = agregarLibroAComparar("Health Book")
                .quitarProducto();

        // El ultimo agregado (Health Book) aparece primero en la tabla y es el que se quita
        List<String> nombres = comparacion.obtenerNombres();
        assertEquals(1, nombres.size());
        assertFalse(nombres.contains("Health Book"));
        assertTrue(nombres.contains("Computing and Internet"));
    }

    @Test
    @DisplayName("CP-062 Limpiar la lista de comparación")
    public void limpiarListaDeComparacion() {
        agregarLibroAComparar("Computing and Internet");
        ComparePage comparacion = agregarLibroAComparar("Health Book")
                .limpiarLista();

        assertEquals("You have no items to compare.", comparacion.obtenerMensajeListaVacia());
        assertEquals(0, comparacion.obtenerCantidadDeProductos());
    }
}
