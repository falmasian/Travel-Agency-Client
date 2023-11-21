package com.flightagencyclient.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;

@Component
public class Client {

    Scanner scanner = new Scanner(System.in);
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    BookingClient bookingClient;

    public Client(BookingClient bookingClient) {
        this.bookingClient = bookingClient;
    }

    public int showMenu() {
        String menu = """
                what do you want to do?
                1. Book Flight
                2. Cancel Reservation
                3. pay one of my reservation
                4. see my reservations
                5. see all flights
                6. filter Flights""";
        try {
            LOGGER.info(menu);
            return scanner.nextInt();
        } catch (Exception ex) {
            LOGGER.error("Error in the server ");
        }
        return 0;
    }

    public void selectingItem(int item) {
        switch (item) {
            case 1 -> bookingClient.reserve();
            case 2 -> bookingClient.cancel();
            case 3 -> bookingClient.pay();
            case 4 -> bookingClient.getAndPrintAllClientReservations();
            case 5 -> bookingClient.getAndPrintFlights();
            case 6 -> bookingClient.filterAndPrintFlights();
            default -> showMenu();
        }
        process();
    }

    public void process() {
        int selectedItem = showMenu();
        selectingItem(selectedItem);
    }
}