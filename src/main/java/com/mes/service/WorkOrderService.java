package com.mes.service;

import com.mes.model.WorkOrder;
import com.mes.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderService(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    public List<WorkOrder> findAll() {
        return workOrderRepository.findAll();
    }

    public Optional<WorkOrder> findById(Long id) {
        return workOrderRepository.findById(id);
    }

    public WorkOrder save(WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    public void deleteById(Long id) {
        workOrderRepository.deleteById(id);
    }

    public List<WorkOrder> findByStatus(WorkOrder.Status status) {
        return workOrderRepository.findByStatus(status);
    }

    public List<WorkOrder> findByPriority(WorkOrder.Priority priority) {
        return workOrderRepository.findByPriority(priority);
    }

    public List<WorkOrder> findByAssignedTo(String assignedTo) {
        return workOrderRepository.findByAssignedTo(assignedTo);
    }

    public long countByStatus(WorkOrder.Status status) {
        return workOrderRepository.findByStatus(status).size();
    }

    public long countAll() {
        return workOrderRepository.count();
    }
}
