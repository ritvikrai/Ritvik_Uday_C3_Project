import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    private void addRestaurantDetails(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        addRestaurantDetails();
        LocalTime currentTime = restaurant.getCurrentTime();
        boolean result = restaurant.isRestaurantOpen();

        if(currentTime.isAfter(restaurant.openingTime) && currentTime.isBefore(restaurant.closingTime)){
            assertTrue(result);
        }
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        addRestaurantDetails();
        LocalTime currentTime = restaurant.getCurrentTime();
        boolean result = restaurant.isRestaurantOpen();

        if(currentTime.isAfter(restaurant.closingTime) || currentTime.isBefore(restaurant.openingTime)){
            assertFalse(result);
        }

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        addRestaurantDetails();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        addRestaurantDetails();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        addRestaurantDetails();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>CUSTOMER: GETTING ORDER TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void check_order_total(){
        addRestaurantDetails();
        String[] itemNames = new String[2];
        int[] itemPrices = new int[2];
        int expectedOrderTotal = 0;
        itemNames[0]=restaurant.getMenu().get(0).getName();
        itemNames[1]=restaurant.getMenu().get(1).getName();

        itemPrices[0]=restaurant.getMenu().get(0).getPrice();
        itemPrices[1]=restaurant.getMenu().get(1).getPrice();


        for(int itemPrice: itemPrices){
            expectedOrderTotal += itemPrice;
        }

        int actualOrderTotal = restaurant.getOrderTotal(itemNames);
        assertEquals(expectedOrderTotal,actualOrderTotal);
    }
    //<<<<<<<<<<<<<<<<<<<<CUSTOMER: GETTING ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>
}