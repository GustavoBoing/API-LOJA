package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Customer;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerByName(String name){
        return customerRepository.findCustomerByName(name).orElseThrow(
                () -> new RuntimeException("Name is not found")
        );
    }

    public void saveCustomer(Customer customer){
        customerRepository.saveAndFlush(customer);
    }

    public void deleteCustomerById(int id){
        customerRepository.deleteCustomerById(id);
    }

    public void updateCustomerById(int id, Customer newDataCustomer){
        Customer actualDataCustomer = customerRepository.findCustomerById(id).orElseThrow(
                () -> new RuntimeException("Id customer is invalid")
        );
        Customer refreshCustomer = Customer.builder()
                .id(actualDataCustomer.getId())
                .name(newDataCustomer != null && newDataCustomer.getName() != null ? newDataCustomer.getName() : actualDataCustomer.getName())
                .numberCellPhone(newDataCustomer != null && newDataCustomer.getNumberCellPhone() != null ? newDataCustomer.getNumberCellPhone() : actualDataCustomer.getNumberCellPhone())
                .email(newDataCustomer != null && newDataCustomer.getEmail() != null ? newDataCustomer.getEmail() : actualDataCustomer.getEmail())
                .build();

        customerRepository.saveAndFlush(refreshCustomer);
    }

}
