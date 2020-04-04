package com.rolandopalermo.facturacion.ec.app.config;

import com.rolandopalermo.facturacion.ec.dto.v1.bol.GuiaRemisionDTO;
import com.rolandopalermo.facturacion.ec.dto.v1.cm.NotaCreditoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1.dm.NotaDebitoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1.pc.LiquidacionCompraDTO;
import com.rolandopalermo.facturacion.ec.dto.v1.invoice.FacturaDTO;
import com.rolandopalermo.facturacion.ec.dto.v1.withholding.ComprobanteRetencionDTO;
import com.rolandopalermo.facturacion.ec.jaxb.impl.v1_0.SRIResponseJaxbServiceImpl;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;
import com.rolandopalermo.facturacion.ec.mapper.bol.GuiaRemisionMapper;
import com.rolandopalermo.facturacion.ec.mapper.cm.NotaCreditoMapper;
import com.rolandopalermo.facturacion.ec.mapper.dm.NotaDebitoMapper;
import com.rolandopalermo.facturacion.ec.mapper.pc.LiquidacionCompraMapper;
import com.rolandopalermo.facturacion.ec.mapper.invoice.FacturaMapper;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaComprobanteMapper;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaSolicitudMapper;
import com.rolandopalermo.facturacion.ec.mapper.withholding.ComprobanteRetencionMapper;
import com.rolandopalermo.facturacion.ec.modelo.factura.Factura;
import com.rolandopalermo.facturacion.ec.modelo.guia.GuiaRemision;
import com.rolandopalermo.facturacion.ec.modelo.notacredito.NotaCredito;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.NotaDebito;
import com.rolandopalermo.facturacion.ec.modelo.retencion.ComprobanteRetencion;
import com.rolandopalermo.facturacion.ec.modelo.liquidacion.LiquidacionCompra;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapperConfig {

    @Bean
    public Mapper<GuiaRemisionDTO, GuiaRemision> getBolMapper() {
        return new GuiaRemisionMapper();
    }

    @Bean
    public Mapper<NotaCreditoDTO, NotaCredito> getCreditMemoMapper() {
        return new NotaCreditoMapper();
    }

    @Bean
    public Mapper<NotaDebitoDTO, NotaDebito> getDebitMemoMapper() {
        return new NotaDebitoMapper();
    }

    @Bean
    public Mapper<FacturaDTO, Factura> getInvoiceMapper() {
        return new FacturaMapper();
    }

    @Bean
    public Mapper<LiquidacionCompraDTO, LiquidacionCompra> getPurchaseClearanceMapper() {
        return new LiquidacionCompraMapper();
    }

    @Bean
    public Mapper<ComprobanteRetencionDTO, ComprobanteRetencion> getWithHoldingMapper() {
        return new ComprobanteRetencionMapper();
    }

    @Bean
    public RespuestaSolicitudMapper getRespuestaSolicitudMapper() {
        return new RespuestaSolicitudMapper();
    }

    @Bean
    public RespuestaComprobanteMapper getRespuestaComprobanteMapper() {
        return new RespuestaComprobanteMapper(new SRIResponseJaxbServiceImpl());
    }

}