package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Stock;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.ProductRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.StockRepository;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public StockService(StockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    public Stock findByStockByIdProduct(int productId){
        return stockRepository.findByProductId(productId).orElseThrow(
                () -> new RuntimeException("IdStock not found")
        );
    }

    public Stock findById(int id){
        return stockRepository.findById(id).orElseThrow(
                () -> new RuntimeException("IdStock not found")
        );
    }

    public void saveStock(Stock stock){
        Product product = productRepository.getReferenceById(stock.getProduct().getId());
        Integer quantityMinimumProduct = product.getMinimumStock();
        if(stock.getQuantity() < 0){
            throw new RuntimeException("Quantity insert is invalid");
        }
        if(quantityMinimumProduct < stock.getQuantity()){
            new Thread(() -> {
                JOptionPane.showMessageDialog(null, "The quantity entered is less than the minimum quantity of the product");
            }).start();
        }
        stockRepository.saveAndFlush(stock);
    }

    public void deleteStockById(int id){
        stockRepository.deleteById(id);
    }

    public void refreshStockById(int id, Stock stock){
        Stock actualStock = findById(id);
        Stock newDataStock = Stock.builder()
                .id(actualStock.getId())
                .product(actualStock.getProduct())
                .quantity(stock != null && stock.getQuantity() != null ? stock.getQuantity() : actualStock.getQuantity())
                .storageUnity(stock != null && stock.getStorageUnity() != null ? stock.getStorageUnity() : actualStock.getStorageUnity())
                .locale(stock != null && stock.getLocale() != null ? stock.getLocale() : actualStock.getLocale())
                .build();
        saveStock(newDataStock);
    }

}
