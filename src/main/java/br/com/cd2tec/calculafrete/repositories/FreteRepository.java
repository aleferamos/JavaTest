package br.com.cd2tec.calculafrete.repositories;

import br.com.cd2tec.calculafrete.models.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {
}
