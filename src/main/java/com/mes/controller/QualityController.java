package com.mes.controller;

import com.mes.model.QualityInspection;
import com.mes.model.WorkOrder;
import com.mes.service.QualityService;
import com.mes.service.WorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quality")
public class QualityController {

    private final QualityService qualityService;
    private final WorkOrderService workOrderService;

    public QualityController(QualityService qualityService, WorkOrderService workOrderService) {
        this.qualityService = qualityService;
        this.workOrderService = workOrderService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) QualityInspection.Result result,
                       Model model) {
        if (result != null) {
            model.addAttribute("inspections", qualityService.findByResult(result));
            model.addAttribute("currentResult", result);
        } else {
            model.addAttribute("inspections", qualityService.findAll());
        }
        model.addAttribute("results", QualityInspection.Result.values());
        model.addAttribute("passRate", String.format("%.1f", qualityService.getPassRate()));
        model.addAttribute("totalInspections", qualityService.countAll());
        return "quality/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("inspection", new QualityInspection());
        model.addAttribute("results", QualityInspection.Result.values());
        model.addAttribute("workOrders", workOrderService.findAll());
        return "quality/create";
    }

    @PostMapping
    public String create(QualityInspection inspection,
                         @RequestParam Long orderId,
                         RedirectAttributes redirectAttributes) {
        inspection.setOrderId(orderId);
        qualityService.save(inspection);
        redirectAttributes.addFlashAttribute("message", "质检记录创建成功");
        return "redirect:/quality";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        QualityInspection inspection = qualityService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("质检记录不存在: " + id));
        model.addAttribute("inspection", inspection);
        workOrderService.findById(inspection.getOrderId()).ifPresent(wo ->
                model.addAttribute("workOrder", wo));
        return "quality/detail";
    }
}
