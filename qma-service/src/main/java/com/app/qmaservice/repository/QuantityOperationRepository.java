package com.app.qmaservice.repository;

import com.app.qmaservice.entity.QuantityOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuantityOperationRepository extends JpaRepository<QuantityOperation, Long> {

    List<QuantityOperation> findByOperation(String operation);

    List<QuantityOperation> findByMeasurementType(String measurementType);

    long countByOperation(String operation);
}
