package com.sample.spring.cloud.customer;

import com.sample.spring.cloud.customer.model.Customer;
import io.specto.hoverfly.junit.core.HoverflyConfig;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.concurrent.TimeUnit;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;

@Slf4j
@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControllerTest {

    @Autowired
    TestRestTemplate template;

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule
            .inSimulationMode(dsl(
                    service("192.168.100.101:8091")
                            .andDelay(200, TimeUnit.MILLISECONDS)
                            .forAll()
                            .get(("/customer/1")).anyQueryParams()
                            .willReturn(
                                    success("[{\"id\":\"1\",\"number\":\"1234567890\",\"balance\":5000}]",
                                            "application/json").disableTemplating()
                            ),
                    service("192.168.100.101:9091")
                            .andDelay(2000, TimeUnit.MILLISECONDS)
                            .forAll()
                            .get(("/customer/1")).anyQueryParams()
                            .willReturn(
                                    success("[{\"id\":\"2\",\"number\":\"1234567891\",\"balance\":8000}]",
                                            "application/json").disableTemplating()
                            )
            ), HoverflyConfig.localConfigs().addCommands("--disable-cache"))
            .printSimulationData();

//    @ClassRule
//    public static HoverflyRule hoverflyRule = HoverflyRule
//            .inSimulationMode(
//                    classpath("simulate.json")
//                    ,localConfigs().addCommands("--disable-cache")
//            ).printSimulationData();

//    @ClassRule
//    public static HoverflyRule hoverflyRule2 = HoverflyRule.inCaptureMode(
//            "capture.json"
//            //, localConfigs().destination("account-service")
//    );

    @Disabled
    @Test
    //https://github.com/SpectoLabs/hoverfly-java/issues/19
    public void testCustomerWithAccounts() {
        int a = 0, b = 0, d = 0;
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(500);
                Customer c = template.getForObject("/withAccounts/{id}", Customer.class, 1);
                log.info("index: " + i + ", Customer: {}", c);
                if (c != null && c.getAccounts().size() != 0) {
                    if (c.getAccounts().get(0).getBalance() == 500000)
                        a++;
                    else if (c.getAccounts().get(0).getBalance() == 200000)
                        b++;
                }
            } catch (Exception e) {
                log.error("Error connecting with service", e);
            }

        }
        log.info("TEST RESULT: 8091={}, 9091={}", a, b);
    }

}