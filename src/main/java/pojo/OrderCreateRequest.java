package pojo;
import java.util.List;

public class OrderCreateRequest {
    private List<String> ingredients;

    public List<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public  OrderCreateRequest() {
    }
}

