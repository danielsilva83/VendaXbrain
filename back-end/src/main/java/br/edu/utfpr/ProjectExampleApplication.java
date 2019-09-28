package br.edu.utfpr;

import br.edu.utfpr.dao.VendaDAO;
import br.edu.utfpr.dao.VendedorDAO;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectExampleApplication implements CommandLineRunner {

  
    private final  VendedorDAO vendedorDAO; 
    private final  VendaDAO vendaDAO; 

    @Autowired
    public ProjectExampleApplication(VendedorDAO vendedorDAO,VendaDAO vendaDAO) {
        this.vendedorDAO = vendedorDAO;
        this.vendaDAO = vendaDAO;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

                
    }

}
