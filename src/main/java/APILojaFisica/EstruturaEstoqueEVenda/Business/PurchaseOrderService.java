package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.*;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.ProductRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.PurchaseOrderRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final ProductService productService;
    private final StockService stockService;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, SupplierRepository supplierRepository, ProductService productService, StockService stockService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.productService = productService;
        this.stockService = stockService;
    }

    public PurchaseOrder findPurchaseOrderById(int id){
        return purchaseOrderRepository.findPurchseOrderById(id).orElseThrow(
                () -> new RuntimeException("Purchase Order Id is not found")
        );
    }

    public void deletePurchaseOrderById(int id){
        if(!purchaseOrderRepository.existsById(id)){
            throw new RuntimeException("Id insert not found");
        }
        purchaseOrderRepository.deletePurchaseOrderById(id);
    }

    public void insertNewPurchaseOrder(PurchaseOrder purchaseOrder){
        purchaseOrder.updateTotal();
        purchaseOrder.defDate();
        int supplierId = purchaseOrder.getIdSupplier().getId();
        Supplier supplier = supplierRepository.findSupplierById(supplierId).orElseThrow(
                () -> new RuntimeException("Supplier not exists")
        );
        if(purchaseOrder.getDateTime().isAfter(LocalDateTime.now())){
            throw new RuntimeException("The date insert  id after date and time now");
        }
        purchaseOrder.getItems().forEach(item -> {
            int productId = item.getProductId().getId();
            Product product = productService.findProductById(productId);
            Stock stock = stockService.findByStockByIdProduct(productId);
            int quantityStock = stock.getQuantity();
            stock.setQuantity(quantityStock + item.getQuantity());

            if(item.getQuantity() < 0){
                throw new RuntimeException("Quantity insert is invalid");
            }
            item.setPriceUnity(product.getSalePrice());
            item.setProductId(product);
            item.setPurchaseOrderId(purchaseOrder);

        });
        purchaseOrder.setIdSupplier(supplier);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
    }

    public void refreshPurchaseOrderById(PurchaseOrder purchaseOrder, int id){
        PurchaseOrder actualPurchaseOrder = findPurchaseOrderById(id);

        // Atualiza o Fornecedor (se foi enviado um novo)
        if (purchaseOrder.getIdSupplier() != null){
            actualPurchaseOrder.setIdSupplier(purchaseOrder.getIdSupplier());
        }

        // Atualiza o status
        if (purchaseOrder.getStatus() != null){
            actualPurchaseOrder.setStatus(purchaseOrder.getStatus());
        }

        if (purchaseOrder.getItems() != null){
            actualPurchaseOrder.getItems().clear();

            for (PurchaseItem item : purchaseOrder.getItems()){
                int productId = item.getProductId().getId();
                Product product = productService.findProductById(productId);

                if(item.getQuantity() < 0){
                    throw new RuntimeException("Quantity insert is invalid");
                }

                item.setPriceUnity(product.getSalePrice());
                item.setProductId(product);
                item.setPurchaseOrderId(actualPurchaseOrder);

                purchaseOrder.getItems().add(item);
            }
        }

        actualPurchaseOrder.updateTotal();

        purchaseOrderRepository.saveAndFlush(actualPurchaseOrder);
    }
}
