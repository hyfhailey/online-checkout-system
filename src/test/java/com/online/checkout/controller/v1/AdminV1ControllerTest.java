package com.online.checkout.controller.v1;

import com.online.checkout.OnlineCheckoutSystemApplication;
import com.online.checkout.config.MockDataConfig;
import com.online.checkout.data.repository.ProductRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static com.online.checkout.util.TestObjects.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OnlineCheckoutSystemApplication.class, MockDataConfig.class})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class AdminV1ControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductRepository productRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @SneakyThrows
    @Test
    public void testAddNewProduct() {
        String requestBody = "{\"name\": \"%s\", \"description\": \"%s\", \"price\": %S}"
                .formatted(PRODUCT_NAME, PRODUCT_DESCRIPTION, PRICE);
        when(productRepository.findByNameAndDescriptionAndPrice(PRODUCT_NAME, PRODUCT_DESCRIPTION, PRICE))
                .thenReturn(Optional.empty());

        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/v1/admin/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(post).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void testRemoveProduct() {
        String requestBody = "{\"productId\": \"%s\"}".formatted(PRODUCT_ID);

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(PRODUCT));

        MockHttpServletRequestBuilder delete = MockMvcRequestBuilders.delete("/v1/admin/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(delete).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void testAddNewDiscount() {
        String requestBody = "{\"productId\": \"%s\", \"discountCode\": \"%s\", \"applyAlone\": true}"
                .formatted(PRODUCT_ID, PERCENT_DISCOUNT_CODE);
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(PRODUCT));

        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/v1/admin/product-discounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(post).andExpect(status().isOk());
    }
}
