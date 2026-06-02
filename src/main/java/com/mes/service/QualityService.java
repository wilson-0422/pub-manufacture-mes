package com.mes.service;

import com.mes.model.QualityInspection;
import com.mes.repository.QualityInspectionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QualityService {

    private final QualityInspectionRepository qualityRepository;

    public QualityService(QualityInspectionRepository qualityRepository) {
        this.qualityRepository = qualityRepository;
    }

    public List<QualityInspection> findAll() {
        return qualityRepository.findAllByOrderByInspectionTimeDesc();
    }

    public Optional<QualityInspection> findById(Long id) {
        return qualityRepository.findById(id);
    }

    public QualityInspection save(QualityInspection inspection) {
        return qualityRepository.save(inspection);
    }

    public void deleteById(Long id) {
        qualityRepository.deleteById(id);
    }

    public List<QualityInspection> findByOrderId(Long orderId) {
        return qualityRepository.findByOrderId(orderId);
    }

    public List<QualityInspection> findByResult(QualityInspection.Result result) {
        return qualityRepository.findByResult(result);
    }

    public long countByResult(QualityInspection.Result result) {
        return qualityRepository.findByResult(result).size();
    }

    public long countAll() {
        return qualityRepository.count();
    }

    public double getPassRate() {
        long total = countAll();
        if (total == 0) return 0.0;
        long passCount = countByResult(QualityInspection.Result.PASS);
        return (double) passCount / total * 100;
    }
}
