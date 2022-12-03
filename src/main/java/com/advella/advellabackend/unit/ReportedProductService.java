package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.ReportedProduct;
import com.advella.advellabackend.repositories.IReportedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportedProductService {
    private final IReportedProductRepository reportedProductRepository;

    public List<ReportedProduct> getAllReportedProduct() {
        return reportedProductRepository.findAll();
    }
}
