package APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional <Customer> findCustomerByName(String name);

    Optional <Customer> findCustomerById(int id);

    @Transactional
    void deleteCustomerById(int id);
}
