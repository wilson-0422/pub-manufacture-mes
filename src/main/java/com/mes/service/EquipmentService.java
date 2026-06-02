package com.mes.service;

import com.mes.model.Equipment;
import com.mes.model.EquipmentLog;
import com.mes.repository.EquipmentRepository;
import com.mes.repository.EquipmentLogRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentLogRepository equipmentLogRepository;

    public EquipmentService(EquipmentRepository equipmentRepository,
                            EquipmentLogRepository equipmentLogRepository) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentLogRepository = equipmentLogRepository;
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> findById(Long id) {
        return equipmentRepository.findById(id);
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public void deleteById(Long id) {
        equipmentRepository.deleteById(id);
    }

    public List<Equipment> findByStatus(Equipment.Status status) {
        return equipmentRepository.findByStatus(status);
    }

    public long countByStatus(Equipment.Status status) {
        return equipmentRepository.findByStatus(status).size();
    }

    public long countAll() {
        return equipmentRepository.count();
    }

    public List<EquipmentLog> findLogsByEquipmentId(Long equipmentId) {
        return equipmentLogRepository.findByEquipmentIdOrderByRecordedAtDesc(equipmentId);
    }

    public EquipmentLog saveLog(EquipmentLog log) {
        return equipmentLogRepository.save(log);
    }
}
