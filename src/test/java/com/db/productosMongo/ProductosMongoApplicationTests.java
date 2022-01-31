package com.db.productosMongo;


import com.db.productosMongo.model.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductosMongoApplicationTests {
		Logger logger = LogManager.getLogger(ProductosMongoApplicationTests.class);

	private String url;
		@LocalServerPort
		private int port;
		private ObjectMapper objectMapper = new ObjectMapper();

		@Autowired
		private TestRestTemplate restTemplate;

		@BeforeAll
		static void setup() {
			System.out.println("@BeforeAll - se ejecuta antes de todos los tests");
		}

		@BeforeEach
		void init() {
			url = String.format("http://localhost:%d/inventario/", port);
			System.out.println("@BeforeEach - se ejecuta antes de la ejecución de cada test");
		}

		@Test
		public void getAllProducts() throws Exception {
			long start = System.nanoTime();
			var uriTest = String.format("%s%s", url, "producto/all");

			var messageResult = this.restTemplate.getForObject(uriTest, List.class);

			Assert.notNull(messageResult, "Lista de productos no nula");
			Assert.notEmpty(messageResult, "Lista de productos con elementos");
			Assert.isTrue(messageResult.size() == 3, "Tamaño de la lista de productos es de 3");

			logger.info("Tamaño de messageResult es "+messageResult.size());

			long end = System.nanoTime();
			logger.info("Se ejecutó el metodo getAllProducts con una duración de {} ms", TimeUnit.NANOSECONDS.toMillis(end - start));
		}
	@Test
	public void createProduct() {
		long start = System.nanoTime();
		var uriTest = String.format("%s%s", url, "producto");
		var message = Producto.builder().id(10L).name("cable").stock(25).build();

		var messageResult = this.restTemplate.postForObject(uriTest, message, Producto.class);

		Assert.notNull(messageResult, "	Producto no nulo");
		Assert.isTrue(messageResult.getId() == 10L, "ID del producto OK");
		Assert.isTrue(messageResult.getName().equals("cable"), "Name del producto OK");



		long end = System.nanoTime();
		logger.info("Se ejecutó el metodo createProduct con una duración de {} ms", TimeUnit.NANOSECONDS.toMillis(end - start));
	}

	@Test
	public void getAllProductsHttpRequestStatus() throws IOException {
		long start = System.nanoTime();
		var uriTest = String.format("%s%s", url, "producto/all");

		var request = new HttpGet(uriTest);
		var httpResponse = HttpClientBuilder.create().build().execute(request);

		Assert.isTrue(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK, "Response status OK");

		long end = System.nanoTime();
		logger.info("Se ejecutó el metodo getAllProductsHttpRequestStatus con una duración de {} ms", TimeUnit.NANOSECONDS.toMillis(end - start));

	}
	@Test
	public void getAllProductsHttpRequestHeader() throws IOException {
		long start = System.nanoTime();
		var uriTest = String.format("%s%s", url, "producto/all");
		var headerAppJson = "application/json";

		var request = new HttpGet(uriTest);
		var httpResponse = HttpClientBuilder.create().build().execute(request);
		var mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
		Assert.isTrue(headerAppJson.equals(mimeType), "Header application/json OK");


		long end = System.nanoTime();
		logger.info("Se ejecutó el metodo getAllProductsHttpRequestHeader con una duración de {} ms", TimeUnit.NANOSECONDS.toMillis(end - start));

	}
	@Test
	public void getAllProductsHttpRequestPayload() throws IOException {
		long start = System.nanoTime();

		var uriTest = String.format("%s%s", url, "producto/all");

		var request = new HttpGet(uriTest);
		var httpResponse = HttpClientBuilder.create().build().execute(request);

		String content = EntityUtils.toString(httpResponse.getEntity());
		var messageResult = objectMapper.readValue(content, List.class);

		Assert.notNull(messageResult, "Lista de productos no nulo");
		Assert.notEmpty(messageResult, "Lista de productos con elementos");
		Assert.isTrue(messageResult.size() == 3, "Tamaño de la lista es de 3");

		long end = System.nanoTime();
		logger.info("Se ejecutó el metodo getAllProductsHttpRequestPayload con una duración de {} ms", TimeUnit.NANOSECONDS.toMillis(end - start));
	}
}

