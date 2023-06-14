package clients;
import io.restassured.response.ValidatableResponse;
import pojo.UserCreateRequest;
import pojo.UserLoginRequest;

import static io.restassured.RestAssured.given;

public class ClientUser extends BaseClient {
    public ValidatableResponse create(UserCreateRequest clientCreateRequest){
        return given()
                .spec(getSpecUnauthorized())
                .and()
                .body(clientCreateRequest)
                .when()
                .post("api/auth/register")
                .then();
    }

    public ValidatableResponse login(UserLoginRequest loginCreateRequest){
        return given()
                .spec(getSpecUnauthorized())
                .body(loginCreateRequest)
                .when()
                .post("api/auth/login")
                .then();
    }

    public ValidatableResponse delete(String token){
        return given()
                .spec(getSpecAuthorized(token))
                .when()
                .delete("api/auth/user")
                .then();
    }

    public ValidatableResponse changeAuthUser(String token, UserCreateRequest clientCreateRequest){
        return given()
                .spec(getSpecAuthorized(token))
                .body(clientCreateRequest)
                .when()
                .patch("/api/auth/user")
                .then();
    }
    public ValidatableResponse changeNotAuthUser(UserCreateRequest clientCreateRequest){
        return given()
                .spec(getSpecUnauthorized())
                .body(clientCreateRequest)
                .when()
                .patch("/api/auth/user")
                .then();
    }
}
