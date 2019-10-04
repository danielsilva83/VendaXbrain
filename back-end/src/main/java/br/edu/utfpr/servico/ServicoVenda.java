/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.utfpr.servico;

import br.edu.utfpr.dto.VendaDTO;
import br.edu.utfpr.dto.VendedorDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;


/**
 *
 * @author Daniel
 */

@RestController
public class ServicoVenda {
    
 private final List<VendaDTO> Vendas;
 private final  List<VendedorDTO> Vendedor;

    public ServicoVenda() {
      
        Vendedor = Stream.of(
            VendedorDTO.builder().idVendedor(Long.MIN_VALUE).nomeVendedor("Daniel").build(),
            VendedorDTO.builder().idVendedor(Long.MIN_VALUE).nomeVendedor("Joao").build(),
            VendedorDTO.builder().idVendedor(Long.MIN_VALUE).nomeVendedor("Marcio").build()                       
        ).collect(Collectors.toList());
        
        Vendas = Stream.of(
            VendaDTO.builder().id(Long.MIN_VALUE).datavenda(LocalDate.MIN).valor(100).idVendedor(Vendedor.get(1)).build(),
            VendaDTO.builder().id(Long.MIN_VALUE).datavenda(LocalDate.MIN).valor(200).idVendedor(Vendedor.get(2)).build()        
       ).collect(Collectors.toList());
    }

    @GetMapping ("/servico/vendas")
    public ResponseEntity<List<VendaDTO>> listar() {
    
        return ResponseEntity.ok(Vendas);
    }

    
    @GetMapping ("/servico/vendas/{id}")
    public ResponseEntity<VendaDTO> listarPorId(@PathVariable int id) {
        Optional<VendaDTO> vendedorEncontrado = Vendas.stream().filter(p -> p.getId() == id).findAny();

        return ResponseEntity.of(vendedorEncontrado);
    }

    
    @PostMapping ("/servico/vendas")
    public ResponseEntity<VendaDTO> criar (@RequestBody VendaDTO vendas) {

        
               
        Vendas.add(vendas);

        return ResponseEntity.status(201).body(vendas);
    }

    @DeleteMapping ("/servico/vendas/{id}")
    public ResponseEntity excluir (@PathVariable int id) {
        
        if (Vendas.removeIf(vendas -> vendas.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/vendas/{id}")
    public ResponseEntity<VendaDTO> alterar (@PathVariable int id, @RequestBody VendaDTO venda) {
        Optional<VendaDTO> vendaExistente = Vendas.stream().filter(p -> p.getId() == id).findAny();

        vendaExistente.ifPresent(p -> {
            p.setId(venda.getId());
            p.setDatavenda(venda.getDatavenda());
            p.setValor(venda.getValor());
            p.setIdVendedor(venda.getIdVendedor());
        });

        return ResponseEntity.of(vendaExistente);
    }
    
}


