//package io.springbatch.springbatch.config;
//
//import io.springbatch.springbatch.domain.Customer;
//import io.springbatch.springbatch.mapper.CustomerRowMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.Order;
//import org.springframework.batch.item.database.PagingQueryProvider;
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
//import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
//import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
//import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
//import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * JDBC -> Wirter 사용 시 Bulk 처리가 가능하여 성능에 이점이 있음
// *
// * read 상세: AbstractPagingItemReader -> doRead()
// */
//@Configuration
//@RequiredArgsConstructor
//@Slf4j
//public class JdbcBatchConfiguration {
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final EntityManagerFactory entityManagerFactory;
//    private final DataSource dataSource;
//
//    @Bean
//    @Primary
//    public Job jdbcBatchJob() throws Exception {
//        return this.jobBuilderFactory.get("jdbcBatchJob")
//                .incrementer(new RunIdIncrementer())
//                .start(jdbcBatchStep1())
//                .build();
//    }
//
//    @Bean
//    public Step jdbcBatchStep1() throws Exception {
//        return this.stepBuilderFactory.get("jdbcBatchStep1")
//                .<Customer, Customer>chunk(100)
//                .reader(jdbcPagingItemReader())
//                .writer(jpaPagingItemWriter())
//                .build();
//    }
//
//    private ItemReader<? extends Customer> jdbcPagingItemReader() throws Exception {
//
//        log.info("======== Call Item Reader ========");
//
//        return new JdbcPagingItemReaderBuilder<Customer>()
//                .name("jdbcPagingItemReader")
//                .pageSize(100)
//                .dataSource(dataSource)
//                .rowMapper(new BeanPropertyRowMapper<>(Customer.class))
//                .queryProvider(createQueryProvider())
//                .build();
//
//
////        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
////
////        reader.setDataSource(this.dataSource);
////        reader.setFetchSize(10);
////        reader.setRowMapper(new CustomerRowMapper());
////
////        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
////        queryProvider.setSelectClause("id, firstname, lastname, birthdate");
////        queryProvider.setFromClause("from customer");
////        queryProvider.setWhereClause("where firstname like :firstname");
////
////        //Paging에는 꼭 orderBy 설정이 필요하다. (순서에 대한 보장을 위해)
////        Map<String, Order> sortKeys = new HashMap<>(1);
////
////        sortKeys.put("id", Order.ASCENDING);
////        queryProvider.setSortKeys(sortKeys);
////        reader.setQueryProvider(queryProvider);
////
////        HashMap<String, Object> parameters = new HashMap<>();
////        parameters.put("firstname", "A%");
////
////        reader.setParameterValues(parameters);
////
////
////        return reader;
//    }
//
//    @Bean
//    public PagingQueryProvider createQueryProvider() throws Exception {
//        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
//        queryProvider.setDataSource(dataSource);
//        queryProvider.setSelectClause("id, firstname, lastname, birthdate");
//        queryProvider.setFromClause("from customer");
////        queryProvider.setWhereClause("where firstname like :firstname");
//
//        //Paging에는 꼭 orderBy 설정이 필요하다. (순서에 대한 보장을 위해)
//        Map<String, Order> sortKeys = new HashMap<>();
//
//        sortKeys.put("id", Order.ASCENDING);
//        queryProvider.setSortKeys(sortKeys);
//
//        return queryProvider.getObject();
//    }
//
//    public JdbcBatchItemWriter<Customer> jdbcPagingItemWriter() {
//        log.info("======== Call Item Writer ========");
//        return new JdbcBatchItemWriterBuilder<Customer>()
//                .dataSource(dataSource)
//                .sql("insert into customer2 values (:id, :firstname, :lastname, :birthdate")
//                .beanMapped() //일반 클래스 타입으로 매핑 (파라미터 매핑: Customer)
////                .columnMapped() //key, value 기반으로 insert SQL의 Values를 매핑 (Map)
//                .build();
//    }
//
//    private ItemWriter<Customer> jpaPagingItemWriter() {
//
//        return items -> {
//            for (Customer customer : items) {
//                System.out.println(customer.getAddress().getLocation());
//            }
//        };
//    }
//}
