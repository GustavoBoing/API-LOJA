package APILojaFisica.EstruturaEstoqueEVenda.Controller;

import APILojaFisica.EstruturaEstoqueEVenda.Business.PurchaseOrderService;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.PurchaseOrder;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping ("/purchaseOrderById")
    public ResponseEntity<PurchaseOrder> findPurchaseOrderById(@RequestParam int id){
        return ResponseEntity.ok(purchaseOrderService.findPurchaseOrderById(id));
    }

    @GetMapping
    public List<PurchaseOrder> listPurchaseOrder(){ return purchaseOrderRepository.findAll(); }

    @PostMapping
    public ResponseEntity<Void> savePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        purchaseOrderService.insertNewPurchaseOrder(purchaseOrder);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePurchaseOrder(@RequestParam int id){
        purchaseOrderService.deletePurchaseOrderById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> refreshPurchaseOrder(@RequestParam int id, @RequestBody PurchaseOrder purchaseOrder){
        purchaseOrderService.refreshPurchaseOrderById(purchaseOrder, id);
        return ResponseEntity.ok().build();
    }

}
