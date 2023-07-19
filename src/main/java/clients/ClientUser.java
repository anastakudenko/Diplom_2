package clients;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.UserCreateRequest;
import pojo.UserLoginRequest;

import static io.restassured.RestAssured.given;

public class ClientUser extends BaseClient {
    private final String pathRegister = "api/auth/register";
    private final String pathLogin= "api/auth/login";
    private final String pathUser =  "api/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse create(UserCreateRequest clientCreateRequest){
        return given()
                .spec(getSpecUnauthorized())
                .and()
                .body(clientCreateRequest)
                .when()
                .post(pathRegister)
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse login(UserLoginRequest loginCreateRequest){
        return given()
                .spec(getSpecUnauthorized())
                .body(loginCreateRequest)
                .when()
                .post(pathLogin)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String token){
        return given()
                .spec(getSpecAuthorized(token))
                .when()
                .delete(pathUser)
                .then();
    }

    @Step("Изменение пользователя с авторизацией")
    public ValidatableResponse changeAuthUser(String token, UserCreateRequest clientCreateRequest){
        return given()
                .spec(getSpecAuthorized(token))
                .body(clientCreateRequest)
                .when()
                .patch(pathUser)
                .then();
    }
    @Step("Изменение пользователя без авторизации")
    public ValidatableResponse changeNotAuthUser(UserCreateRequest clientCreateRequest){
        return given()
                .spec(getSpecUnauthorized())
                .body(clientCreateRequest)
                .when()
                .patch(pathUser)
                .then();
    }
}
