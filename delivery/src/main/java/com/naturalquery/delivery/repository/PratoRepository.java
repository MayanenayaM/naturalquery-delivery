package com.naturalquery.delivery.repository;

import com.naturalquery.delivery.model.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PratoRepository extends JpaRepository<Prato, Long>, JpaSpecificationExecutor<Prato> {
}
