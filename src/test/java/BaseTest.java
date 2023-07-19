import clients.ClientUser;
import org.junit.After;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {
    public ClientUser clientUser = new ClientUser();
    public String accessTokenUser;
    @After
    public void tearDown() {
        //удаление пользователя
        clientUser.delete(accessTokenUser)
                .statusCode(202)
                .body("success", equalTo(true))
                .body("message",equalTo("User successfully removed"));
    }
}
