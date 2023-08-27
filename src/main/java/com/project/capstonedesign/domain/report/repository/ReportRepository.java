package com.project.capstonedesign.domain.report.repository;

import com.project.capstonedesign.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByReporterIdAndReportedId(Long reporterId, Long reportedId);
    List<Report> findByReportedId(Long reportedId);
    void deleteAllByReportedId(Long reportId);
}
