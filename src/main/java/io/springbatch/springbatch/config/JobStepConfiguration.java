//package io.springbatch.springbatch.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.h2.util.Task;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///*
//* JobStep 기반
//* Job안에서 다른 Job을 수행하는 Process
//* + DefaultJobParametersExtractor를 사용하여 ExecutionContext안에 설정된 파라미터 값을 공유
//*
//* * parentJob 1) jobStep -> childJob -> step1
//*             2) step2
//*
//*  선행 수행되는 1)에서 Exception이 발생하면 step2는 수행 되지 않는다.
//*
//*  JobStep 기법: 이와 같이 여러 Job을 조합하여 사용할 때 유용
//* */
//
//@Configuration
//@RequiredArgsConstructor
//@Slf4j
//public class JobStepConfiguration {
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Job parentJob() {
//        return this.jobBuilderFactory.get("parentjob")
//                .start(jobStep(null))
//                .next(step2())
//                .build();
//    }
//
//    @Bean
//    public Step jobStep(JobLauncher jobLauncher) {
//        return stepBuilderFactory.get("jobStep")
//                .job(childjob())
//                .launcher(jobLauncher)
//                .parametersExtractor(jobParametersExtractor())
//                .listener(new StepExecutionListener() {
//                    @Override
//                    public void beforeStep(StepExecution stepExecution) {
//                        stepExecution.getExecutionContext().putString("name", "user1");
//                    }
//
//                    @Override
//                    public ExitStatus afterStep(StepExecution stepExecution) {
//                        return null;
//                    }
//                })
//                .build();
//    }
//
//    //ExcutionContext 안에 있는 값을 key 기반으로 가져오기 위함
//    private DefaultJobParametersExtractor jobParametersExtractor() {
//        DefaultJobParametersExtractor extractor = new DefaultJobParametersExtractor();
//        extractor.setKeys(new String[]{"name"});
//        return extractor;
//    }
//
//    public Job childjob() {
//        return jobBuilderFactory.get("childJob")
//                .start(step1())
//                .build();
//    }
//
//    @Bean
//    public Step step1() {
//        return stepBuilderFactory.get("step1")
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        return RepeatStatus.FINISHED;
//                    }
//                })
//                .build();
//    }
//
//    @Bean
//    public Step step2() {
//        return stepBuilderFactory.get("step2")
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        return RepeatStatus.FINISHED;
//                    }
//                })
//                .build();
//    }
//}
