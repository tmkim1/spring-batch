package io.springbatch.springbatch.config;

import io.springbatch.springbatch.domain.Customer;
import io.springbatch.springbatch.domain.Customer2;
import io.springbatch.springbatch.processor.CustomItemProcessor;
import io.springbatch.springbatch.processor.CustomerItemProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JpaPagingConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;

    @Bean
    @Primary
    public Job jpaPagingJob() {
        return this.jobBuilderFactory.get("jpaPagingJob")
                .incrementer(new RunIdIncrementer())
                .start(jpaPagingStep1())
                .build();
    }

    @Bean
    public Step jpaPagingStep1() {
        return this.stepBuilderFactory.get("jpaPagingStep1")
                .<Customer, Customer2>chunk(3)
                .reader(jpaPagingItemReader())
                .processor(customerItemProcessor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    public ItemReader<? extends Customer> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Customer>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(3)
                .queryString("select c from Customer c join fetch c.address")
                .build();
    }

    @Bean
    public ItemProcessor<? super Customer, ? extends Customer2> customerItemProcessor() {
        return new CustomerItemProcessor();
    }


    @Bean
    public ItemWriter<? super Customer2> jpaItemWriter() {
        return new JpaItemWriterBuilder<Customer2>()
                .usePersist(false) // JPA 영속성으로 인해 Primary key 값이 셋팅되어 들어가면 자체적으로 find를 하게되며 에러가 발생한다.
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    public JdbcBatchItemWriter<Customer> jdbcPagingItemWriter() {
        log.info("======== Call Item Writer ========");
        return new JdbcBatchItemWriterBuilder<Customer>()
                .dataSource(dataSource)
                .sql("insert into customer2 values (:id, :firstname, :lastname, :birthdate")
                .beanMapped() //일반 클래스 타입으로 매핑 (파라미터 매핑: Customer)
//                .columnMapped() //key, value 기반으로 insert SQL의 Values를 매핑 (Map)
                .build();
    }

    public ItemWriter<Customer> itemWriter() {

        return items -> {
            for (Customer customer : items) {
                System.out.println(customer.getAddress().getLocation());
            }
        };
    }

}
