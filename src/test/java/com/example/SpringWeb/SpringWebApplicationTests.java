package com.example.SpringWeb;

import com.example.SpringWeb.model.Product;
import com.example.SpringWeb.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringWebApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	void findAllProducts() {

		List<Product> getAllProducts = productRepository.findAll();

		assertNotNull(getAllProducts);
	}

	@Test
	void createNewProduct() {

		Product product = new Product();

		product.setName("test3");
		product.setDescription("test3");
		product.setPrice(200);

		Product savedProduct = productRepository.save(product);

		assertNotNull(savedProduct);
	}

	@Test
	void testProductName() {

		RestTemplate restTemplate = new RestTemplate();

		Product product = restTemplate.getForObject("http://localhost:8080/products/1", Product.class);

		assertEquals("test", product.getName());
	}

	@Test
	public void testCreateProduct() {

		RestTemplate restTemplate = new RestTemplate();

		Product product = new Product();
		product.setName("test5");
		product.setDescription("test5");
		product.setPrice(600);

		Product newProduct = restTemplate.postForObject("http://localhost:8080/products/", product, Product.class);

		assertNotNull(newProduct);
		assertEquals("test5", newProduct.getName());

	}

	@Test
	public void updateProduct() {

		RestTemplate restTemplate = new RestTemplate();

		Product oldProduct = restTemplate.getForObject("http://localhost:8080/products/1", Product.class);
		oldProduct.setPrice(1000);

		restTemplate.put("http://localhost:8080/products/", oldProduct);
	}
}
