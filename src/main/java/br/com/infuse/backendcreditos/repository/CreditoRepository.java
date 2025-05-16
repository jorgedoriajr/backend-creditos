package br.com.infuse.backendcreditos.repository;

import br.com.infuse.backendcreditos.model.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {
    List<Credito> findByNumeroCredito(String numeroCredito);
    List<Credito> findByNumeroNfse(String numeroNfse);
}