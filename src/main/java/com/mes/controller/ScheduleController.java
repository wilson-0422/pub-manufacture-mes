package com.mes.controller;

import com.mes.model.ProductionSchedule;
import com.mes.model.WorkOrder;
import com.mes.service.ScheduleService;
import com.mes.service.WorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final WorkOrderService workOrderService;

    public ScheduleController(ScheduleService scheduleService, WorkOrderService workOrderService) {
        this.scheduleService = scheduleService;
        this.workOrderService = workOrderService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) ProductionSchedule.ScheduleStatus status,
                       Model model) {
        if (status != null) {
            model.addAttribute("schedules", scheduleService.findByStatus(status));
            model.addAttribute("currentStatus", status);
        } else {
            model.addAttribute("schedules", scheduleService.findAll());
        }
        model.addAttribute("statuses", ProductionSchedule.ScheduleStatus.values());
        model.addAttribute("workOrders", workOrderService.findAll());
        return "schedule/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        ProductionSchedule schedule = scheduleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("排产记录不存在: " + id));
        model.addAttribute("schedule", schedule);
        workOrderService.findById(schedule.getOrderId()).ifPresent(wo ->
                model.addAttribute("workOrder", wo));
        return "schedule/detail";
    }

    @PostMapping
    public String create(ProductionSchedule schedule,
                         @RequestParam Long orderId,
                         org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        schedule.setOrderId(orderId);
        scheduleService.save(schedule);
        redirectAttributes.addFlashAttribute("message", "排产记录创建成功");
        return "redirect:/schedule";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam ProductionSchedule.ScheduleStatus status,
                               org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        ProductionSchedule schedule = scheduleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("排产记录不存在: " + id));
        schedule.setStatus(status);
        scheduleService.save(schedule);
        redirectAttributes.addFlashAttribute("message", "排产状态已更新");
        return "redirect:/schedule";
    }
}
