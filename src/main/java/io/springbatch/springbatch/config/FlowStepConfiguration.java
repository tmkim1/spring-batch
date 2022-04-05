package io.springbatch.springbatch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlowStepConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob() {
        return this.jobBuilderFactory.get("flowJob")
                .start(flowStep1())
                .on("COMPLETED").to(flowStep3())
                .from(flowStep1())
                .on("FAILED").to(flowStep2())
                .end()
                .build();
    }

    @Bean
    public Step flowStep1() {
        return stepBuilderFactory.get("flowStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("step1 has executed");
                        throw new RuntimeException("step1 was failed");
//                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step flowStep2() {
        return stepBuilderFactory.get("flowStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("flowStep2 has executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step flowStep3() {
        return stepBuilderFactory.get("flowStep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("flowStep3 has executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
