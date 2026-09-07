package cl.duoc.bancoxyz.core_legacy.repository;

import cl.duoc.bancoxyz.core_legacy.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}