import clients.ClientUser;
import dataProvider.ClientProvider;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import pojo.UserCreateRequest;
import pojo.UserLoginRequest;
import static org.hamcrest.Matchers.equalTo;

public class TestLoginUserWithInvalidData {
    @Test
    @DisplayName("Cant login with invalid Data api/auth/login")
    public void cantLoginUserWithInvalidData(){
        // логин несуществующего  пользователя
        ClientUser clientUser = new ClientUser();
        UserCreateRequest userCreateRequest = ClientProvider.getRandomCreateClient();
        UserLoginRequest userLoginRequest = UserLoginRequest.from(userCreateRequest);
        clientUser.login(userLoginRequest)
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
