package io.springbatch.springbatch.config;

import io.springbatch.springbatch.domain.Customer;
import io.springbatch.springbatch.itemReader.CustomItemReader;
import io.springbatch.springbatch.processor.CustomItemProcessor;
import io.springbatch.springbatch.writer.CustomItemWriter;
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
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CustomReadProcessWriteConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @Primary
    public Job chunkCustomJob() {
        return this.jobBuilderFactory.get("chunkCustomJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkCustomStep1())
                .build();
    }

    @Bean
    public Step chunkCustomStep1() {
        return this.stepBuilderFactory.get("chunkCustomStep1")
                //chunk API 사용
                .<Customer, Customer>chunk(2)
                .reader(customerItemReader())
                .processor(customerItemProcessor())
                .writer(customerItemWriter())
                .build();
    }

    @Bean
    public ItemWriter<? super Customer> customerItemWriter() {
        return new CustomItemWriter();
    }

    @Bean
    public ItemProcessor<? super Customer,? extends Customer> customerItemProcessor() {
       return new CustomItemProcessor();
    }

    @Bean
    public ItemReader<Customer> customerItemReader() {
        return new CustomItemReader(Arrays.asList(
                new Customer("taemin1")
                ,new Customer("taemin2")
                ,new Customer("taemin3")));
    }
}
