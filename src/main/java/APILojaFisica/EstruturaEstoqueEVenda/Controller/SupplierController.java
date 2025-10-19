package APILojaFisica.EstruturaEstoqueEVenda.Controller;

import APILojaFisica.EstruturaEstoqueEVenda.Business.SupplierService;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Supplier;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")

public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping
    public List<Supplier> listSuppliers(){ return supplierRepository.findAll(); }

    @GetMapping ("/searchSupplier")
    public Supplier findSupplierByName(@RequestParam String name){ return supplierService.findSupplierByName(name); }

    @PostMapping
    public void saveNewSupplier(@RequestBody Supplier supplier){ supplierService.saveSupplier(supplier); }

    @DeleteMapping
    public void deleteSuppllierById(@RequestParam int id){ supplierService.deleteSupplierById(id); }

    @PutMapping
    public void refreshSupplier(@RequestParam int id, @RequestBody Supplier supplier){ supplierService.refreshSupplierById(id, supplier); }
}
