package cl.duoc.bancoxyz.bff_web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BffController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/cuentas")
    public Object obtenerCuentas() {

        String url = "http://localhost:8080/cuentas";

        return restTemplate.getForObject(url, Object.class);
    }

    @GetMapping("/transacciones")
    public Object obtenerTransacciones() {

        String url = "http://localhost:8080/transacciones";

        return restTemplate.getForObject(url, Object.class);
    }

    @GetMapping("/cuentas/{id}")
    public Object obtenerCuenta(@PathVariable Long id) {

        String url = "http://localhost:8080/cuentas/" + id;

        return restTemplate.getForObject(url, Object.class);
    }

    @GetMapping("/cuentas/{id}/transacciones")
    public Map<String, Object> obtenerHistorial(@PathVariable Long id) {

        String cuentaUrl = "http://localhost:8080/cuentas/" + id;
        String transaccionesUrl = "http://localhost:8080/transacciones";

        Object cuenta = restTemplate.getForObject(cuentaUrl, Object.class);
        Object transacciones = restTemplate.getForObject(transaccionesUrl, Object.class);

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("cuenta", cuenta);
        respuesta.put("historialTransacciones", transacciones);

        return respuesta;
    }
}