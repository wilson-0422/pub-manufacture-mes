package com.mes.controller;

import com.mes.model.Equipment;
import com.mes.model.EquipmentLog;
import com.mes.service.EquipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) Equipment.Status status,
                       Model model) {
        if (status != null) {
            model.addAttribute("equipments", equipmentService.findByStatus(status));
            model.addAttribute("currentStatus", status);
        } else {
            model.addAttribute("equipments", equipmentService.findAll());
        }
        model.addAttribute("statuses", Equipment.Status.values());
        model.addAttribute("runningCount", equipmentService.countByStatus(Equipment.Status.RUNNING));
        model.addAttribute("maintenanceCount", equipmentService.countByStatus(Equipment.Status.MAINTENANCE));
        model.addAttribute("totalCount", equipmentService.countAll());
        return "equipment/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("设备不存在: " + id));
        model.addAttribute("equipment", equipment);
        model.addAttribute("logs", equipmentService.findLogsByEquipmentId(id));
        model.addAttribute("logTypes", EquipmentLog.LogType.values());
        return "equipment/detail";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam Equipment.Status status,
                               RedirectAttributes redirectAttributes) {
        Equipment equipment = equipmentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("设备不存在: " + id));
        equipment.setStatus(status);
        equipmentService.save(equipment);

        EquipmentLog log = new EquipmentLog();
        log.setEquipmentId(id);
        log.setLogType(status == Equipment.Status.MAINTENANCE
                ? EquipmentLog.LogType.MAINTENANCE
                : EquipmentLog.LogType.OPERATION);
        log.setDescription("状态变更为: " + status.getDeclaringClass().getSimpleName() + "." + status);
        log.setRecordedAt(LocalDateTime.now());
        equipmentService.saveLog(log);

        redirectAttributes.addFlashAttribute("message", "设备状态已更新");
        return "redirect:/equipment/" + id;
    }

    @PostMapping("/{id}/log")
    public String addLog(@PathVariable Long id,
                         @RequestParam EquipmentLog.LogType logType,
                         @RequestParam String description,
                         RedirectAttributes redirectAttributes) {
        EquipmentLog log = new EquipmentLog();
        log.setEquipmentId(id);
        log.setLogType(logType);
        log.setDescription(description);
        log.setRecordedAt(LocalDateTime.now());
        equipmentService.saveLog(log);
        redirectAttributes.addFlashAttribute("message", "设备日志已添加");
        return "redirect:/equipment/" + id;
    }
}
