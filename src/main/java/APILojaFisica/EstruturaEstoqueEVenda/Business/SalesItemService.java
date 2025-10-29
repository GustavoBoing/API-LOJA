package APILojaFisica.EstruturaEstoqueEVenda.Business;

import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.SalesItem;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Stock;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.SalesItemRepository;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SalesItemService {
    private final SalesItemRepository salesItemRepository;
    private final StockService stockService;

    public SalesItemService(SalesItemRepository salesItemRepository, StockService stockService) {
        this.salesItemRepository = salesItemRepository;
        this.stockService = stockService;
    }

    public SalesItem findSalesItemById(int id){
        return salesItemRepository.findSalesItemById(id).orElseThrow(
                () ->  new RuntimeException("id insert is not found")
        );
    }

    public List<SalesItem> findSalesItemByProductId_Id(int id){
        List<SalesItem> items = salesItemRepository.findSalesItemByProductId_Id(id);

        if(items.isEmpty()){
            throw new RuntimeException("Id product is not found");
        }

        return items;
    }

    public List<SalesItem> findSalesItemBySalesOrderId_Id(int id){
        List<SalesItem> items = salesItemRepository.findSalesItemBySalesOrderId_Id(id);

        if(items.isEmpty()){
            throw new RuntimeException("Id salesOrder not found");
        }

        return items;
    }

    public void refreshSalesItemById(int id, SalesItem salesItem){
        SalesItem actualSalesItem = findSalesItemById(id);
        int idProduct = salesItem.getProductId().getId();
        Stock stockActual = stockService.findByStockByIdProduct(idProduct);

        if(salesItem.getQuantity() != null){
            stockActual.setQuantity(stockActual.getQuantity() + actualSalesItem.getQuantity());
            stockActual.setQuantity(stockActual.getQuantity() - salesItem.getQuantity());
            actualSalesItem.setQuantity(salesItem.getQuantity());
        }

        salesItemRepository.saveAndFlush(actualSalesItem);
    }
}
