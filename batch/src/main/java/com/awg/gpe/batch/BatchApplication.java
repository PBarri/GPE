/**
 * 
 */
package com.awg.gpe.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pablo Barrientos
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.awg.gpe.batch", "com.awg.gpe.data" })
public class BatchApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

}
