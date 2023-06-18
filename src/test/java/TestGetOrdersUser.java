import clients.ClientOrder;
import dataProvider.ClientProvider;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pojo.OrderCreateRequest;
import pojo.UserCreateRequest;
import java.util.Arrays;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class TestGetOrdersUser extends BaseTest {
    ClientOrder clientOrder = new ClientOrder();
    @Before
    public void setUp(){
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        UserCreateRequest clientCreateRequest = ClientProvider.getRandomCreateClient();
        //создание пользователя
        accessTokenUser = clientUser.create(clientCreateRequest)
                .extract().jsonPath().get("accessToken");
        //создать заказ пользователя
        String ingredient1 = clientOrder.getListIngredients()
                .extract().path("data[0]._id");
        orderCreateRequest.setIngredients(Arrays.asList(ingredient1));
        clientOrder.createOrderAuthorized(orderCreateRequest, accessTokenUser)
                .statusCode(200);
    }

    @Test
    @DisplayName("Can get orders user Authorized /api/orders")
    public void canGetOrdersUserAuthorized() {
        clientOrder.getOrdersUserAuthorized(accessTokenUser)
                .statusCode(200)
                .body("success", equalTo(true))
                .body("$", hasKey("orders"))
                .body("$", hasKey("total"))
                .body("$", hasKey("totalToday"));
    }

    @Test
    @DisplayName("Cant get orders user Unauthorized /api/orders")
    public void cantGetOrdersUserUnauthorized() {
        clientOrder.getOrdersUserUnauthorized()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}

