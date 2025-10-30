package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Supplier;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.SupplierRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier findSupplierById(int id){
        return supplierRepository.findSupplierById(id).orElseThrow(
                () -> new RuntimeException("Supplier Id is invalid")
        );
    }

    public Supplier findSupplierByName(String name){
        return supplierRepository.findSupplierByName(name).orElseThrow(
                () -> new RuntimeException("Supplier name is invalid")
        );
    }

    public void saveSupplier(Supplier supplier){ supplierRepository.saveAndFlush(supplier); }

    public void deleteSupplierById(int id){ supplierRepository.deleteSupplierById(id); }

    public void refreshSupplierById(int id, Supplier dataSupplier){
        Supplier actualDataSupplier = findSupplierById(id);
        Supplier newDataSupplier = Supplier.builder()
                .id(actualDataSupplier.getId())
                .name(dataSupplier != null && dataSupplier.getName() != null ? dataSupplier.getName() : actualDataSupplier.getName())
                .phone(dataSupplier != null && dataSupplier.getPhone() != null ? dataSupplier.getPhone() : actualDataSupplier.getPhone())
                .email(dataSupplier != null && dataSupplier.getEmail() != null ? dataSupplier.getPhone() : actualDataSupplier.getEmail())
                .build();
        supplierRepository.saveAndFlush(newDataSupplier);
    }
}

