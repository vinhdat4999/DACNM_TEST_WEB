package com.blas.fish.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.blas.fish.dao.AdminDAO;
import com.blas.fish.dao.CategoryDAO;
import com.blas.fish.dao.FishDAO;
import com.blas.fish.dao.OrderDAO;
import com.blas.fish.dao.OrderDetailDAO;
import com.blas.fish.dao.ProductImageDAO;
import com.blas.fish.dao.impl.AdminDAOImpl;
import com.blas.fish.dao.impl.CategoryDAOImpl;
import com.blas.fish.dao.impl.FishDAOImpl;
import com.blas.fish.dao.impl.OrderDAOImpl;
import com.blas.fish.dao.impl.OrderDetailDAOImpl;
import com.blas.fish.dao.impl.ProductImageDAOImpl;

@Configuration
@ComponentScan("com.blas.fish.*")
@EnableTransactionManagement
// Load to Environment.
@PropertySource("classpath:ds-hibernate-cfg.properties")
public class ApplicationContextConfig {

	@Autowired
	private Environment env;

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
		// Load property in message/validator.properties
		rb.setBasenames(new String[] { "messages/validator" });
		return rb;
	}

	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();

		// Set Max Size...
		// commonsMultipartResolver.setMaxUploadSize(...);

		return commonsMultipartResolver;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
		Properties properties = new Properties();

		// Xem: ds-hibernate-cfg.properties
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("current_session_context_class", env.getProperty("current_session_context_class"));

		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		// Package entity.
		factoryBean.setPackagesToScan(new String[] { "com.blas.fish.model" });
		factoryBean.setDataSource(dataSource);
		factoryBean.setHibernateProperties(properties);
		factoryBean.afterPropertiesSet();
		//
		SessionFactory sf = factoryBean.getObject();
		return sf;
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

	@Bean(name = "categoryDAO")
	public CategoryDAO getCategoryDAO() {
		return new CategoryDAOImpl();
	}

	@Bean(name = "fishDAO")
	public FishDAO getFishDAO() {
		return new FishDAOImpl();
	}

	@Bean(name = "orderDAO")
	public OrderDAO getOrderDAO() {
		return new OrderDAOImpl();
	}

	@Bean(name = "orderDetailDAO")
	public OrderDetailDAO getOrderDetailDAO() {
		return new OrderDetailDAOImpl();
	}

	@Bean(name = "adminDAO")
	public AdminDAO getAdminDAO() {
		return new AdminDAOImpl();
	}

	@Bean(name = "productImageDAO")
	public ProductImageDAO getProductImageDAO() {
		return new ProductImageDAOImpl();
	}
}