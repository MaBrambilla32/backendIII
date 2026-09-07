package cl.duoc.bancoxyz.core_legacy.controller;

import cl.duoc.bancoxyz.core_legacy.model.Transaccion;
import cl.duoc.bancoxyz.core_legacy.repository.TransaccionRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    private final TransaccionRepository transaccionRepository;

    public TransaccionController(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    @GetMapping
    public List<Transaccion> listarTransacciones() {
        return transaccionRepository.findAll();
    }
}