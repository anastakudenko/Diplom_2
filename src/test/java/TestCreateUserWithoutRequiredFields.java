import clients.ClientUser;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.UserCreateRequest;
import dataProvider.ClientProvider;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class TestCreateUserWithoutRequiredFields {
    ClientUser clientUser = new ClientUser();
    private final UserCreateRequest currentRequest;
    public TestCreateUserWithoutRequiredFields(UserCreateRequest currentRequest) {
        this.currentRequest = currentRequest;
    }

    @Parameterized.Parameters
    public static Object[][] getUserWithoutRequiredField() {
        return new Object[][]{{ClientProvider.getRandomCreateWithoutName()},{ClientProvider.getRandomCreateWithoutEmail()},{ClientProvider.getRandomCreateWithoutPassword()}};
    }

    @Test
    @DisplayName("Can't Create User Without Required Field api/auth/register")
    public void cantCreateUserWithoutEmail() {
        clientUser.create(currentRequest)
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
