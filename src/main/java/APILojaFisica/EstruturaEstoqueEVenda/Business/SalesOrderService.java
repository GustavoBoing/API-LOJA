package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.*;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.SalesOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SalesOrderService {
    private final SalesOrderRepository salesOrderRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final StockService stockService;

    public SalesOrderService(SalesOrderRepository salesOrderRepository, ProductService productService, CustomerService customerService, StockService stockService) {
        this.salesOrderRepository = salesOrderRepository;
        this.productService = productService;
        this.customerService = customerService;
        this.stockService = stockService;
    }

    public SalesOrder findSalesOrderById(int id){
        return salesOrderRepository.findSalesOrderById(id).orElseThrow(
                () -> new RuntimeException("SalesOrder Id is not found")
        );
    }

    public void saveSalesOrder(SalesOrder salesOrder){
        int customerId = salesOrder.getCustomerId().getId();
        Customer customer = customerService.findCustomerById(customerId);

        salesOrder.getItems().forEach(item -> {
            int productId = item.getProductId().getId();
            Product product = productService.findProductById(productId);
            Stock stock = stockService.findByStockByIdProduct(productId);

            if(item.getQuantity() < 0 || stock.getQuantity() < item.getQuantity()){
                throw new RuntimeException("Quantity insert is invalid");
            }

            stock.setQuantity(stock.getQuantity() - item.getQuantity());
            item.setProductId(product);
            item.setPriceUnit(product.getSalePrice());
            item.setSalesOrderId(salesOrder);
        });
        salesOrder.setCustomerId(customer);
        salesOrder.setDate(LocalDateTime.now());
        salesOrder.updateTotal();
        salesOrderRepository.saveAndFlush(salesOrder);
    }

    public void deleteSalesOrderById(int id){
        if(!salesOrderRepository.existsById(id)){
            throw new RuntimeException("Id insert not found");
        }
        salesOrderRepository.deleleSalesOrderById(id);
    }

    public void refreshSalesOrder(int id, SalesOrder salesOrder){
        SalesOrder actualSalesOrder = findSalesOrderById(id);

        if(salesOrder.getCustomerId() != null){
            actualSalesOrder.setCustomerId(salesOrder.getCustomerId());
        }

        if(salesOrder.getStatus() != null){
            actualSalesOrder.setStatus(salesOrder.getStatus());
        }

        if(salesOrder.getItems() != null){
            actualSalesOrder.getItems().clear();
            for (SalesItem item : salesOrder.getItems()) {
                int productId = item.getProductId().getId();
                Product product = productService.findProductById(productId);
                Stock stock = stockService.findByStockByIdProduct(productId);

                if(item.getQuantity() < 0 || stock.getQuantity() < item.getQuantity()){
                    throw new RuntimeException("Quantity insert is invalid");
                }

                stock.setQuantity(stock.getQuantity() - item.getQuantity());
                item.setProductId(product);
                item.setPriceUnit(product.getSalePrice());
                item.setSalesOrderId(salesOrder);
            }
        }

        salesOrder.updateTotal();
        
        salesOrderRepository.saveAndFlush(salesOrder);
    }
}
