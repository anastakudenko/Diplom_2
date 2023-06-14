import clients.ClientOrder;
import clients.ClientUser;
import dataProvider.ClientProvider;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.UserCreateRequest;
import pojo.OrderCreateRequest;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Arrays;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class TestCreateOrder {
    ClientUser clientUser = new ClientUser();
    ClientOrder clientOrder = new ClientOrder();
    OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
    String accessTokenUser;
    String ingredient1;
    String ingredient2;
    @Before
    public void setUp(){
        //создание пользователя
        UserCreateRequest clientCreateRequest = ClientProvider.getRandomCreateClient();
        accessTokenUser = clientUser.create(clientCreateRequest)
                .statusCode(200)
                .body("success", equalTo(true))
                .extract().jsonPath().get("accessToken");
    }

    @Test
    @DisplayName("Can create order Authorized User /api/orders")
    public void canCreateOrder(){
        ingredient1 = clientOrder.getListIngredients()
                .statusCode(200)
                .extract().path("data[0]._id");
        orderCreateRequest.setIngredients(Arrays.asList(ingredient1));
        clientOrder.createOrderAuthorized(orderCreateRequest, accessTokenUser)
                .statusCode(200)
                .body("success", equalTo(true))
                .body("$", hasKey("name"))
                .body("$", hasKey("order"));
    }
    @Test
    @DisplayName("Can create order Unauthorized User /api/orders")
    public void canCreateOrderUnauthorizedUser(){
        ingredient1 = clientOrder.getListIngredients()
                .statusCode(200)
                .extract().path("data[0]._id");
        ingredient2 = clientOrder.getListIngredients()
                .statusCode(200)
                .extract().path("data[1]._id");
        orderCreateRequest.setIngredients(Arrays.asList(ingredient1,ingredient2));
        clientOrder.createOrderUnauthorized(orderCreateRequest)
                .statusCode(200)
                .body("success", equalTo(true))
                .body("$", hasKey("name"))
                .body("$", hasKey("order"));
    }

    @Test
    @DisplayName("Can't create order Without Ingredients /api/orders")
    public void cantCreateOrderWithoutIngredients(){
        clientOrder.createOrderAuthorized(orderCreateRequest, accessTokenUser)
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Can't create order With Invalid id Ingredient /api/orders")
    public void cantCreateOrderInvalidIdIngredient(){
        // в документации прописано 500 при невалидном хеше, если поставить 25 символов, будет 400.
        // Но на 400 мы проверяем и в тесте на отсутсвие ингридиентов, поэтому решила такую проверку оставить
        // тк 500 указано в документации
        orderCreateRequest.setIngredients(Arrays.asList(RandomStringUtils.randomAlphabetic(24)));
        clientOrder.createOrderAuthorized(orderCreateRequest, accessTokenUser)
                .statusCode(500);
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
