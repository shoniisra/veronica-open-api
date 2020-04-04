package com.rolandopalermo.facturacion.ec.service.sri.v1;

import com.rolandopalermo.facturacion.ec.common.DateUtils;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.domain.PurchaseClearance;
import com.rolandopalermo.facturacion.ec.dto.v1.pc.LiquidacionCompraDTO;
import com.rolandopalermo.facturacion.ec.modelo.liquidacion.LiquidacionCompra;
import org.springframework.stereotype.Service;

import static org.exolab.castor.dsml.ImportEventListener.CREATED;

@Service("purchaseClearanceServiceImpl")
public class PurchaseClearanceServiceImpl extends GenericSRIServiceImpl<LiquidacionCompraDTO, LiquidacionCompra, PurchaseClearance> {

    @Override
    public PurchaseClearance toEntity(LiquidacionCompra liquidacionCompra, String contentAsXML) throws VeronicaException {
        PurchaseClearance purchaseClearance = new PurchaseClearance();
        purchaseClearance.setAccessKey(liquidacionCompra.getInfoTributaria().getClaveAcceso());
        purchaseClearance.setSriVersion(liquidacionCompra.getVersion());
        purchaseClearance.setXmlContent(contentAsXML);
        purchaseClearance.setSupplierId(liquidacionCompra.getInfoTributaria().getRuc());
        purchaseClearance.setCustomerId(liquidacionCompra.getInfoLiquidacionCompra().getIdentificacionProveedor());
        purchaseClearance.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(liquidacionCompra.getInfoLiquidacionCompra().getFechaEmision()));
        purchaseClearance.setPurchaseClearanceNumber(liquidacionCompra.getInfoTributaria().getSecuencial());
        purchaseClearance.setInternalStatusId(CREATED);
        return purchaseClearance;
    }

}