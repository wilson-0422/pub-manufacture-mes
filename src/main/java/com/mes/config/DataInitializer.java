package com.mes.config;

import com.mes.model.*;
import com.mes.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final WorkOrderRepository workOrderRepository;
    private final EquipmentRepository equipmentRepository;
    private final QualityInspectionRepository qualityInspectionRepository;
    private final ProductionScheduleRepository productionScheduleRepository;
    private final EquipmentLogRepository equipmentLogRepository;

    public DataInitializer(WorkOrderRepository workOrderRepository,
                           EquipmentRepository equipmentRepository,
                           QualityInspectionRepository qualityInspectionRepository,
                           ProductionScheduleRepository productionScheduleRepository,
                           EquipmentLogRepository equipmentLogRepository) {
        this.workOrderRepository = workOrderRepository;
        this.equipmentRepository = equipmentRepository;
        this.qualityInspectionRepository = qualityInspectionRepository;
        this.productionScheduleRepository = productionScheduleRepository;
        this.equipmentLogRepository = equipmentLogRepository;
    }

    @Override
    public void run(String... args) {
        if (workOrderRepository.count() > 0) {
            return;
        }

        List<WorkOrder> orders = new ArrayList<>();
        String[][] orderData = {
                {"WO-2024-001", "精密轴承 A-100", "500", "COMPLETED", "HIGH", "2024-01-15T08:00", "2024-01-20T18:00", "2024-01-15T08:30", "2024-01-20T16:00", "张伟"},
                {"WO-2024-002", "液压缸体 B-200", "300", "IN_PROGRESS", "HIGH", "2024-01-18T08:00", "2024-01-25T18:00", "2024-01-18T09:00", "", "李明"},
                {"WO-2024-003", "齿轮组件 C-300", "200", "IN_PROGRESS", "MEDIUM", "2024-01-20T08:00", "2024-01-28T18:00", "2024-01-20T08:15", "", "王芳"},
                {"WO-2024-004", "密封垫片 D-400", "1000", "PENDING", "LOW", "2024-01-25T08:00", "2024-01-30T18:00", "", "", "赵刚"},
                {"WO-2024-005", "传动轴 E-500", "150", "COMPLETED", "HIGH", "2024-01-10T08:00", "2024-01-16T18:00", "2024-01-10T08:00", "2024-01-15T17:00", "陈静"},
                {"WO-2024-006", "法兰盘 F-600", "400", "PENDING", "MEDIUM", "2024-01-28T08:00", "2024-02-03T18:00", "", "", "刘洋"},
                {"WO-2024-007", "连接螺栓 G-700", "2000", "IN_PROGRESS", "LOW", "2024-01-22T08:00", "2024-01-26T18:00", "2024-01-22T08:00", "", "周磊"},
                {"WO-2024-008", "活塞环 H-800", "800", "COMPLETED", "MEDIUM", "2024-01-08T08:00", "2024-01-14T18:00", "2024-01-08T07:45", "2024-01-13T16:30", "吴强"},
                {"WO-2024-009", "凸轮轴 I-900", "100", "PENDING", "HIGH", "2024-02-01T08:00", "2024-02-08T18:00", "", "", "孙丽"},
                {"WO-2024-010", "曲轴 J-1000", "50", "IN_PROGRESS", "HIGH", "2024-01-23T08:00", "2024-02-02T18:00", "2024-01-23T08:30", "", "郑华"}
        };

        for (String[] d : orderData) {
            WorkOrder wo = new WorkOrder();
            wo.setOrderNo(d[0]);
            wo.setProductName(d[1]);
            wo.setQuantity(Integer.parseInt(d[2]));
            wo.setStatus(WorkOrder.Status.valueOf(d[3]));
            wo.setPriority(WorkOrder.Priority.valueOf(d[4]));
            wo.setPlannedStart(d[5].isEmpty() ? null : LocalDateTime.parse(d[5]));
            wo.setPlannedEnd(d[6].isEmpty() ? null : LocalDateTime.parse(d[6]));
            wo.setActualStart(d[7].isEmpty() ? null : LocalDateTime.parse(d[7]));
            wo.setActualEnd(d[8].isEmpty() ? null : LocalDateTime.parse(d[8]));
            wo.setAssignedTo(d[9]);
            orders.add(workOrderRepository.save(wo));
        }

        List<Equipment> equipments = new ArrayList<>();
        String[][] equipData = {
                {"CNC加工中心", "EQ-CNC-001", "RUNNING", "A区-01工位", "2024-01-15", "2024-04-15"},
                {"数控车床", "EQ-LATHE-002", "RUNNING", "A区-02工位", "2024-01-10", "2024-04-10"},
                {"液压冲压机", "EQ-PRESS-003", "MAINTENANCE", "B区-01工位", "2024-01-20", "2024-03-20"},
                {"焊接机器人", "EQ-WELD-004", "RUNNING", "B区-03工位", "2024-01-05", "2024-04-05"},
                {"磨削机床", "EQ-GRIND-005", "IDLE", "C区-01工位", "2024-01-18", "2024-04-18"}
        };

        for (String[] d : equipData) {
            Equipment eq = new Equipment();
            eq.setName(d[0]);
            eq.setCode(d[1]);
            eq.setStatus(Equipment.Status.valueOf(d[2]));
            eq.setLocation(d[3]);
            eq.setLastMaintenance(LocalDate.parse(d[4]));
            eq.setNextMaintenance(LocalDate.parse(d[5]));
            equipments.add(equipmentRepository.save(eq));
        }

        String[][] qualityData = {
                {"1", "质检员-王明", "PASS", "0", "", "2024-01-20T15:00"},
                {"2", "质检员-李红", "PASS", "0", "", "2024-01-25T10:30"},
                {"3", "质检员-王明", "FAIL", "5", "表面粗糙度超标，尺寸偏差0.05mm", "2024-01-22T14:00"},
                {"5", "质检员-赵亮", "PASS", "0", "", "2024-01-15T16:00"},
                {"8", "质检员-李红", "PASS", "1", "轻微划痕，不影响使用", "2024-01-13T17:00"},
                {"7", "质检员-王明", "FAIL", "12", "螺纹不合格，需返工处理", "2024-01-24T09:00"},
                {"10", "质检员-赵亮", "PASS", "0", "", "2024-01-23T18:00"},
                {"4", "质检员-李红", "PASS", "0", "", "2024-01-28T11:00"}
        };

        for (String[] d : qualityData) {
            QualityInspection qi = new QualityInspection();
            qi.setOrderId(Long.parseLong(d[0]));
            qi.setInspector(d[1]);
            qi.setResult(QualityInspection.Result.valueOf(d[2]));
            qi.setDefectCount(Integer.parseInt(d[3]));
            qi.setDefectDescription(d[4]);
            qi.setInspectionTime(LocalDateTime.parse(d[5]));
            qualityInspectionRepository.save(qi);
        }

        String[][] scheduleData = {
                {"1", "CNC加工", "2024-01-15T08:00", "2024-01-18T18:00", "COMPLETED"},
                {"2", "车削加工", "2024-01-18T08:00", "2024-01-22T18:00", "IN_PROGRESS"},
                {"3", "齿轮铣削", "2024-01-20T08:00", "2024-01-24T18:00", "IN_PROGRESS"},
                {"5", "热处理", "2024-01-10T08:00", "2024-01-13T18:00", "COMPLETED"},
                {"7", "冲压成型", "2024-01-22T08:00", "2024-01-25T18:00", "IN_PROGRESS"},
                {"10", "精密磨削", "2024-01-23T08:00", "2024-01-28T18:00", "IN_PROGRESS"}
        };

        for (String[] d : scheduleData) {
            ProductionSchedule ps = new ProductionSchedule();
            ps.setOrderId(Long.parseLong(d[0]));
            ps.setProcessName(d[1]);
            ps.setStartTime(LocalDateTime.parse(d[2]));
            ps.setEndTime(LocalDateTime.parse(d[3]));
            ps.setStatus(ProductionSchedule.ScheduleStatus.valueOf(d[4]));
            productionScheduleRepository.save(ps);
        }

        for (Equipment eq : equipments) {
            EquipmentLog log = new EquipmentLog();
            log.setEquipmentId(eq.getId());
            log.setLogType(EquipmentLog.LogType.OPERATION);
            log.setDescription("设备正常运行记录");
            log.setRecordedAt(LocalDateTime.now().minusHours(2));
            equipmentLogRepository.save(log);
        }
    }
}
