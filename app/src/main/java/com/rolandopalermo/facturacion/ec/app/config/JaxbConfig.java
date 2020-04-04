package com.rolandopalermo.facturacion.ec.app.config;

import com.rolandopalermo.facturacion.ec.jaxb.JaxbService;
import com.rolandopalermo.facturacion.ec.jaxb.impl.v1_0.BolJaxbServiceImpl;
import com.rolandopalermo.facturacion.ec.jaxb.impl.v1_0.CreditMemoJaxbServiceImpl;
import com.rolandopalermo.facturacion.ec.jaxb.impl.v1_0.DebitMemoJaxbServiceImpl;
import com.rolandopalermo.facturacion.ec.jaxb.impl.v1_0.InvoiceJaxbServiceImpl;
import com.rolandopalermo.facturacion.ec.jaxb.impl.v1_0.WithHoldingJaxbServiceImpl;
import com.rolandopalermo.facturacion.ec.jaxb.impl.v1_0.PurchaseClearanceJaxbServiceImpl;
import com.rolandopalermo.facturacion.ec.modelo.factura.Factura;
import com.rolandopalermo.facturacion.ec.modelo.guia.GuiaRemision;
import com.rolandopalermo.facturacion.ec.modelo.notacredito.NotaCredito;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.NotaDebito;
import com.rolandopalermo.facturacion.ec.modelo.retencion.ComprobanteRetencion;
import com.rolandopalermo.facturacion.ec.modelo.liquidacion.LiquidacionCompra;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaxbConfig {

    @Bean
    public JaxbService<GuiaRemision> getBolMarshaller() {
        return new BolJaxbServiceImpl();
    }

    @Bean
    public JaxbService<NotaCredito> getCMMarshaller() {
        return new CreditMemoJaxbServiceImpl();
    }

    @Bean
    public JaxbService<NotaDebito> getDMMarshaller() {
        return new DebitMemoJaxbServiceImpl();
    }

    @Bean
    public JaxbService<Factura> getInvoiceMarshaller() {
        return new InvoiceJaxbServiceImpl();
    }

    @Bean
    public JaxbService<LiquidacionCompra> getPCMarshaller() {
        return new PurchaseClearanceJaxbServiceImpl();
    }

    @Bean
    public JaxbService<ComprobanteRetencion> getWithHoldingMarshaller() {
        return new WithHoldingJaxbServiceImpl();
    }

}