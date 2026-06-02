package com.mes.repository;

import com.mes.model.QualityInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QualityInspectionRepository extends JpaRepository<QualityInspection, Long> {
    List<QualityInspection> findByOrderId(Long orderId);
    List<QualityInspection> findByResult(QualityInspection.Result result);
    List<QualityInspection> findByInspector(String inspector);
    List<QualityInspection> findAllByOrderByInspectionTimeDesc();
}
