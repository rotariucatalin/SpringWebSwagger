package com.example.SpringWeb;

import com.example.SpringWeb.model.Product;
import com.example.SpringWeb.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SpringMockMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    public static final String URL = "/products/";
    private static final int ID = 1;
    private static final String NAME = "MackBook";
    public static final String DESCRIPTION = "Description";
    private static final int PRICE = 1000;

    @Test
    public void findAll() throws Exception {

        Product product = createNewProduct();

        List<Product> products = Arrays.asList(product);

        when(productRepository.findAll()).thenReturn(products);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc
                .perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(products)));
    }

    @Test
    public void getOneProduct() throws Exception {

        Product product = createNewProduct();
        when(productRepository.findById(ID)).thenReturn(Optional.of(product));
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(get(URL+ID)).andExpect(status().isOk()).andExpect(content().json(objectWriter.writeValueAsString(product)));
    }

    @Test
    public void testCreateNewProduct() throws Exception {

        Product newProduct = createNewProduct();
        when(productRepository.save(any())).thenReturn(newProduct);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();


        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(newProduct)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(newProduct)));

    }

    @Test
    public void testUpdateProduct() throws Exception {

        Product newProduct = createNewProduct();
        newProduct.setPrice(10);
        when(productRepository.save(any())).thenReturn(newProduct);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();


        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(newProduct)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(newProduct)));

    }

    @Test
    public void deleteProduct() throws Exception {

        doNothing().when(productRepository).deleteById(ID);

        mockMvc
                .perform(delete(URL+ID))
                .andExpect(status().isOk());
    }

    private Product createNewProduct() {

        Product product = new Product();
        product.setId(ID);
        product.setName(NAME);
        product.setDescription(DESCRIPTION);
        product.setPrice(PRICE);

        return product;
    }
}
