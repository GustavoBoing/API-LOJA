package APILojaFisica.EstruturaEstoqueEVenda.Controller;

import APILojaFisica.EstruturaEstoqueEVenda.Business.CustomerService;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Customer;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping ("/searchCustomer")
    public Customer findCustomerByName(@RequestParam String name){ return customerService.findCustomerByName(name); }

    @GetMapping
    public List<Customer> listCustomers(){ return customerRepository.findAll(); }

    @PostMapping
    public void saveCustomer(@RequestBody Customer customer){ customerService.saveCustomer(customer); }

    @DeleteMapping
    public void deleteCustomerById(@RequestParam int id){ customerService.deleteCustomerById(id); }

    @PutMapping
    public void refreshCustomer(@RequestParam int id, @RequestBody Customer customer){ customerService.updateCustomerById(id, customer); }
}
