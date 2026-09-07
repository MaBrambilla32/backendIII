package cl.duoc.bancoxyz.bff_mobile.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("/api/mobile")
public class BffMobileController {

    private final RestTemplate restTemplate;

    @Value("${core.legacy.url}")
    private String coreUrl;

    public BffMobileController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/cuentas/{id}/resumen")
    public ResponseEntity<?> getResumenMovil(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Long id) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "Token móvil no proporcionado"));
        }

        try {
            // 1. Buscamos la cuenta (esto ya sabemos que funciona perfecto con el ID 101)
            Map<?, ?> cuentaLegacy = restTemplate.getForObject(coreUrl + "/cuentas/" + id, Map.class);
            
            // 2. Apuntamos a la ruta general que sí existe en el código de tu compañero
            List<?> transaccionesLegacy = restTemplate.getForObject(coreUrl + "/transacciones", List.class);

            Map<String, Object> respuestaLigera = new HashMap<>();
            // Usamos "cuentaId" porque así viene escrito desde la base de datos
            respuestaLigera.put("numeroCuenta", cuentaLegacy.get("cuentaId")); 
            respuestaLigera.put("saldoDisponible", cuentaLegacy.get("saldo"));
            
            // Tomamos un máximo de 3 transacciones para no saturar el móvil
            int limite = transaccionesLegacy != null ? Math.min(transaccionesLegacy.size(), 3) : 0;
            respuestaLigera.put("ultimosMovimientos", transaccionesLegacy != null ? transaccionesLegacy.subList(0, limite) : List.of());

            return ResponseEntity.ok(respuestaLigera);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error al consultar Core Legacy: " + e.getMessage()));
        }
    }
}