package com.example.SpringWeb.controller;

import com.example.SpringWeb.model.Product;
import com.example.SpringWeb.repositories.ProductRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductRestController {

    @Autowired
    ProductRepository productRepository;

    @ApiOperation(
                    value = "Retrieves all the products",
                    notes = "A list of products",
                    response = Product.class,
                    responseContainer = "List",
                    produces = "application/json")
    @RequestMapping(value = "/products/", method = RequestMethod.GET)
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProduct( @PathVariable("id") int id ) {

        return productRepository.findById(id).get();
    }

    @RequestMapping(value = "/products/", method = RequestMethod.POST)
    public Product createNewProduct(@Valid @RequestBody Product product) {

        return productRepository.save(product);
    }

    @RequestMapping(value = "/products/", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product) {

        return productRepository.save(product);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable("id") int id) {

        productRepository.deleteById(id);
    }
}
