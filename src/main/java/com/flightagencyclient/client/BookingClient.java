package com.flightagencyclient.client;


import com.flightagencyclient.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class BookingClient {


    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingClient.class);

    @Value("${rest.client.base-url}")
    private String BASE_URL;

    @Autowired
    public BookingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void reserve() {
        try {
            getAndPrintAllCities();
            FilterResponseDto filterResponseDto = filterFlights();
            printFilterFlights(filterResponseDto);
            ReservationResponseDto reservationResponseDto;
            reservationResponseDto = book();
            if (reservationResponseDto != null) {
                continueBooking(reservationResponseDto.getTracingCode());
            }
        } catch (HttpStatusCodeException e) {
            LOGGER.info(e.getResponseBodyAsString());
        }
    }

    public void filterAndPrintFlights() {
        try {
            FilterResponseDto filterResponseDto = filterFlights();
            printFilterFlights(filterResponseDto);
        } catch (HttpStatusCodeException e) {
            LOGGER.info(e.getResponseBodyAsString());
        }
    }

    public void printFilterFlights(FilterResponseDto filterResponseDto) {
        LOGGER.info("flight number                      	origin 	destination   flight date	cost   remaining seats");
        for (FlightDto f : filterResponseDto.getFlightDtoList()) {
            LOGGER.info(f.getFlightNumber() + "		"
                        + f.getOriginId() + "."
                        + f.getOriginName() + "    "
                        + f.getDestinationId() + "."
                        + f.getDestinationName() + "    "
                        + f.getFlyDateTime() + " 	 "
                        + f.getCost() + "   "
                        + f.getRemainingSeats());
        }
    }

    public AllCitiesResponse getAllCities() {
        String cityUrl = BASE_URL + "/api/city/all";
        ParameterizedTypeReference<AllCitiesResponse> cityResponseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<AllCitiesResponse> cityResponse = restTemplate.exchange(cityUrl, HttpMethod.GET
                , null, cityResponseType);
        AllCitiesResponse citiesResponse = cityResponse.getBody();
        return citiesResponse;
    }

    public void printCities(AllCitiesResponse citiesResponse) {
        int i = 0;
        for (CityDto c : citiesResponse.getCityDtoList()) {
            i++;
            LOGGER.info(i + ". " + c.getCityName());
        }
    }

    public void getAndPrintAllCities() {
        AllCitiesResponse allCitiesResponse = getAllCities();
        if (allCitiesResponse != null) {
            printCities(allCitiesResponse);
        }
    }

    public FilterResponseDto filterFlights() {
        FilterFlightDto filterFlightDto = getInput();
        String filterUrl = BASE_URL + "/api/book/filter";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FilterFlightDto> requestEntity = new HttpEntity<>(filterFlightDto, headers);
        ParameterizedTypeReference<FilterResponseDto> filterResponseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<FilterResponseDto> filterResponse = restTemplate.exchange(filterUrl, HttpMethod.POST
                , requestEntity, filterResponseType);
        return filterResponse.getBody();
    }

    public FilterFlightDto getInput() {
        Scanner s = new Scanner(System.in);

        LOGGER.info("enter origin city number");
        int originId = s.nextInt();
        LOGGER.info("enter destination city number");
        int destinationId = s.nextInt();
        LOGGER.info("enter flight date in this format: YYYY-MM-DD");
        String str = s.next();
        Date flightDate = java.sql.Date.valueOf(str);

        return new FilterFlightDto(originId, destinationId, flightDate);
    }

    public ReservationResponseDto book() {
        Scanner s = new Scanner(System.in);
        LOGGER.info("enter Flight number");
        int flightId = s.nextInt();
        LOGGER.info("enter your national code");
        Scanner s1 = new Scanner(System.in);
        String customerId = s1.nextLine();
        while (customerId.trim().length() != 10) {
            LOGGER.info("national code must have 10 character.please try again.");
            LOGGER.info("enter your national code");
            customerId = s1.nextLine();
        }
        LOGGER.info("how many tickets do you want?");
        int numberOfTickets = s.nextInt();
        ArrayList<String> nationalcodes = new ArrayList<>();
        for (int i = 0; i < numberOfTickets; i++) {
            int j = i + 1;
            LOGGER.info("enter national code of passenger number " + j + " ");
            Scanner scanner = new Scanner(System.in);
            String nationalCode = scanner.nextLine().trim();
            if (nationalcodes.contains(nationalCode)) {
                i--;
                LOGGER.info("duplicate national code! please try again.");
                continue;
            }
            if (nationalCode.trim().length() != 10) {
                i--;
                LOGGER.info("national code must have 10 character.please try again.");
            }
            nationalcodes.add(nationalCode);
        }
        BookingDto bookingDto = new BookingDto(customerId, flightId, nationalcodes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BookingDto> requestEntity = new HttpEntity<>(bookingDto, headers);
        String url = BASE_URL + "/api/book/reserve";
        try {
            ResponseEntity<ReservationResponseDto> response = restTemplate.exchange(url, HttpMethod.POST
                    , requestEntity
                    , ReservationResponseDto.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.info(e.getResponseBodyAsString());
        }
        return null;
    }

    public void continueBooking(String tracingCode) {
        Scanner s = new Scanner(System.in);
        LOGGER.info("The temporary reservation has been made.\n your tracking code is: " + tracingCode
                    + " \n You have ten minutes to pay and finalize your request");
        LOGGER.info("do you want to pay now? 1.yes 2.no");
        String response = s.nextLine();
        if (response.trim().equals("1")) {
            pay(tracingCode);
        } else {
            LOGGER.info("we will wait until 10 minutes later!");
        }
    }

    public AllFlightsResponse getAllFlights() {
        String endpoint = "/api/flight/all";
        String url = BASE_URL + endpoint;
        ParameterizedTypeReference<AllFlightsResponse> responseType = new ParameterizedTypeReference<>() {
        };
        AllFlightsResponse allFlightsResponse = null;
        try {
            ResponseEntity<AllFlightsResponse> response = restTemplate.exchange(url, HttpMethod.GET
                    , null, responseType);
            allFlightsResponse = response.getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.info(e.getResponseBodyAsString());
        }
        return allFlightsResponse;
    }

    public void printFlights(AllFlightsResponse allFlightsResponse) {
        LOGGER.info("flight number	origin 	destination   flight date	cost   remaining seats");
        for (FlightDto f : allFlightsResponse.getFlightDtoList()) {
            LOGGER.info(f.getFlightNumber() + "	"
                        + f.getOriginId() + "."
                        + f.getOriginName() + " 	"
                        + f.getDestinationId() + "."
                        + f.getDestinationName() + "  	  "
                        + f.getFlyDateTime() + " 	 "
                        + f.getCost() + "   "
                        + f.getRemainingSeats());
        }
    }

    public void getAndPrintFlights() {
        AllFlightsResponse allFlightsResponse = getAllFlights();
        printFlights(allFlightsResponse);
    }

    public void cancel() {
        CustomerReservationsResponseDto customerReservationsResponseDto = getAllClientReservations();
        List<ReservationGetDto> reservationGetDtoList = customerReservationsResponseDto.getReservationGetDtoList();
        printCustomerReservationsResponseDto(customerReservationsResponseDto);
        if (reservationGetDtoList.size() <= 0) {
            return;
        }
        LOGGER.info("you have reserved " + reservationGetDtoList.size()
                    + " seats with this tracking code." + " \nHow many do you want to cancel?");

        Scanner ss = new Scanner(System.in);
        int numberOfTickets = ss.nextInt();
        if (numberOfTickets > reservationGetDtoList.size()) {
            LOGGER.info("this input is more than your reserved seats.");
            return;
        }
        if (numberOfTickets <= 0) {
            LOGGER.info("invalid input");
            return;
        }
        String customerId = reservationGetDtoList.get(0).getCustomerId();
        int flightId = reservationGetDtoList.get(0).getFlightId();
        ArrayList<String> nationalcodes = new ArrayList<>();
        for (int i = 0; i < numberOfTickets; i++) {
            int j = i + 1;
            LOGGER.info("enter passanger number " + j + " nationalcode: ");
            Scanner scanner1 = new Scanner(System.in);
            String code = scanner1.nextLine().trim();
            int numOfTicketsWithInputNationalCode = reservationGetDtoList.stream()
                    .filter(c -> c.getNationalCode().equals(code.trim())).toList().size();
            if (numOfTicketsWithInputNationalCode > 0) {
                nationalcodes.add(code);
            } else {
                LOGGER.info("You do not have a reservation with this national code.please enter again.");
                i--;
            }
        }
        CancellationDto cancellationDto = new CancellationDto(customerId, flightId, nationalcodes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CancellationDto> requestEntity = new HttpEntity<>(cancellationDto, headers);
        String url = BASE_URL + "/api/book/cancel";
        try {
            ResponseEntity<CancellingResponseDto> response = restTemplate.exchange(url, HttpMethod.POST
                    , requestEntity, CancellingResponseDto.class);
            CancellingResponseDto cancellingResponseDto = response.getBody();
            if (cancellingResponseDto != null && cancellingResponseDto.getCost() > 0) {
                LOGGER.info("The tickets have been successfully cancelled.\n"
                            + cancellingResponseDto.getCost() + "$ will be deposited into your account within twenty-four hours");
            } else {
                LOGGER.info("Could not able to cancel.");
            }
        } catch (HttpStatusCodeException e) {
            LOGGER.info(e.getResponseBodyAsString());
        }
    }

    public CustomerReservationsResponseDto getAllClientReservations() {
        Scanner scanner = new Scanner(System.in);
        LOGGER.info("enter your national code: ");
        String nationalCode = scanner.nextLine().trim();
        LOGGER.info("enter flight number: ");
        int flightId = scanner.nextInt();
        ReservationDto reservationDto = new ReservationDto(nationalCode, flightId);
        String url = BASE_URL + "/api/book/allCustomerReservations";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReservationDto> requestEntity = new HttpEntity<>(reservationDto, headers);
        ParameterizedTypeReference<CustomerReservationsResponseDto> responseType = new ParameterizedTypeReference<>() {
        };
        CustomerReservationsResponseDto customerReservationsResponseDto = null;
        try {
            ResponseEntity<CustomerReservationsResponseDto> response = restTemplate.exchange(url, HttpMethod.POST
                    , requestEntity, responseType);

            customerReservationsResponseDto = response.getBody();

        } catch (HttpStatusCodeException e) {
            LOGGER.info(e.getResponseBodyAsString());
        }
        return customerReservationsResponseDto;
    }

    public void printCustomerReservationsResponseDto(CustomerReservationsResponseDto customerReservationsResponseDto) {
        LOGGER.info("customerId   flight number   passenger nationalCode  tracingCode");
        for (ReservationGetDto r : customerReservationsResponseDto.getReservationGetDtoList()) {
            LOGGER.info(r.getCustomerId() + "    " + r.getFlightId()
                        + "    " + r.getNationalCode() + "    " + r.getTrackingCode());
        }
    }

    public void getAndPrintAllClientReservations() {
        CustomerReservationsResponseDto customerReservationsResponseDto = getAllClientReservations();
        if (customerReservationsResponseDto != null) {
            printCustomerReservationsResponseDto(customerReservationsResponseDto);
        }
    }

    public void pay() {
        Scanner scanner = new Scanner(System.in);
        try {
            LOGGER.info("Enter your tracking code: ");
            String str = scanner.nextLine();
            pay(str.trim());
        } catch (HttpStatusCodeException e) {
            LOGGER.info(e.getResponseBodyAsString());
        }
    }

    public void pay(String tracingCode) {
        PaymentDto paymentDto = new PaymentDto(tracingCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentDto> requestEntity = new HttpEntity<>(paymentDto, headers);
        String url = BASE_URL + "/api/payment";
        try {
            ResponseEntity<PaymentResponseDto> response = restTemplate.exchange(url, HttpMethod.POST
                    , requestEntity, PaymentResponseDto.class);
            PaymentResponseDto paymentResponseDto = response.getBody();
            LOGGER.info(paymentResponseDto.getCost() + "$ Deducted from your account");
        } catch (HttpStatusCodeException e) {
            LOGGER.info(e.getResponseBodyAsString());
        }
    }
}



