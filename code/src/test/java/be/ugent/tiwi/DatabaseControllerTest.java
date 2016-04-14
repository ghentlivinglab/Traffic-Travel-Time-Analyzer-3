package be.ugent.tiwi;

import be.ugent.tiwi.dal.DatabaseController;
import be.ugent.tiwi.domein.Provider;
import be.ugent.tiwi.settings.DependencyModules.RepositoryTestModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for simple App.
 */
public class DatabaseControllerTest{
    private static DatabaseController dbController;

    @BeforeClass
    public static void prepareClass(){
        Injector injector = Guice.createInjector(new RepositoryTestModule());
        dbController = injector.getInstance(DatabaseController.class);

    }

    @Test
    public void haalProviderOpMetId_Correct() {
        Provider prov = dbController.haalProviderOp(1);
        assertEquals("Provider 1", prov.getNaam());
    }

    @Test
    public void haalProviderOpMetId_BestaatNiet() {
        Provider prov = dbController.haalProviderOp(80);
        assertNull(prov);
    }

    @Test
    public void haalProviderOpMetNaam_Correct() {
        Provider prov = dbController.haalProviderOp("Provider 2");
        assertEquals(2, prov.getId());
    }

    @Test
    public void haalProviderOpMetNaam_BestaatNiet() {
        Provider prov = dbController.haalProviderOp("Provider TOMTOM");
        assertNull(prov);
    }

    @Test
    public void haalActieveProviders_Correct() {
        int count = dbController.haalActieveProvidersOp().size();
        assertEquals(2, count);
    }

    @Test
    public void haalAlleProviders_Correct() {
        int count = dbController.haalAlleProvidersOp().size();
        assertEquals(4, count);
    }

    @Test
    public void haalAlleTrajecten_Correct() {
        int count = dbController.haalTrajectenOp().size();
        assertEquals(5, count);
    }
}
