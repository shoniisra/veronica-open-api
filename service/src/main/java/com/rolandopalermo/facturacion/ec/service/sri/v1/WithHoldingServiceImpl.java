package com.rolandopalermo.facturacion.ec.service.sri.v1;

import com.rolandopalermo.facturacion.ec.common.DateUtils;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.domain.Withholding;
import com.rolandopalermo.facturacion.ec.dto.v1.withholding.ComprobanteRetencionDTO;
import com.rolandopalermo.facturacion.ec.modelo.retencion.ComprobanteRetencion;
import org.springframework.stereotype.Service;

import static org.exolab.castor.dsml.ImportEventListener.CREATED;

@Service("withHoldingServiceImpl")
public class WithHoldingServiceImpl extends GenericSRIServiceImpl<ComprobanteRetencionDTO, ComprobanteRetencion, Withholding> {

    @Override
    public Withholding toEntity(ComprobanteRetencion comprobanteRetencion, String contentAsXML) throws VeronicaException {
        Withholding withHolding = new Withholding();
        withHolding.setAccessKey(comprobanteRetencion.getInfoTributaria().getClaveAcceso());
        withHolding.setSriVersion(comprobanteRetencion.getVersion());
        withHolding.setXmlContent(contentAsXML);
        withHolding.setSupplierId(comprobanteRetencion.getInfoTributaria().getRuc());
        withHolding.setCustomerId(comprobanteRetencion.getInfoCompRetencion().getIdentificacionSujetoRetenido());
        withHolding.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(comprobanteRetencion.getInfoCompRetencion().getFechaEmision()));
        withHolding.setInternalStatusId(CREATED);
        return withHolding;
    }

}