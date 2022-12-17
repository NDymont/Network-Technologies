package lab3;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class Main {
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter location :");
        String nameLocation = scanner.next();

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(URLMakers.makeLocationsURL(nameLocation));
        CompletableFuture<ListPlaces> listPlacesCompletableFuture =
                asyncHttpClient
                        .getResponseCompletableFuture()
                        .thenApply(ListPlaces::new);

        CompletableFuture<Place> placeCompletableFuture = listPlacesCompletableFuture
                .thenApply(listPlaces -> {
                    listPlaces.printListPlaces();
                    Scanner in = new Scanner(System.in);
                    System.out.printf(ANSI_YELLOW + "Choose location: ");
                    int num = in.nextInt();
                    Place location = listPlaces.get(num - 1);
                    return location;
                });

        CompletableFuture<Weather> weatherCompletableFuture =
                placeCompletableFuture.thenCompose(location -> {
                    AsyncHttpClient weatherAsyncHttpClient = new AsyncHttpClient(URLMakers.
                            makeWeatherURL(location.getPoint()));
                    return weatherAsyncHttpClient
                            .getResponseCompletableFuture()
                            .thenApply(Weather::new);

                });

        CompletableFuture<ListAttraction> listAttractionsCompletableFuture =
                placeCompletableFuture.thenCompose(location -> {
                    Scanner in = new Scanner(System.in);
                    System.out.printf("Enter radius: ");
                    int radius = in.nextInt();
                    AsyncHttpClient weatherAsyncHttpClient = new AsyncHttpClient(URLMakers.
                            makeAttractionsURL(location.getPoint(), radius));
                    return weatherAsyncHttpClient
                            .getResponseCompletableFuture()
                            .thenApply(ListAttraction::new);
                });

        ArrayList<CompletableFuture<DescriptionAttraction>> completableFuturesDescriptions = new ArrayList<>();

        CompletableFuture<Void> sendAttractionRequests = listAttractionsCompletableFuture
                .thenAccept(listAttraction -> {
                    if (listAttraction.getSize() == 0) {
                        System.out.println("No attractions found");
                    }
                    int count = 1;
                    for (var attraction : listAttraction) {
                        if (count % 10 == 0) {
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        count++;
                        AsyncHttpClient attractionAsyncHttpClient = new AsyncHttpClient(URLMakers.
                                makeAttractionURL(attraction));
                        completableFuturesDescriptions
                                .add(attractionAsyncHttpClient
                                        .getResponseCompletableFuture()
                                        .thenApply(DescriptionAttraction::new));
                    }
                });

        CompletableFuture<Void> printDescriptions = listAttractionsCompletableFuture
                .thenAccept(listAttraction -> {
                    for (var description : completableFuturesDescriptions) {
                        description.thenAccept(DescriptionAttraction::PrintDescription);
                    }
                });

        CompletableFuture<Void> printListAttractions = listAttractionsCompletableFuture
                .thenAccept(ListAttraction::printListPlaces);

        printDescriptions.join();
        printListAttractions.join();

        CompletableFuture<Void> printWeather = weatherCompletableFuture
                .thenAccept(System.out::println);

        for (var it : completableFuturesDescriptions) {
            it.join();
        }
        printWeather.join();
    }
}
