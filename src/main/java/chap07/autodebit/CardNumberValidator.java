package chap07.autodebit;

import static chap07.autodebit.CardValidity.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CardNumberValidator {
	public CardValidity validate(String cardNumber) {
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://some-external-pg.com/card"))
			.header("Content-Type", "text/plain")
			.POST(HttpRequest.BodyPublishers.ofString(cardNumber))
			.build();
		try {
			HttpResponse<String> response =
				httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			switch (response.body()) {
				case "ok":
					return VALID;
				case "bad":
					return INVALID;
				case "expired":
					return EXPIRED;
				case "theft":
					return THEFT;
				default:
					return UNKNOWN;

			}
		} catch (IOException | InterruptedException exception) {
			return ERROR;
		}
	}
}
