package br.com.infuse.backendcreditos.repository;

import br.com.infuse.backendcreditos.model.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {
    Credito findByNumeroCredito(String numeroCredito);
    Credito findByNumeroNfse(String numeroNfse);
}