package br.com.demo.regescweb.controllers;

import br.com.demo.regescweb.models.Cliente;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    @Transactional
    public List<Cliente> listarClientes() {
        return entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    @PostMapping
    @Transactional
    public Cliente salvarCliente(@RequestBody Cliente cliente) {
        if (cliente.getId() == null) {
            entityManager.persist(cliente);
        } else {
            entityManager.merge(cliente);
        }
        return cliente;
    }

    @GetMapping("/{id}")
    @Transactional 
    public Optional<Cliente> verCliente(@PathVariable Long id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        return Optional.ofNullable(cliente);
    }

    @PutMapping("/{id}")
    @Transactional
    public Cliente atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        entityManager.merge(cliente);
        return cliente;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletarCliente(@PathVariable Long id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        if (cliente != null) {
            entityManager.remove(cliente);
        }
    }
}
