package cl.duoc.bancoxyz.bff_atm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/atm")
public class AtmController {

    private final RestTemplate restTemplate;
    @Value("${core.legacy.url:http://localhost:8080}")
    private String coreUrl;

    public AtmController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(
            @RequestHeader(value = "x-atm-key", required = false) String atmKey,
            @RequestBody Map<String, Long> body) {
        
        if (atmKey == null || !atmKey.equals("ATM-CHILE-TERMINAL-01")) {
            return ResponseEntity.status(401).body(Map.of("error", "Cajero no autorizado"));
        }

        try {
            Long cuentaId = body.get("cuentaId");
            Map<?, ?> cuenta = restTemplate.getForObject(coreUrl + "/cuentas/" + cuentaId, Map.class);
            
            Map<String, Object> response = new HashMap<>();
            response.put("terminalValidado", true);
            response.put("cuentaId", cuenta.get("cuentaId"));
            response.put("saldoDisponible", cuenta.get("saldo"));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error en Core Legacy"));
        }
    }

    @PostMapping("/giro")
    public ResponseEntity<?> realizarGiro(
            @RequestHeader(value = "x-atm-key", required = false) String atmKey,
            @RequestBody Map<String, Object> body) {
        
        if (atmKey == null || !atmKey.equals("ATM-CHILE-TERMINAL-01")) {
            return ResponseEntity.status(401).body(Map.of("error", "Cajero no autorizado"));
        }

        try {
            Long cuentaId = Long.valueOf(body.get("cuentaId").toString());
            Double monto = Double.valueOf(body.get("monto").toString());

            Map<?, ?> cuenta = restTemplate.getForObject(coreUrl + "/cuentas/" + cuentaId, Map.class);
            Double saldoActual = Double.valueOf(cuenta.get("saldo").toString());

            if (saldoActual < monto) {
                return ResponseEntity.badRequest().body(Map.of("error", "Saldo insuficiente"));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("exito", true);
            response.put("mensaje", "Giro realizado correctamente");
            response.put("montoGirado", monto);
            response.put("nuevoSaldo", saldoActual - monto);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error en Core Legacy"));
        }
    }
}