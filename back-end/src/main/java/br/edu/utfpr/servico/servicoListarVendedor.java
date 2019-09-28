/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.utfpr.servico;
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



/**
 *
 * @author Daniel
 */


@RestController

public class servicoListarVendedor {

    private final List<VendedorDTO> vendedores;

    public servicoListarVendedor() {
        vendedores = Stream.of(
            VendedorDTO.builder().id(Long.MIN_VALUE).nome("Daniel").totalVenda(100).mediaVendaDiaria(10).build(),
            VendedorDTO.builder().id(Long.MIN_VALUE).nome("Joao").totalVenda(200).mediaVendaDiaria(50).build(),
            VendedorDTO.builder().id(Long.MIN_VALUE).nome("Marcio").totalVenda(400).mediaVendaDiaria(100).build()                       
        ).collect(Collectors.toList());
    }

    @GetMapping ("/servico/vendedor")
    public ResponseEntity<List<VendedorDTO>> listar() {
    
        return ResponseEntity.ok(vendedores);
    }

    @GetMapping ("/servico/vendedor/{id}")
    public ResponseEntity<VendedorDTO> listarPorId(@PathVariable int id) {
        Optional<VendedorDTO> vendedorEncontrado = vendedores.stream().filter(p -> p.getId() == id).findAny();

        return ResponseEntity.of(vendedorEncontrado);
    }

    @PostMapping ("/servico/vendedor")
    public ResponseEntity<VendedorDTO> criar (@RequestBody VendedorDTO vendedor) {

        vendedor.setId(Long.MIN_VALUE);
               
        vendedores.add(vendedor);

        return ResponseEntity.status(201).body(vendedor);
    }

    @DeleteMapping ("/servico/vendedor/{id}")
    public ResponseEntity excluir (@PathVariable int id) {
        
        if (vendedores.removeIf(vendedor -> vendedor.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/vendedor/{id}")
    public ResponseEntity<VendedorDTO> alterar (@PathVariable int id, @RequestBody VendedorDTO vendedor) {
        Optional<VendedorDTO> vendedorExistente = vendedores.stream().filter(p -> p.getId() == id).findAny();

        vendedorExistente.ifPresent(p -> {
            p.setNome(vendedor.getNome());
            p.setTotalVenda(vendedor.getTotalVenda());
                   
            p.setMediaVendaDiaria(vendedor.getMediaVendaDiaria());
           
        });

        return ResponseEntity.of(vendedorExistente);
    }
    
}
