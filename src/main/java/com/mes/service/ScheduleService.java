package com.mes.service;

import com.mes.model.ProductionSchedule;
import com.mes.repository.ProductionScheduleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ProductionScheduleRepository scheduleRepository;

    public ScheduleService(ProductionScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ProductionSchedule> findAll() {
        return scheduleRepository.findAllByOrderByStartTimeAsc();
    }

    public Optional<ProductionSchedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }

    public ProductionSchedule save(ProductionSchedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<ProductionSchedule> findByOrderId(Long orderId) {
        return scheduleRepository.findByOrderId(orderId);
    }

    public List<ProductionSchedule> findByStatus(ProductionSchedule.ScheduleStatus status) {
        return scheduleRepository.findByStatus(status);
    }
}
