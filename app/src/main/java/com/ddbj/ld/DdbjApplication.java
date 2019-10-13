package com.ddbj.ld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class DdbjApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DdbjApplication.class, args);
    }

    // This is the test of batch.
    @Override
    public void run(String[] args) throws Exception {
        System.out.println("Batch run!!!");
    }
}
