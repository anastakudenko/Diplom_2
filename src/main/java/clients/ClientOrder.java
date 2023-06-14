package clients;
import io.restassured.response.ValidatableResponse;
import pojo.OrderCreateRequest;

import static io.restassured.RestAssured.given;

public class ClientOrder extends BaseClient {

    public ValidatableResponse getListIngredients(){
        return given()
                .spec(getSpecUnauthorized())
                .and()
                .when()
                .get("/api/ingredients")
                .then();
    }
    public ValidatableResponse createOrderUnauthorized(OrderCreateRequest orderCreateRequest){
        return given()
                .spec(getSpecUnauthorized())
                .and()
                .body(orderCreateRequest)
                .when()
                .post("/api/orders")
                .then();
    }

    public ValidatableResponse createOrderAuthorized(OrderCreateRequest orderCreateRequest, String token){
        return given()
                .spec(getSpecAuthorized(token))
                .and()
                .body(orderCreateRequest)
                .when()
                .post("/api/orders")
                .then();
    }

    public ValidatableResponse getOrdersUserUnauthorized(){
        return given()
                .spec(getSpecUnauthorized())
                .and()
                .when()
                .get("/api/orders")
                .then();
    }

    public ValidatableResponse getOrdersUserAuthorized(String token){
        return given()
                .spec(getSpecAuthorized(token))
                .and()
                .when()
                .get("/api/orders")
                .then();
    }
}
