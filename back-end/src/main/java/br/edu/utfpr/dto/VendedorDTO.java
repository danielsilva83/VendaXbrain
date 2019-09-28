

package br.edu.utfpr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendedorDTO {
    
    private Long id;
    private String nome;    
    private double totalVenda;
    private double mediaVendaDiaria;
}