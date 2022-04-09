package io.springbatch.springbatch.config;

import io.springbatch.springbatch.domain.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JpaCursorConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    @Primary
    public Job jpaCursorJob() {
        return this.jobBuilderFactory.get("jpaCursorJob")
                .incrementer(new RunIdIncrementer())
                .start(jpaCursorStep1())
                .build();
    }

    @Bean
    public Step jpaCursorStep1() {
        return this.stepBuilderFactory.get("jpaCursorStep1")
                .<Customer, Customer>chunk(3)
                .reader(jpaCursorItemReader())
                .writer(jpaCursorItemWriter())
                .build();
    }

    private ItemWriter<Customer> jpaCursorItemWriter() {
        return items -> {
            for (Customer item : items) {
                System.out.println(item.toString());
            }
        };
    }

    private ItemReader<? extends Customer> jpaCursorItemReader() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstname", "A%");

        return new JpaCursorItemReaderBuilder<Customer>()
                .name("jpaCursorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Customer c where firstname like :firstname")
                .parameterValues(parameters)
                .build();
    }


    @Bean
    public Step jpaCursorStep2() {
        return this.stepBuilderFactory.get("jpaCursorStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info(">> jpaCursorStep2 has executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
