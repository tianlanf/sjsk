package com.thoughtworks.yafei;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(locations={"classpath:applicationContextRestartSpikeJob.xml"})
public class JobIntegrationTest {
    private SimpleJobLauncher launcher = new SimpleJobLauncher();

    @Autowired
    private Job profileLoad;

    @Test
    public void shouldRunJobSuccessfully() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextRestartSpikeJob.xml");
        JobRepository jobRepository = (JobRepository)context.getBean("jobRepository");
        launcher.setJobRepository(jobRepository);
        TaskExecutor taskExecutor = (SimpleAsyncTaskExecutor)context.getBean("taskExecutor");
        launcher.setTaskExecutor(taskExecutor);
        Job profileLoad1 = (Job)context.getBean("profileLoad");

        Map<String, JobParameter> jobParameterMap = ImmutableMap.of("date", new JobParameter(new Date()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);
        JobExecution jobExecution = launcher.run(profileLoad1, jobParameters);

        assertThat(jobExecution.getExitStatus(), is(ExitStatus.COMPLETED));
    }
}
