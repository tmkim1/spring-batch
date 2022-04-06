package io.springbatch.springbatch.config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* 복잡한 Flow 구성이 필요한 경우에 사용한다.
*
* Simple Flow를 사용하면 Flow안에 Step, 중첩 Flow 사용이 가능하다.
*
* */

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SimpleFlowConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleFlowJob() {
        return this.jobBuilderFactory.get("simpleFlowJob")
                .incrementer(new RunIdIncrementer())
                .start(simpleFlow1())
                    .on("COMPLETED")
                    .to(simpleFlow2())
                .from(simpleFlow1())
                    .on("FAILED")
                    .to(simpleFlow3())
                .end()
                .build();
    }

    @Bean
    public Flow simpleFlow1() {
        FlowBuilder<Flow> builder = new FlowBuilder<>("simpleFlow");
        builder.start(simpleStep1())
                .next(simpleStep2())
                .end();

        return builder.build();
    }

    @Bean
    public Flow simpleFlow2() {
        FlowBuilder<Flow> builder = new FlowBuilder<>("simpleFlow");
        builder.start(simpleFlow3())
                .next(simpleStep5())
                .next(simpleStep6())
                .end();

        return builder.build();
    }

    @Bean
    public Flow simpleFlow3() {
        FlowBuilder<Flow> builder = new FlowBuilder<>("simpleFlow");
        builder.start(simpleStep3())
                .next(simpleStep4())
                .end();

        return builder.build();
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> simpleStep1 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step simpleStep2() {
        return stepBuilderFactory.get("simpleStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> simpleStep2 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step simpleStep3() {
        return stepBuilderFactory.get("simpleStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> simpleStep3 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step simpleStep4() {
        return stepBuilderFactory.get("simpleStep4")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> simpleStep4 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step simpleStep5() {
        return stepBuilderFactory.get("simpleStep5")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> simpleStep5 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step simpleStep6() {
        return stepBuilderFactory.get("simpleStep6")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> simpleStep6 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
