package com.example.issue621;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.content.commons.repository.Store;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.content.rest.StoreRestResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Issue621Application {

	public static void main(String[] args) {
		SpringApplication.run(Issue621Application.class, args);
	}

	@StoreRestResource(path = "videos")
	public interface VideoStore extends Store<String> {

	}

	@EnableWebMvc
	@Configuration
	public class MvcConfig implements WebMvcConfigurer {

	    @Bean
	    public File filesystemRoot() {
	        try {
	            String fileName = "/tmp/issue-621";
	            Path path = Paths.get(fileName);
	            return Files.createDirectories(path).toFile();
            } catch (IOException ioe) {
                int i=0;
            }
	        return null;
	    }

	    @Bean
	    public FileSystemResourceLoader fileSystemResourceLoader() {
	        return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
	    }

	    @Override
	    public void addInterceptors(final InterceptorRegistry registry) {
//	        registry.addInterceptor(new LoggerInterceptor());
	    }
	}
}
