package outout.unit.services;

import outout.model.Restaurant;
import outout.model.Suggestion;
import outout.repository.SuggestionRepository.ISuggestionRepository;
import outout.services.SuggestionService.Exception.SuggestionException;
import outout.services.SuggestionService.ISuggestionService;
import outout.services.SuggestionService.Impl.SuggestionService;
import outout.view.RestaurantSuggestion;
import outout.view.RestaurantSuggestions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SuggestionServiceTests {

    @Mock
    private ISuggestionRepository suggestionRepository;

    @InjectMocks
    private ISuggestionService suggestionService = new SuggestionService();

    @Test
    public void Should_Suggest_Restaurant_By_Date() {
        //Arrange
        Mockito
                .when(suggestionRepository.getSuggestionsByDate(Mockito.any(Date.class)))
                .thenReturn(new ArrayList<Suggestion>(){{
                    add(new Suggestion());
                }});

        //Act
        RestaurantSuggestions response = suggestionService.getSuggestionsByDate(new Date());
        //Assert
        assertEquals(response.getRestaurantSuggestions().size(), 1);
    }

    @Test
    public void Should_Get_Empty_Suggestions() {
        //Arrange
        Mockito
                .when(suggestionRepository.getSuggestionsByDate(Mockito.any(Date.class)))
                .thenReturn(new ArrayList<>());

        //Act
        RestaurantSuggestions response = suggestionService.getSuggestionsByDate(new Date());
        //Assert
        assertEquals(response.getRestaurantSuggestions().size(), 0);
    }

    @Test
    public void Should_Create_Suggestion() throws SuggestionException {
        //Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setName("TestRestaurant");
        RestaurantSuggestion restaurantSuggestion = new RestaurantSuggestion();
        restaurantSuggestion.setRestaurant(restaurant.getName());

        Mockito.doNothing().when(suggestionRepository).createSuggestion(Mockito.any(Suggestion.class));
        Mockito.when(suggestionRepository.GetSuggestionsByUserAndDate(Mockito.anyString(), Mockito.any(Date.class)))
                .thenReturn(new ArrayList<>()); //Suggestions By User
        Mockito.when(suggestionRepository.getSuggestionsByDate(Mockito.any(Date.class))).thenReturn(new ArrayList<>()); //Restaurant Suggestions By Date
        //Act
        suggestionService.suggestRestaurant(restaurantSuggestion, "testme", new Date());
        //Assert
        Mockito.verify(suggestionRepository, Mockito.times(1)).createSuggestion(Mockito.any(Suggestion.class));
    }

    @Test
    public void Should_Not_Create_Suggestion_When_User_Has_More_Than_2_Suggestions() {
        //Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setName("TestRestaurant");
        RestaurantSuggestion restaurantSuggestion = new RestaurantSuggestion();
        restaurantSuggestion.setRestaurant(restaurant.getName());

        Mockito.when(suggestionRepository.GetSuggestionsByUserAndDate(Mockito.anyString(), Mockito.any(Date.class)))
                .thenReturn(new ArrayList<Suggestion>() {{
                    add(new Suggestion());
                    add(new Suggestion());
                }}); //User Suggestions
        Mockito.when(suggestionRepository.getSuggestionsByDate(Mockito.any(Date.class))).thenReturn(new ArrayList<>()); //Restaurant Suggestions By Date
        //Act & Assert
        SuggestionException exception = org.junit.jupiter.api.Assertions.assertThrows(SuggestionException.class, () -> {
            suggestionService.suggestRestaurant(restaurantSuggestion, "testme", new Date());
        });
        assertEquals("Suggestions requirements not met - 2 suggestion per user and 1 suggestion per restaurant per day.", exception.getMessage());
    }
}
