package dataProvider;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.UserCreateRequest;

public class ClientProvider {
    public static UserCreateRequest getRandomCreateClient() {
        UserCreateRequest clientCreateRequest = new UserCreateRequest();
        clientCreateRequest.setEmail(RandomStringUtils.randomAlphabetic(5)+"@loveburgers.com");
        clientCreateRequest.setPassword(RandomStringUtils.randomAlphabetic(10));
        clientCreateRequest.setName(RandomStringUtils.randomAlphabetic(7));
        return clientCreateRequest;
    }

    public static UserCreateRequest getRandomCreateWithoutEmail() {
        UserCreateRequest clientCreateRequest = new UserCreateRequest();
        clientCreateRequest.setPassword(RandomStringUtils.randomAlphabetic(10));
        clientCreateRequest.setName(RandomStringUtils.randomAlphabetic(7));
        return clientCreateRequest;
    }

    public static UserCreateRequest getRandomCreateWithoutPassword() {
        UserCreateRequest clientCreateRequest = new UserCreateRequest();
        clientCreateRequest.setEmail(RandomStringUtils.randomAlphabetic(5)+"@loveburgers.com");
        clientCreateRequest.setName(RandomStringUtils.randomAlphabetic(7));
        return clientCreateRequest;
    }

    public static UserCreateRequest getRandomCreateWithoutName() {
        UserCreateRequest clientCreateRequest = new UserCreateRequest();
        clientCreateRequest.setEmail(RandomStringUtils.randomAlphabetic(5)+"@loveburgers.com");
        clientCreateRequest.setPassword(RandomStringUtils.randomAlphabetic(10));
        return clientCreateRequest;
    }
}
