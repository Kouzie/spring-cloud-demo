package com.sample.spring.cloud.order;

import com.sample.spring.cloud.order.model.Order;
import com.sample.spring.cloud.order.model.OrderStatus;
import io.specto.hoverfly.junit.core.HoverflyConfig;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.any;

@Disabled
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "spring.profiles.active=zone1"
        }
)
public class OrderControllerTest {

    private static Logger LOGGER = LoggerFactory.getLogger(OrderControllerTest.class);

    @Autowired
    TestRestTemplate template;

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
            .inSimulationMode(dsl(
                    service("192.168.100.101:8091")
                            .andDelay(200, TimeUnit.MILLISECONDS)
                            .forAll()
                            .post(any()).anyQueryParams()
                            .willReturn(
                                    success("[{\"id\":\"1\",\"number\":\"1234567890\",\"balance\":5000}]",
                                            "application/json").disableTemplating()
                            ),
                    service("192.168.100.101:9091")
                            .andDelay(2000, TimeUnit.MILLISECONDS)
                            .forAll()
                            .post(any()).anyQueryParams()
                            .willReturn(
                                    success("[{\"id\":\"2\",\"number\":\"1234567891\",\"balance\":8000}]",
                                            "application/json").disableTemplating()
                            )
            ), HoverflyConfig.localConfigs().addCommands("--disable-cache"))
            .printSimulationData();


    @Test
    public void testOrder() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            sendAndAcceptOrder();
            Thread.sleep(100);
        }
    }

    private void sendAndAcceptOrder() {
        try {
            Random r = new Random();
            Order order = new Order();
            order.setCustomerId((long) r.nextInt(3)+1);
            order.setProductIds(Arrays.asList(new Long[] {(long) r.nextInt(10)+1,(long) r.nextInt(10)+1}));
            order = template.postForObject("http://localhost:9090", order, Order.class);
            if (order.getStatus() != OrderStatus.REJECTED) {
                template.put("http://localhost:9090/{id}", null, order.getId());
            }
        } catch (Exception e) {

        }
    }

}
