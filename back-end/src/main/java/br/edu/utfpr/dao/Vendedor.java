
package br.edu.utfpr.dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Daniel
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vendedor implements Serializable {

    @Id @GeneratedValue
    private Long idVendedor;
    private String nome; 
 
}