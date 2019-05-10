package com.bnpinnovation.batch.config;

import com.bnpinnovation.batch.domain.User;
import com.bnpinnovation.batch.repository.UserRepository;
import com.bnpinnovation.batch.type.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Slf4j
public class InactiveUserJobConfig {
    private static final int CHUNK_SIZE = 10;

    @Autowired
    private UserRepository userRepository;


    @Bean
    public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, Step inactiveJobStep) {
        return jobBuilderFactory.get("inactiveUserJob1")
                .preventRestart()
                .start(inactiveJobStep)
                .build();
    }

    @Bean
    public Step inactiveJobStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("inactiveUserStep1")
                .<User, User>chunk( CHUNK_SIZE )
                .reader(inactiveUserReader())
                .processor(inactiveUserProcessor())
                .writer(inactiveUserWriter())
                .build();
    }

    @Bean
    public ItemWriter<? super User> inactiveUserWriter() {
        return items -> userRepository.saveAll(items);
    }

    @Bean
    public ItemProcessor<? super User, ? extends User> inactiveUserProcessor() {
        return (ItemProcessor<User, User>) user -> user.inactive();
    }

    @Bean
    @StepScope
    public ListItemReader<? extends User> inactiveUserReader() {
        List<User> inactiveUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(LocalDateTime.now().minusYears(1), UserStatus.ACTIVE);
        return new ListItemReader<>(inactiveUsers);
    }
}
