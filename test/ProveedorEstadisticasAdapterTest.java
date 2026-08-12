package test;

import com.sportspredictor.adapter.ProveedorDatosExterno;
import com.sportspredictor.adapter.ProveedorEstadisticasAdapter;
import com.sportspredictor.shared.Estadistica;
import com.sportspredictor.shared.Tendencia;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProveedorEstadisticasAdapterTest {

    @Test
    void cp19_obtenerEstadisticas_adaptaDatosDelProveedorExterno() {
        ProveedorDatosExterno proveedor = new ProveedorDatosExterno("https://api.sportspredictor.local");
        ProveedorEstadisticasAdapter adapter = new ProveedorEstadisticasAdapter(proveedor);

        List<Estadistica> estadisticas = adapter.obtenerEstadisticas("evt-001");

        assertNotNull(estadisticas);
        assertEquals(1, estadisticas.size());
        assertEquals("evt-001", estadisticas.get(0).getId());
        assertEquals("resumen", estadisticas.get(0).getTipo());
        assertTrue(estadisticas.get(0).getDescripcion().contains("posesion"));
    }

    @Test
    void cp20_obtenerTendencias_devuelveListaValida() {
        ProveedorEstadisticasAdapter adapter = new ProveedorEstadisticasAdapter(new ProveedorDatosExterno("https://api.example.com"));

        List<Tendencia> tendencias = adapter.obtenerTendencias("evt-001");

        assertNotNull(tendencias);
        assertEquals(1, tendencias.size());
        assertAll(
                () -> assertEquals("Racha de victorias", tendencias.get(0).getDescripcion()),
                () -> assertEquals(60.0, tendencias.get(0).getPorcentaje()),
                () -> assertTrue(tendencias.get(0).toString().contains("Racha de victorias"))
        );
    }
}
