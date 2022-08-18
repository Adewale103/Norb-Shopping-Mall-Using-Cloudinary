package com.twinkles.Norbs_Shopping_Mall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
class NorbsShoppingMallApplicationTests {

	@Autowired
	private DataSource datasource;

	@Test
	void applicationCanBeConnectedToDatabase() {
		assertThat(datasource).isNotNull();
		Connection connection;
		try{
			connection = datasource.getConnection();
			assertThat(connection).isNotNull();
			log.info("Connection -> {}",connection.getCatalog());
		}catch (SQLException e){e.printStackTrace();}
	}

}
