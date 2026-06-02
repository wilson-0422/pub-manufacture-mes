package com.mes.repository;

import com.mes.model.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByStatus(WorkOrder.Status status);
    List<WorkOrder> findByPriority(WorkOrder.Priority priority);
    List<WorkOrder> findByAssignedTo(String assignedTo);
    List<WorkOrder> findAllByOrderByPriorityDescCreatedAtDesc();
}
