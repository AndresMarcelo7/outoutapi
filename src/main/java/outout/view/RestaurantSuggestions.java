package outout.view;

import java.util.List;

public class RestaurantSuggestions {
    private List<RestaurantSuggestion> restaurantSuggestions;

    public RestaurantSuggestions() {
    }
    public RestaurantSuggestions(List<RestaurantSuggestion> restaurantSuggestions) {
        this.restaurantSuggestions = restaurantSuggestions;
    }

    public List<RestaurantSuggestion> getRestaurantSuggestions() {
        return restaurantSuggestions;
    }

    public void setRestaurantSuggestions(final List<RestaurantSuggestion> restaurantSuggestions) {
        this.restaurantSuggestions = restaurantSuggestions;
    }
}
