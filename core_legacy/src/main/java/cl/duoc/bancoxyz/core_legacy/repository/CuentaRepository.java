package cl.duoc.bancoxyz.core_legacy.repository;

import cl.duoc.bancoxyz.core_legacy.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}