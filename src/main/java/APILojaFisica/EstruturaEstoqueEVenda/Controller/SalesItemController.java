package APILojaFisica.EstruturaEstoqueEVenda.Controller;

import APILojaFisica.EstruturaEstoqueEVenda.Business.SalesItemService;
import APILojaFisica.EstruturaEstoqueEVenda.Business.SalesOrderService;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.SalesItem;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.SalesItemRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salesItem")
public class SalesItemController {

    @Autowired
    private SalesItemService salesItemService;

    @Autowired
    private SalesItemRepository salesItemRepository;

    @GetMapping
    public ResponseEntity<List<SalesItem>> findAllSalesItem(){
        return ResponseEntity.ok(salesItemRepository.findAll());
    }

    @GetMapping("/id")
    public ResponseEntity<SalesItem> findSalesItemById(@RequestParam  int id){
        return ResponseEntity.ok(salesItemService.findSalesItemById(id));
    }

    @GetMapping("/productId")
    public ResponseEntity<List<SalesItem>> findSalesItemByProductId(@RequestParam int id){
        return ResponseEntity.ok(salesItemService.findSalesItemByProductId_Id(id));
    }

    @GetMapping("/salesOrderId")
    public ResponseEntity<List<SalesItem>> findSalesItemBySalesOrder(@RequestParam int id){
        return ResponseEntity.ok(salesItemService.findSalesItemBySalesOrderId_Id(id));
    }

    @PutMapping
    public ResponseEntity<Void> refreshSalesItemById(@RequestBody SalesItem salesItem, @RequestParam int id){
        salesItemService.refreshSalesItemById(id, salesItem);
        return ResponseEntity.ok().build();
    }
}
