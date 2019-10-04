

package br.edu.utfpr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendedorDTO {
    
    private Long idVendedor;
    private String nomeVendedor;    

    
}