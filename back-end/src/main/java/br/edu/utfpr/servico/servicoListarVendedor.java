/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.utfpr.servico;
import br.edu.utfpr.dao.Vendedor;
import br.edu.utfpr.dto.VendaDTO;
import br.edu.utfpr.dto.VendedorDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.JdbcTemplate;
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
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;



/**
 *
 * @author Daniel
 */


@RestController

public class servicoListarVendedor {

    private final List<VendedorDTO> vendedores;
    private final List<VendaDTO> Vendas;
    private JdbcTemplate jdbcTemplate;
    private Date datavendaini;
    private Date datavendafim;
    public servicoListarVendedor() {
        vendedores = Stream.of(
            VendedorDTO.builder().idVendedor(Long.MIN_VALUE).nomeVendedor("Daniel").build(),
            VendedorDTO.builder().idVendedor(Long.MIN_VALUE).nomeVendedor("Joao").build(),
            VendedorDTO.builder().idVendedor(Long.MIN_VALUE).nomeVendedor("Marcio").build()                       
        ).collect(Collectors.toList());
        
         Vendas = Stream.of(
            VendaDTO.builder().id(Long.MIN_VALUE).datavenda(LocalDate.MIN).valor(100).idVendedor(vendedores.get(1)).build(),
            VendaDTO.builder().id(Long.MIN_VALUE).datavenda(LocalDate.MIN).valor(200).idVendedor(vendedores.get(2)).build()        
       ).collect(Collectors.toList());
    }

    @GetMapping ("/servico/listarvendedor")
    public ResponseEntity<List<VendedorDTO>> listar() {
    
        return ResponseEntity.ok(vendedores);
    }

    /**
     *
     * @param datavendaini
     * @param datavendafim
     * @return
     */
    @GetMapping ("/servico/listarpordata")
    public Vendedor listarPorData(Date datavendaini, Date datavendafim) {
          
        return jdbcTemplate.queryForObject("SELECT *,sum(venda.valor), FROM venda "
                + "INNER JOIN vendedor ON venda.idVendedor = vendedor.idVendedor"
                + "WHERE datavenda BETWEEN datavendaini AND datavendafim",
				(rs, rowNum) -> new Vendedor(rs.getLong("idVendedor"), rs.getString("nomeVendedor")));           
    }
    
    
    @GetMapping ("/servico/listarvendedor/{id}")
    public ResponseEntity<VendedorDTO> listarPorId(@PathVariable int id) {
        Optional<VendedorDTO> vendedorEncontrado = vendedores.stream().filter(p -> p.getIdVendedor() == id).findAny();

        return ResponseEntity.of(vendedorEncontrado);
    }

    @PostMapping ("/servico/listarvendedor")
    public ResponseEntity<VendedorDTO> criar (@RequestBody VendedorDTO vendedor) {

        vendedor.setIdVendedor(Long.MIN_VALUE);
               
        vendedores.add(vendedor);

        return ResponseEntity.status(201).body(vendedor);
    }

    @DeleteMapping ("/servico/listarvendedor/{id}")
    public ResponseEntity excluir (@PathVariable int id) {
        
        if (vendedores.removeIf(vendedor -> vendedor.getIdVendedor() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/listarvendedor/{id}")
    public ResponseEntity<VendedorDTO> alterar (@PathVariable int id, @RequestBody VendedorDTO vendedor) {
        Optional<VendedorDTO> vendedorExistente = vendedores.stream().filter(p -> p.getIdVendedor()== id).findAny();

        vendedorExistente.ifPresent(p -> {
            p.setNomeVendedor(vendedor.getNomeVendedor());                    
        });

        return ResponseEntity.of(vendedorExistente);
    }
    
}
