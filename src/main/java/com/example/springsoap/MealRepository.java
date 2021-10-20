package com.example.springsoap;

import javax.annotation.PostConstruct;
import java.util.*;


import io.foodmenu.cs.webservice.*;


import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MealRepository {
    private static final Map<String, Meal> meals = new HashMap<String, Meal>();

    @PostConstruct
    public void initData() {

        Meal a = new Meal();
        a.setName("Steak");
        a.setDescription("Steak with fries");
        a.setMealtype(Mealtype.MEAT);
        a.setKcal(1100);
        a.setPrice(10.4);


        meals.put(a.getName(), a);

        Meal b = new Meal();
        b.setName("Portobello");
        b.setDescription("Portobello Mushroom Burger");
        b.setMealtype(Mealtype.VEGAN);
        b.setKcal(637);
        b.setPrice(15.8);


        meals.put(b.getName(), b);

        Meal c = new Meal();
        c.setName("Fish and Chips");
        c.setDescription("Fried fish with chips");
        c.setMealtype(Mealtype.FISH);
        c.setKcal(950);
        c.setPrice(5.5);


        meals.put(c.getName(), c);
    }

    public Meal findMeal(String name) {
        Assert.notNull(name, "The meal's code must not be null");
        return meals.get(name);
    }

    public Meal findBiggestMeal() {

        if (meals == null) return null;
        if (meals.size() == 0) return null;

        var values = meals.values();
        return values.stream().max(Comparator.comparing(Meal::getKcal)).orElseThrow(NoSuchElementException::new);

    }

    public Meal findCheapestMeal() {

        if (meals == null) return null;
        if (meals.size() == 0) return null;

        var values = meals.values();
        return values.stream().min(Comparator.comparing(Meal::getPrice)).orElseThrow(NoSuchElementException::new);

    }

    public OrderConfirmation addOrder(Order order){
        OrderConfirmation orderConfirmation = new OrderConfirmation();

        orderConfirmation.setAddress(order.getAddress());
        orderConfirmation.setMealList(order.getMealList());

        List<String> list = Arrays.asList(order.getMealList().split(","));
        Double price = 0.0;
        for (String name:list){
            Meal m = findMeal(name);
            price += m.getPrice();
        }

        orderConfirmation.setPrice(price);

        return orderConfirmation;
    }

}
