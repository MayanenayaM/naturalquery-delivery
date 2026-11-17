package com.naturalquery.delivery.repository;

import com.naturalquery.delivery.model.Prato;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PratoRepository extends JpaRepository<Prato, Long>, JpaSpecificationExecutor<Prato> {

    @Override
    @EntityGraph(attributePaths = "estabelecimento")
    List<Prato> findAll(Specification<Prato> spec);
}
