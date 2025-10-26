package APILojaFisica.EstruturaEstoqueEVenda.Controller;

import APILojaFisica.EstruturaEstoqueEVenda.Business.PurchaseItemService;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.PurchaseItem;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.PurchaseOrder;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.PurchaseItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchaseItem")

public class PurchaseItemController {

    private final PurchaseItemService purchaseItemService;
    private final PurchaseItemRepository purchaseItemRepository;

    public PurchaseItemController(PurchaseItemService purchaseItemService, PurchaseItemRepository purchaseItemRepository) {
        this.purchaseItemService = purchaseItemService;
        this.purchaseItemRepository = purchaseItemRepository;
    }

    @GetMapping("/id")
    public ResponseEntity<PurchaseItem> findItemById(@RequestParam int id){
        return ResponseEntity.ok(purchaseItemService.findPurchaseItemById(id));
    }

    @GetMapping("/purchaseOrderId/{id}")
    public ResponseEntity<List<PurchaseItem>> findItemByPurchaseOrderId(@PathVariable int id){
        return ResponseEntity.ok(purchaseItemService.findPurchaseItemByPurchaseOrderId(id));
    }

    @GetMapping("/productId/{id}")
    public ResponseEntity<List<PurchaseItem>> findItemByProductId(@PathVariable int id){
        return ResponseEntity.ok(purchaseItemService.findPurchaseItemByProductId(id));
    }

    @GetMapping
    public List<PurchaseItem> listItemProducts(){
          return purchaseItemRepository.findAll();
    }

    @PutMapping
    public ResponseEntity<Void> refreshItemById(@RequestBody PurchaseItem purchaseItem, @RequestParam int id){
        purchaseItemService.refreshPurchaseItemById(purchaseItem, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePurchaseItemById(@RequestParam int id){
        purchaseItemService.deletePurchaseItemById(id);
        return ResponseEntity.ok().build();
    }
}
