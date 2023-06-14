import clients.ClientOrder;
import clients.ClientUser;
import dataProvider.ClientProvider;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.OrderCreateRequest;
import pojo.UserCreateRequest;
import java.util.Arrays;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class TestGetOrdersUser {
    String accessTokenUser;
    ClientOrder clientOrder = new ClientOrder();
    ClientUser clientUser = new ClientUser();
    @Before
    public void setUp(){
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        UserCreateRequest clientCreateRequest = ClientProvider.getRandomCreateClient();
        //создание пользователя
        accessTokenUser = clientUser.create(clientCreateRequest)
                .statusCode(200)
                .body("success", equalTo(true))
                .extract().jsonPath().get("accessToken");
        //создать заказ пользователя
        String ingredient1 = clientOrder.getListIngredients()
                .statusCode(200)
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

    @After
    public void tearDown() {
        //удаление пользователя
        clientUser.delete(accessTokenUser)
                .statusCode(202)
                .body("success", equalTo(true))
                .body("message",equalTo("User successfully removed"));
    }
}

