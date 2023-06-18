import dataProvider.ClientProvider;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pojo.UserCreateRequest;
import static org.hamcrest.Matchers.equalTo;

public class TestChangeUserData extends BaseTest {
    UserCreateRequest clientCreateRequest = ClientProvider.getRandomCreateClient();

    @Before
    public void setUp(){
        //создание пользователя
        accessTokenUser = clientUser.create(clientCreateRequest)
                .extract().jsonPath().get("accessToken");
    }

    @Test
    @DisplayName("Can change Auth User api/auth/login")
    public void canChangeAuthorizedUser(){
        // изменение пользователя
        // toLowerCase добавила, тк сайт приводит email к lowerCase всегда
        // изменяю все поля, тк если бы одно из полей не изменилось, пришел другой бы ответ
        clientUser.changeAuthUser(accessTokenUser, clientCreateRequest)
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(clientCreateRequest.getEmail().toLowerCase()))
                .body("user.name", equalTo(clientCreateRequest.getName()));
    }

    @Test
    @DisplayName("Can't change Not Auth User api/auth/login")
    public void cantChangeUnauthorizedUser(){
        // изменение неавторизованного пользователя
        // подаю все поля для изменения, но тк нет токена - ошибка
        clientUser.changeNotAuthUser(clientCreateRequest)
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
