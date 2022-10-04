package com.online.checkout.controller.v1;

import com.online.checkout.OnlineCheckoutSystemApplication;
import com.online.checkout.config.MockDataConfig;
import com.online.checkout.data.dto.CustomerDto;
import com.online.checkout.data.repository.BasketItemsRepository;
import com.online.checkout.data.repository.BasketRepository;
import com.online.checkout.data.repository.CustomerRepository;
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
import java.util.UUID;

import static com.online.checkout.util.TestObjects.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OnlineCheckoutSystemApplication.class, MockDataConfig.class})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class CustomerV1ControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketItemsRepository basketItemsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @SneakyThrows
    @Test
    public void testAddToBasket() {
        String requestBody = "{\"customerId\": \"44ad09d0-4401-11ed-b878-0242ac120002\", \"productId\": 1, \"quantity\": 3}";
        when(customerRepository.findById(UUID.fromString("44ad09d0-4401-11ed-b878-0242ac120002")))
                .thenReturn(Optional.of(new CustomerDto()));
        when(productRepository.findById(1)).thenReturn(Optional.of(PRODUCT));

        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/v1/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(post).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void testRemoveBasket() {
        String requestBody = "{\"customerId\": \"%s\", \"basketId\": \"%s\", \"basketItemId\": \"%s\"}"
                .formatted(CUSTOMER_ID, BASKET_ID, BASKET_ITEM_ID);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(new CustomerDto()));
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(basketItemsRepository.findById(BASKET_ITEM_ID)).thenReturn(Optional.of(BASKET_ITEM));
        when(basketRepository.findByIdAndCustomerId(BASKET_ID, CUSTOMER_ID)).thenReturn(Optional.of(BASKET));
        when(basketItemsRepository.findByIdAndBasket_Id(BASKET_ITEM_ID, BASKET_ID)).thenReturn(Optional.of(BASKET_ITEM));

        MockHttpServletRequestBuilder delete = MockMvcRequestBuilders.delete("/v1/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(delete).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void testGetReceipt() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(new CustomerDto()));
        when(basketRepository.findById(BASKET_ID)).thenReturn(Optional.of(BASKET));
        when(basketRepository.findByIdAndCustomerId(BASKET_ID, CUSTOMER_ID)).thenReturn(Optional.of(BASKET));

        String url = "/v1/receipts/%s/%s".formatted(CUSTOMER_ID, BASKET_ID);
        MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get(url);

        mockMvc.perform(get).andExpect(status().isOk());
    }
}
