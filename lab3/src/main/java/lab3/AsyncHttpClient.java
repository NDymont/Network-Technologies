package lab3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static java.net.http.HttpRequest.newBuilder;

public class AsyncHttpClient {

    String URIAddress;
    HttpRequest request;

    public AsyncHttpClient(String URIAddress) {
        this.URIAddress = URIAddress;
        request = newBuilder()
                .GET()
                .uri(URI.create(URIAddress))
                .build();
    }

    CompletableFuture<String> getResponseCompletableFuture() {
        return HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}