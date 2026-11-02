package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
}
