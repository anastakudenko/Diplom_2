import clients.ClientUser;
import dataProvider.ClientProvider;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.UserCreateRequest;
import pojo.UserLoginRequest;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class TestLoginUser {
    ClientUser clientUser = new ClientUser();
    String accessTokenUser;
    UserCreateRequest userCreateRequest = ClientProvider.getRandomCreateClient();
    @Before
    public void setUp(){
        //создание пользователя
        accessTokenUser = clientUser.create(userCreateRequest)
                .statusCode(200)
                .body("success", equalTo(true))
                .extract().jsonPath().get("accessToken");
    }

    @Test
    @DisplayName("Can login User api/auth/login")
    public void canLoginUser(){
        // логин пользователя
        UserLoginRequest userLoginRequest = UserLoginRequest.from(userCreateRequest);
        clientUser.login(userLoginRequest)
                .statusCode(200)
                .body("success", equalTo(true))
                .body("$", hasKey("accessToken"))
                .body("$", hasKey("refreshToken"))
                .body("$", hasKey("user"));
    }

    @After
    public void tearDown() {
        //удаление пользователя
        clientUser.delete(accessTokenUser)
                .statusCode(202)
                .body("success", equalTo(true))
                .body("message",equalTo("User successfully removed"));
    }
}
