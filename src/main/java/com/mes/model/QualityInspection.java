package com.mes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quality_inspections")
public class QualityInspection {

    public enum Result { PASS, FAIL, CONDITIONAL }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String inspector;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Result result = Result.PASS;

    @Column(nullable = false)
    private Integer defectCount = 0;

    @Column(length = 500)
    private String defectDescription;

    @Column(nullable = false)
    private LocalDateTime inspectionTime;

    public QualityInspection() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getInspector() { return inspector; }
    public void setInspector(String inspector) { this.inspector = inspector; }
    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }
    public Integer getDefectCount() { return defectCount; }
    public void setDefectCount(Integer defectCount) { this.defectCount = defectCount; }
    public String getDefectDescription() { return defectDescription; }
    public void setDefectDescription(String defectDescription) { this.defectDescription = defectDescription; }
    public LocalDateTime getInspectionTime() { return inspectionTime; }
    public void setInspectionTime(LocalDateTime inspectionTime) { this.inspectionTime = inspectionTime; }
}
