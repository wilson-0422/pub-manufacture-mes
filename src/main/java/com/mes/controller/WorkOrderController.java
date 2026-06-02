package com.mes.controller;

import com.mes.model.WorkOrder;
import com.mes.service.WorkOrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/workorders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) WorkOrder.Status status,
                       @RequestParam(required = false) WorkOrder.Priority priority,
                       Model model) {
        if (status != null) {
            model.addAttribute("workOrders", workOrderService.findByStatus(status));
            model.addAttribute("currentStatus", status);
        } else if (priority != null) {
            model.addAttribute("workOrders", workOrderService.findByPriority(priority));
            model.addAttribute("currentPriority", priority);
        } else {
            model.addAttribute("workOrders", workOrderService.findAll());
        }
        model.addAttribute("statuses", WorkOrder.Status.values());
        model.addAttribute("priorities", WorkOrder.Priority.values());
        return "workorders/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        WorkOrder workOrder = workOrderService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("工单不存在: " + id));
        model.addAttribute("workOrder", workOrder);
        return "workorders/detail";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("workOrder", new WorkOrder());
        model.addAttribute("statuses", WorkOrder.Status.values());
        model.addAttribute("priorities", WorkOrder.Priority.values());
        return "workorders/create";
    }

    @PostMapping
    public String create(@Valid WorkOrder workOrder, BindingResult result,
                         Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", WorkOrder.Status.values());
            model.addAttribute("priorities", WorkOrder.Priority.values());
            return "workorders/create";
        }
        workOrderService.save(workOrder);
        redirectAttributes.addFlashAttribute("message", "工单创建成功");
        return "redirect:/workorders";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        WorkOrder workOrder = workOrderService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("工单不存在: " + id));
        model.addAttribute("workOrder", workOrder);
        model.addAttribute("statuses", WorkOrder.Status.values());
        model.addAttribute("priorities", WorkOrder.Priority.values());
        return "workorders/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid WorkOrder workOrder,
                         BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", WorkOrder.Status.values());
            model.addAttribute("priorities", WorkOrder.Priority.values());
            return "workorders/edit";
        }
        workOrder.setId(id);
        workOrderService.save(workOrder);
        redirectAttributes.addFlashAttribute("message", "工单更新成功");
        return "redirect:/workorders";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        workOrderService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "工单已删除");
        return "redirect:/workorders";
    }
}
