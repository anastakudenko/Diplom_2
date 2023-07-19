package clients;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
public class BaseClient {

    private final String baseUrl = "https://stellarburgers.nomoreparties.site";
    protected RequestSpecification getSpecAuthorized(String token) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", token)
                .setBaseUri(baseUrl)
                .build();
    }

    protected RequestSpecification getSpecUnauthorized() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUrl)
                .build();
    }
}
