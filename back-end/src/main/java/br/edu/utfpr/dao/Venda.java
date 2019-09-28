/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.utfpr.dao;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 *
 * @author Daniel
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Venda implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private LocalDate datavenda;
    private double valor;
    
    @ManyToOne
    private Vendedor nomeVendedor;
    
}