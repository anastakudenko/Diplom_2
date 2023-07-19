import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import pojo.UserCreateRequest;
import dataProvider.ClientProvider;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class TestCreateUserWithRequiredFields extends BaseTest{
    UserCreateRequest userLoginRequest = ClientProvider.getRandomCreateClient();

    @Test
    @DisplayName("Can Create User api/auth/register")
    //создание пользователя с валидными полями
    public void canCreateUser(){
        accessTokenUser = clientUser.create(userLoginRequest)
                .statusCode(200)
                .body("success", equalTo(true))
                .body("$", hasKey("user"))
                .body("$", hasKey("refreshToken"))
                .extract().jsonPath().get("accessToken");
    }

    @Test
    @DisplayName("Can't Create Two identical Users api/auth/register")
    //создание пользователя, который уже существует
    public void cantCreateTwoIdenticalUsers() {
        accessTokenUser = clientUser.create(userLoginRequest)
                .extract().jsonPath().get("accessToken");
        clientUser.create(userLoginRequest)
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }
}
