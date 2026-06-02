package com.mes.controller;

import com.mes.model.WorkOrder;
import com.mes.model.Equipment;
import com.mes.model.QualityInspection;
import com.mes.service.WorkOrderService;
import com.mes.service.EquipmentService;
import com.mes.service.QualityService;
import com.mes.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class HomeController {

    private final WorkOrderService workOrderService;
    private final EquipmentService equipmentService;
    private final QualityService qualityService;
    private final ScheduleService scheduleService;

    public HomeController(WorkOrderService workOrderService,
                          EquipmentService equipmentService,
                          QualityService qualityService,
                          ScheduleService scheduleService) {
        this.workOrderService = workOrderService;
        this.equipmentService = equipmentService;
        this.qualityService = qualityService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/")
    public String index(Model model) {
        long totalOrders = workOrderService.countAll();
        long pendingOrders = workOrderService.countByStatus(WorkOrder.Status.PENDING);
        long inProgressOrders = workOrderService.countByStatus(WorkOrder.Status.IN_PROGRESS);
        long completedOrders = workOrderService.countByStatus(WorkOrder.Status.COMPLETED);

        long runningEquipment = equipmentService.countByStatus(Equipment.Status.RUNNING);
        long maintenanceEquipment = equipmentService.countByStatus(Equipment.Status.MAINTENANCE);
        long totalEquipment = equipmentService.countAll();

        double passRate = qualityService.getPassRate();
        long totalInspections = qualityService.countAll();
        long failCount = qualityService.countByResult(QualityInspection.Result.FAIL);

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("inProgressOrders", inProgressOrders);
        model.addAttribute("completedOrders", completedOrders);
        model.addAttribute("runningEquipment", runningEquipment);
        model.addAttribute("maintenanceEquipment", maintenanceEquipment);
        model.addAttribute("totalEquipment", totalEquipment);
        model.addAttribute("passRate", String.format("%.1f", passRate));
        model.addAttribute("totalInspections", totalInspections);
        model.addAttribute("failCount", failCount);

        List<WorkOrder> recentOrders = workOrderService.findAll();
        if (recentOrders.size() > 5) {
            recentOrders = recentOrders.subList(0, 5);
        }
        model.addAttribute("recentOrders", recentOrders);

        return "index";
    }

    @GetMapping("/dashboard/overview")
    public String dashboard(Model model) {
        Map<String, Long> orderStats = new LinkedHashMap<>();
        orderStats.put("待处理", workOrderService.countByStatus(WorkOrder.Status.PENDING));
        orderStats.put("进行中", workOrderService.countByStatus(WorkOrder.Status.IN_PROGRESS));
        orderStats.put("已完成", workOrderService.countByStatus(WorkOrder.Status.COMPLETED));
        orderStats.put("已取消", workOrderService.countByStatus(WorkOrder.Status.CANCELLED));

        Map<String, Long> equipStats = new LinkedHashMap<>();
        equipStats.put("运行中", equipmentService.countByStatus(Equipment.Status.RUNNING));
        equipStats.put("空闲", equipmentService.countByStatus(Equipment.Status.IDLE));
        equipStats.put("维护中", equipmentService.countByStatus(Equipment.Status.MAINTENANCE));
        equipStats.put("故障", equipmentService.countByStatus(Equipment.Status.FAULT));
        equipStats.put("离线", equipmentService.countByStatus(Equipment.Status.OFFLINE));

        model.addAttribute("orderStats", orderStats);
        model.addAttribute("equipStats", equipStats);
        model.addAttribute("passRate", String.format("%.1f", qualityService.getPassRate()));
        model.addAttribute("schedules", scheduleService.findAll());

        return "dashboard/overview";
    }
}
