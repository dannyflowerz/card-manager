package io.github.dannyflowerz.cardmanager;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWireMock(port = 8081)
public class CardManagerApplicationTests {

    @Rule
    public StubRunnerRule stubRunnerRule = new StubRunnerRule().downloadStub("io.github.dannyflowerz", "list-cards", "0.0.1-SNAPSHOT", "stubs").workOffline(true).withPort(8083);

	@Test
	public void test_should_return_all_frauds() {
		String json = "{\"microservices\":\"demo\"}";
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/cards")).willReturn(WireMock.aResponse().withBody(json).withStatus(200)));

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8081/cards", String.class);

		BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(200);
		BDDAssertions.then(entity.getBody()).isEqualTo(json);
	}

	@Test
	public void test_should_return_all_frauds_integration() {
		String json = "{\"microservices\":\"demo\"}";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8083/cards", String.class);

		BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(200);
		BDDAssertions.then(entity.getBody()).isEqualTo(json);
	}

}
