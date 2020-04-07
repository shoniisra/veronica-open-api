package com.rolandopalermo.facturacion.ec.persistence;

import com.rolandopalermo.facturacion.ec.domain.PurchaseClearance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("purchaseClearanceRepository")
public interface PurchaseClearanceRepository extends BaseSRIRepository<PurchaseClearance, Long> {

    @Override
    @Query("select pc.accessKey from PurchaseClearance pc where pc.supplierId = ?1 and pc.isDeleted = ?2")
    List<String> findBySupplierIdAndIsDeleted(String supplierId, boolean isDeleted);

}