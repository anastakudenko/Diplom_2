package clients;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.OrderCreateRequest;
import static io.restassured.RestAssured.given;


public class ClientOrder extends BaseClient {
    private final String pathIngredients = "/api/ingredients";
    private final String pathOrders = "/api/orders";

    @Step("Получение списка ингридиентов")
    public ValidatableResponse getListIngredients(){
        return given()
                .spec(getSpecUnauthorized())
                .and()
                .when()
                .get(pathIngredients)
                .then();
    }
    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderUnauthorized(OrderCreateRequest orderCreateRequest){
        return given()
                .spec(getSpecUnauthorized())
                .and()
                .body(orderCreateRequest)
                .when()
                .post(pathOrders)
                .then();
    }

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderAuthorized(OrderCreateRequest orderCreateRequest, String token){
        return given()
                .spec(getSpecAuthorized(token))
                .and()
                .body(orderCreateRequest)
                .when()
                .post(pathOrders)
                .then();
    }

    @Step("Получение заказов пользователя без авторизации")
    public ValidatableResponse getOrdersUserUnauthorized(){
        return given()
                .spec(getSpecUnauthorized())
                .and()
                .when()
                .get(pathOrders)
                .then();
    }

    @Step("Получение заказов пользователя с авторизацией")
    public ValidatableResponse getOrdersUserAuthorized(String token){
        return given()
                .spec(getSpecAuthorized(token))
                .and()
                .when()
                .get(pathOrders)
                .then();
    }
}
