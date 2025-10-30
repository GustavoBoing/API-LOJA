package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.*;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.ProductRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.PurchaseOrderRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        return purchaseOrderRepository.findPurchaseOrderById(id).orElseThrow(
                () -> new RuntimeException("Purchase Order Id is not found")
        );
    }

    public List<PurchaseOrder> findPurchaseOrderByIdSupplier(int id){
        List<PurchaseOrder> items = purchaseOrderRepository.findPurchaseOrderByIdSupplier_Id(id);

        if(items.isEmpty()){
            throw new RuntimeException("Id Supplier not found");
        }

        return items;
    }

    public void deletePurchaseOrderById(int id){
        if(!purchaseOrderRepository.existsById(id)){
            throw new RuntimeException("Id insert not found");
        }
        purchaseOrderRepository.deletePurchaseOrderById(id);
    }

    public void insertNewPurchaseOrder(PurchaseOrder purchaseOrder){
        int supplierId = purchaseOrder.getIdSupplier().getId();
        Supplier supplier = supplierRepository.findSupplierById(supplierId).orElseThrow(
                () -> new RuntimeException("Supplier not exists")
        );
        purchaseOrder.setDateTime(LocalDateTime.now());
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
        purchaseOrder.updateTotal();
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
            for (PurchaseItem item : purchaseOrder.getItems()){
                int productId = item.getProductId().getId();
                Product product = productService.findProductById(productId);
                Stock stock = stockService.findByStockByIdProduct(productId);

                if(item.getQuantity() < 0){
                    throw new RuntimeException("Quantity insert is invalid");
                }

                // Procura o item antigo no pedido, se existir
                PurchaseItem oldItem = actualPurchaseOrder.getItems().stream()
                        .filter(i -> i.getProductId().getId() == productId)
                        .findFirst()
                        .orElse(null);

                int oldQuantity = (oldItem != null) ? oldItem.getQuantity() : 0;
                int newQuantity = item.getQuantity();

                int getDifference = newQuantity - oldQuantity;

                stock.setQuantity(stock.getQuantity() + getDifference);

                // Atualiza o item no pedido
                if (oldItem != null) {
                    oldItem.setQuantity(newQuantity);
                    oldItem.setPriceUnity(product.getSalePrice());
                } else {
                    item.setPriceUnity(product.getSalePrice());
                    item.setProductId(product);
                    item.setPurchaseOrderId(actualPurchaseOrder);
                    actualPurchaseOrder.getItems().add(item);
                }
                /*
                item.setPriceUnity(product.getSalePrice());
                item.setProductId(product);
                item.setPurchaseOrderId(actualPurchaseOrder);

                actualPurchaseOrder.getItems().add(item);
                */
            }
        }

        actualPurchaseOrder.updateTotal();

        purchaseOrderRepository.saveAndFlush(actualPurchaseOrder);
    }
}
