package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product){
        double cost = product.getCost();
        int minimumStock = product.getMinimumStock();
        double salePrice = product.getSalePrice();

        if(cost < 0){
            throw new RuntimeException("The cost cannot be less than zero");
        } else if (minimumStock < 0){
            throw new RuntimeException("The minimum stock cannot be less than zero");
        } else if (salePrice < cost){
            throw new RuntimeException("The sale Price cannot be less than cost");
        }
        productRepository.saveAndFlush(product);
    }

    public Product findProductById(int id){
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Id not exist")
        );
    }

    public void deleteProductByName(String nome){
        productRepository.deleteByName(nome);
    }

    public void refreshProduct(int id, Product product){
        Product actualValue = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("id not exists")
        );
        Product newValue = Product.builder()
                .id(product.getId())
                .sku(product != null && product.getSku() != null ? product.getSku() : actualValue.getSku())
                .name(product != null && product.getName() != null ? product.getName() : actualValue.getName())
                .description(product != null && product.getDescription() != null ? product.getDescription() : actualValue.getDescription())
                .cost(product != null && product.getCost() != null ? product.getCost() : actualValue.getCost())
                .salePrice(product != null && product.getSalePrice() != null ? product.getSalePrice() : actualValue.getSalePrice())
                .minimumStock(product != null && product.getMinimumStock() != null ? product.getMinimumStock() : actualValue.getMinimumStock())
                .build();
        saveProduct(newValue);
    }


}
