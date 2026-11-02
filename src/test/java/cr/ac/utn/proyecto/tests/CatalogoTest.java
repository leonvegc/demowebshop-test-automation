package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
