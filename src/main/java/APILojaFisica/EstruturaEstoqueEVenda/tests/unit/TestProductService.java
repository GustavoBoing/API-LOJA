package APILojaFisica.EstruturaEstoqueEVenda.tests.unit;

import APILojaFisica.EstruturaEstoqueEVenda.Business.ProductService;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Entities.Product;
import APILojaFisica.EstruturaEstoqueEVenda.Infraestructure.Repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.FluentQuery;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TestProductService {

    @Test
    public void deveLancarExcecaoQuandoEstoqueMinimoForMenorQueZero(){

        // 1. Criar o mock do repositório (para não dar NullPointerException)
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        ProductService service = new ProductService(repository);

        // 2. Criar o objeto Produto
        Product produtoInvalido = new Product();
        produtoInvalido.setSku("prod1");
        produtoInvalido.setName("Teclado");
        produtoInvalido.setDescription("Teclado Gamer");
        produtoInvalido.setCost(1000.0);
        produtoInvalido.setSalePrice(1200.0);
        produtoInvalido.setMinimumStock(-20);

        // 3. Verificar se a exceção correta é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.saveProduct(produtoInvalido);
        });

        // 4. Validar mensagem da Exceção
        assertEquals("The minimum stock cannot be less than zero", exception.getMessage());
    }

    @Test
    public void develancarExcecaoQuandoCustoForMaiorQuePreco() {
        //1. Criar o mock do repositorio porque não é uma função simples
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        ProductService service = new ProductService(repository);

        //2. Criar o objeto do produto
        Product produtoInvalido = new Product();
        produtoInvalido.setSku("prod1");
        produtoInvalido.setName("Teclado");
        produtoInvalido.setDescription("Teclado Gamer");
        produtoInvalido.setCost(1300.0);
        produtoInvalido.setSalePrice(1200.0);
        produtoInvalido.setMinimumStock(20);

        //3. Verificar qual exceção é lançada no erro do código
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           service.saveProduct(produtoInvalido);
        });

        //4. verificar se a exception é igual ao erro esperado
        assertEquals("The sale Price cannot be less than cost", exception.getMessage());
    }


    @Test
    public void deveCancelarOperacaoSeOCustoForMenorQueZero(){
        //1. Criar o mock para poder criar uma variavel service
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        ProductService service = new ProductService(repository);

        //2. Criar uma classe product
        Product product = new Product();
        product.setSku("prod2");
        product.setName("Mousepad");
        product.setDescription("Mousepad gamer com led");
        product.setCost(-30.0);
        product.setSalePrice(500.0);
        product.setMinimumStock(10);

        //3. Verificar exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.saveProduct(product);
        });

        //4. Verificar se o teste está ok
        assertEquals("The cost cannot be less than zero", exception.getMessage());
    }



    @Test
    public void verificarSeSaveAndFlushFoiExecutado(){
        //1. Criação do Mock
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        ProductService service = new ProductService(repository);

        //2. Criar uma classe product
        Product product = new Product();
        product.setSku("prod2");
        product.setName("Mousepad");
        product.setDescription("Mousepad gamer com led");
        product.setCost(30.0);
        product.setSalePrice(70.0);
        product.setMinimumStock(10);

        service.saveProduct(product);

        Mockito.verify(repository).saveAndFlush(product);
    }

    @Test
    public void deveRetornarOErroNãoEncontrou(){
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        ProductService service = new ProductService(repository);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           service.findProductByName("Teclado");
        });

        assertEquals("name not exist", exception.getMessage());
    }

    @Test
    public void deveFazerFindByName(){
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        ProductService service = new ProductService(repository);

        String name = "Teclado";

        //2. Criar uma classe product
        Product product = new Product();
        product.setSku("prod2");
        product.setName("Teclado");
        product.setDescription("Mousepad gamer com led");
        product.setCost(30.0);
        product.setSalePrice(70.0);
        product.setMinimumStock(10);

        when(repository.findByName(name)).thenReturn(Optional.of(product));

        service.findProductByName("Teclado");

        Mockito.verify(repository).findByName("Teclado");
    }


















}
