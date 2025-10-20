package APILojaFisica.EstruturaEstoqueEVenda.Controller;

import APILojaFisica.EstruturaEstoqueEVenda.Business.ProductService;
import APILojaFisica.EstruturaEstoqueEVenda.Business.StockService;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Stock;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.StockRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockRepository stockRepository;
    private final StockService stockService;

    public StockController(StockRepository stockRepository, StockService stockService) {
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    @GetMapping("/searchStockByProduct")
    public ResponseEntity<Optional<Stock>> findStockByIdProduct(@RequestParam int productId){
        return ResponseEntity.ok(stockRepository.findByProductId(productId));
    }

    @GetMapping("/searchStockById")
    public ResponseEntity<Stock> findStockById(@RequestParam int id){
        return ResponseEntity.ok(stockService.findById(id));
    }

    @GetMapping
    public List<Stock> listarStock(){
        return stockRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> saveStock(@RequestBody Stock stock){
        stockService.saveStock(stock);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStockById(@RequestParam int id){
        stockService.deleteStockById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> refreshStockById(@RequestParam int id, @RequestBody Stock stock){
        stockService.refreshStockById(id, stock);
        return ResponseEntity.ok().build();
    }
}
