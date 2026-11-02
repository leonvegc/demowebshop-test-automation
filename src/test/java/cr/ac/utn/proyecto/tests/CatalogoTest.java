package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CategoryPage;
import cr.ac.utn.proyecto.pages.HomePage;

public class CatalogoTest extends BaseTest {

    @Test
    @DisplayName("CP-030 Navegar por categoría y subcategoría")
    public void navegarPorCategoriaYSubcategoria() {
        CategoryPage computers = new HomePage(driver)
                .abrir()
                .irACategoria("Computers");
        assertEquals("Computers", computers.obtenerTitulo());

        CategoryPage notebooks = computers.irASubcategoria("Notebooks");
        assertEquals("Notebooks", notebooks.obtenerTitulo());
    }

    @Test
    @DisplayName("CP-031 Ordenar por nombre de la A a la Z")
    public void ordenarPorNombre() {
        CategoryPage libros = new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .ordenarPor("Name: A to Z");

        List<String> titulos = libros.obtenerTitulos();
        assertFalse(titulos.isEmpty());

        // Hacemos una copia ordenada y comparamos: si son iguales, el listado ya estaba ordenado
        List<String> ordenados = new ArrayList<>(titulos);
        Collections.sort(ordenados, String.CASE_INSENSITIVE_ORDER);
        assertEquals(ordenados, titulos);
    }

    @Test
    @DisplayName("CP-032 Ordenar por precio de menor a mayor")
    public void ordenarPorPrecio() {
        CategoryPage libros = new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .ordenarPor("Price: Low to High");

        List<Double> precios = libros.obtenerPrecios();
        assertFalse(precios.isEmpty());

        List<Double> ordenados = new ArrayList<>(precios);
        Collections.sort(ordenados);
        assertEquals(ordenados, precios);
    }

    @Test
    @DisplayName("CP-033 Mostrar 4 productos por página")
    public void mostrarCuatroPorPagina() {
        CategoryPage libros = new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .mostrarPorPagina("4");

        List<String> titulos = libros.obtenerTitulos();
        assertFalse(titulos.isEmpty());
        assertTrue(titulos.size() <= 4);

        // Books tiene mas de 4 productos, entonces tiene que salir el paginador
        assertTrue(libros.hayPaginaSiguiente());
    }

    @Test
    @DisplayName("CP-034 Ir a la página siguiente del listado")
    public void irAPaginaSiguiente() {
        CategoryPage pagina1 = new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .mostrarPorPagina("4");
        List<String> titulosPagina1 = pagina1.obtenerTitulos();

        CategoryPage pagina2 = pagina1.irAPaginaSiguiente();
        List<String> titulosPagina2 = pagina2.obtenerTitulos();

        // La segunda pagina tiene productos y ninguno se repite de la primera
        assertFalse(titulosPagina2.isEmpty());
        for (String titulo : titulosPagina2) {
            assertFalse(titulosPagina1.contains(titulo));
        }
    }

    @Test
    @DisplayName("CP-035 Cambiar la vista a lista")
    public void cambiarVistaALista() {
        CategoryPage libros = new HomePage(driver)
                .abrir()
                .irACategoria("Books")
                .cambiarVista("List");

        assertTrue(libros.estaEnVistaLista());
        assertFalse(libros.estaEnVistaCuadricula());
    }
}
