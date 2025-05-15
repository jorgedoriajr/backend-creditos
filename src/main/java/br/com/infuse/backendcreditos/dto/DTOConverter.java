package br.com.infuse.backendcreditos.dto;

import br.com.infuse.backendcreditos.model.Credito;

public class DTOConverter {
    public static CreditoDTO convert(Credito credito) {
        CreditoDTO creditoDTO = new CreditoDTO();
        creditoDTO.setNumeroCredito(credito.getNumeroCredito());
        creditoDTO.setNumeroNfse(credito.getNumeroNfse());
        creditoDTO.setDataConstituicao(credito.getDataConstituicao());
        creditoDTO.setValorIssqn(credito.getValorIssqn());
        creditoDTO.setTipoCredito(credito.getTipoCredito());
        creditoDTO.setSimplesNacional(credito.isSimplesNacional());
        creditoDTO.setAliquota(credito.getAliquota());
        creditoDTO.setValorFaturado(credito.getValorFaturado());
        creditoDTO.setValorDeducao(credito.getValorDeducao());
        creditoDTO.setBaseCalculo(credito.getBaseCalculo());
        return creditoDTO;
    }
}