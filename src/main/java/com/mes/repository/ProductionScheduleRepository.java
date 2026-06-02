package com.mes.repository;

import com.mes.model.ProductionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductionScheduleRepository extends JpaRepository<ProductionSchedule, Long> {
    List<ProductionSchedule> findByOrderId(Long orderId);
    List<ProductionSchedule> findByStatus(ProductionSchedule.ScheduleStatus status);
    List<ProductionSchedule> findAllByOrderByStartTimeAsc();
}
