package com.ets.scorebatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import com.ets.scorebatch.listener.MyJobListener;
import com.ets.scorebatch.listener.MyStepListener;
import com.ets.scorebatch.model.StudentCsv;
import com.ets.scorebatch.model.StudentJson;
import com.ets.scorebatch.model.StudentXml;
import com.ets.scorebatch.processor.FirstItemProcessor;
import com.ets.scorebatch.reader.FisrtItemReader;
import com.ets.scorebatch.service.SecondTask;
import com.ets.scorebatch.writer.FirstItemWriter;

@Configuration
public class BatchConfig {
	
	@Autowired
	MyJobListener joblistener;
	@Autowired
	MyStepListener steplistener;
	@Autowired
	SecondTask secondTask;
	@Autowired
	FisrtItemReader itemreader;
	@Autowired
	FirstItemProcessor itemprocessor;
	@Autowired
	FirstItemWriter itemwriter;

    @Bean
    public Job firstjob(JobRepository jobRepository, Step step,Step secondStep) {
        return new JobBuilder("demoJob", jobRepository)
        		.incrementer(new RunIdIncrementer())
        		.listener(joblistener)
                .start(step)
                .next(secondStep)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("demoStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("🔥 Batch Job Executed Successfully!");
                    return RepeatStatus.FINISHED;
                }, transactionManager).listener(steplistener)
                .build();
    }
    
    @Bean
    public Step secondStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager) {

        return new StepBuilder("secondStep", jobRepository)
                .tasklet(secondTask, transactionManager)
                .listener(steplistener)
                .build();
    }
    
    @Bean
    public Job secondjob(JobRepository jobRepository,Step chunkstep,Step secondStep) {
        return new JobBuilder("Second Job", jobRepository)
        		.incrementer(new RunIdIncrementer())
        		//.listener(joblistener)
                .start(chunkstep)
                //.next(secondStep)
                .build();
    }
    
    @Bean
    public Step chunkstep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("chunkStep", jobRepository)
                .<StudentXml,StudentXml>chunk(3, transactionManager)
				//.reader(flatfileItemReader(null))
                //.reader(jsonItemReader(null))
                .reader(staxEventItemReader(null))
				//.processor(itemprocessor)
				.writer(itemwriter)
                //.listener(steplistener)
                .build();
    }
    
    @StepScope
    @Bean
    public FlatFileItemReader<StudentCsv> flatfileItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource){
    	FlatFileItemReader<StudentCsv> flatfileitemreader = new FlatFileItemReader<StudentCsv>();
    	flatfileitemreader.setResource(fileSystemResource);
    	//flatfileitemreader.setResource(new FileSystemResource(new File("C:\\MyWorkSpace\\scorebatch\\inputFiles\\students.csv")));
    	//flatfileitemreader.setResource(new ClassPathResource("students.csv"));
    	DefaultLineMapper<StudentCsv> linemapper = new DefaultLineMapper<StudentCsv>();
    	DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    	tokenizer.setNames("id", "name", "nationality", "city", "longitude", "latitude", "gender");
    	tokenizer.setDelimiter(",");
    	BeanWrapperFieldSetMapper<StudentCsv> fieldSetMapper = new BeanWrapperFieldSetMapper<StudentCsv>();
    	fieldSetMapper.setTargetType(StudentCsv.class);
    	linemapper.setLineTokenizer(tokenizer);
    	linemapper.setFieldSetMapper(fieldSetMapper);
    	flatfileitemreader.setLineMapper(linemapper);
    	flatfileitemreader.setLinesToSkip(1);
    	return flatfileitemreader;
    }
    @StepScope
    @Bean
    public JsonItemReader<StudentJson> jsonItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource){
    	JsonItemReader<StudentJson> jsonitemreader = new JsonItemReader<StudentJson>();
    	jsonitemreader.setResource(fileSystemResource);
    	jsonitemreader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class));
    	jsonitemreader.setMaxItemCount(8);
    	jsonitemreader.setCurrentItemCount(2);
    	return jsonitemreader;
    }
    @StepScope
    @Bean
    public StaxEventItemReader<StudentXml> staxEventItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource){
    	StaxEventItemReader<StudentXml> staxeventItemReader = new StaxEventItemReader<StudentXml>();
    	staxeventItemReader.setResource(fileSystemResource);
    	staxeventItemReader.setFragmentRootElementName("student");
    	staxeventItemReader.setUnmarshaller(new Jaxb2Marshaller() {
    		{
    			setClassesToBeBound(StudentXml.class);
    		}
    	});
    	return staxeventItemReader;
    }
    
}
