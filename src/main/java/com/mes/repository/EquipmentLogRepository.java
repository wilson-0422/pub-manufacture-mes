package com.mes.repository;

import com.mes.model.EquipmentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipmentLogRepository extends JpaRepository<EquipmentLog, Long> {
    List<EquipmentLog> findByEquipmentId(Long equipmentId);
    List<EquipmentLog> findByEquipmentIdOrderByRecordedAtDesc(Long equipmentId);
    List<EquipmentLog> findByLogType(EquipmentLog.LogType logType);
}
