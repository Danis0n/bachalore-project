package ru.fp.coreservice;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class CoreServiceApplicationTests {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private EntityManager entityManager;

	@Test
	void contextLoads() {
		assert dataSource != null;
		assert entityManager != null;
	}

}
