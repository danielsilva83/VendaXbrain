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
import io.micrometer.core.ipc.http.HttpSender.Response;
import java.time.LocalDate;


/**
 *
 * @author Daniel
 */

@RestController
public class ServicoVenda {
    
 private final List<VendaDTO> Vendas;
 private VendedorDTO vendedor;

    public ServicoVenda() {
       
        
        Vendas = Stream.of(
            VendaDTO.builder().id(Long.MIN_VALUE).datavenda(LocalDate.MIN).valor(100).vendedor((VendedorDTO) vendedor).build()
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
    public ResponseEntity<VendaDTO> criar (@RequestBody VendaDTO vendedor) {

        vendedor.setId(Long.MIN_VALUE);
               
        Vendas.add(vendedor);

        return ResponseEntity.status(201).body(vendedor);
    }

    @DeleteMapping ("/servico/vendas/{id}")
    public ResponseEntity excluir (@PathVariable int id) {
        
        if (Vendas.removeIf(vendas -> vendas.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/vendas/{id}")
    public ResponseEntity<VendaDTO> alterar (@PathVariable int id, @RequestBody VendaDTO vendedor) {
        Optional<VendaDTO> vendedorExistente = Vendas.stream().filter(p -> p.getId() == id).findAny();

        vendedorExistente.ifPresent(p -> {
            p.setId(vendedor.getId());
            p.setDatavenda(vendedor.getDatavenda());
            p.setValor(vendedor.getValor());
            p.setVendedor(vendedor.getVendedor());
        });

        return ResponseEntity.of(vendedorExistente);
    }
    
}


