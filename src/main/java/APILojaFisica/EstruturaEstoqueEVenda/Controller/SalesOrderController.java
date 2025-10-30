package APILojaFisica.EstruturaEstoqueEVenda.Controller;

import APILojaFisica.EstruturaEstoqueEVenda.Business.SalesOrderService;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.SalesOrder;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.SalesOrderRepository;
import org.hibernate.boot.model.internal.CreateKeySecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salesOrder")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @GetMapping ResponseEntity<List<SalesOrder>> listAllSalesOrder(){
        return ResponseEntity.ok(salesOrderRepository.findAll());
    }

    @GetMapping("/specifySalesOrder")
    public ResponseEntity<SalesOrder> findSalesOrderByIdSalesOrder(@RequestParam int id){
        return ResponseEntity.ok(salesOrderService.findSalesOrderById(id));
    }

    @GetMapping("/specifyCustomer")
    public ResponseEntity<List<SalesOrder>> findSalesOrderByIdCustomer(@RequestParam int id){
        return ResponseEntity.ok(salesOrderService.findSalesOrderByIdCustomer(id));
    }

    @PostMapping
    public ResponseEntity<Void> saveNewSalesOrder(@RequestBody SalesOrder salesOrder){
        salesOrderService.saveSalesOrder(salesOrder);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSalesOrder(@RequestParam int id){
        salesOrderService.deleteSalesOrderById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> refreshSalesOrderById(@RequestParam int id, @RequestBody SalesOrder salesOrder){
        salesOrderService.refreshSalesOrder(id, salesOrder);
        return ResponseEntity.ok().build();
    }
}