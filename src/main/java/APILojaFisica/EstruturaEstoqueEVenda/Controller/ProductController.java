package APILojaFisica.EstruturaEstoqueEVenda.Controller;

import APILojaFisica.EstruturaEstoqueEVenda.Business.ProductService;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/product")

public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping ("/searchProduct")
    public Product findProductByName(@RequestParam int id){
        return productService.findProductById(id);
    }

    @GetMapping
    public List<Product> listProducts(){
        return productRepository.findAll();
    }

    @PostMapping
    public void saveProduct(@RequestBody Product product){
        productService.saveProduct(product);
    }

    @DeleteMapping
    public void deleteProductBy(@RequestParam String nome){
        productService.deleteProductByName(nome);
    }

    @PutMapping
    public void refreshProduct(@RequestParam int id, @RequestBody Product product){
        productService.refreshProduct(id, product);
    }

}
