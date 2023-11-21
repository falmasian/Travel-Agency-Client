package com.flightagencyclient.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    Client client;

    public MyCommandLineRunner(Client client) {
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        client.process();
    }
}
