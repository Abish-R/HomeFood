package abish.veettusorudemo;

import android.graphics.drawable.Drawable;

/**
 * Created by INDP on 19-Feb-17.
 */

public class FoodGetter {
    String foodName,foodPrice,foodDescription;
    int foodCount;
    Drawable foodImage;
    boolean isFoodFavEnabled;

    public FoodGetter(){}

    public void setFoodName(String foodName){
        this.foodName=foodName;
    }
    public void setFoodPrice(String foodPrice){
        this.foodPrice=foodPrice;
    }
    public void setFoodCount(int foodCount){
        this.foodCount=foodCount;
    }
    public void setFoodImage(Drawable foodImage){
        this.foodImage=foodImage;
    }
    public void setFoodDescription(String description){
        this.foodDescription=description;
    }
    public void setFavouriteEnabled(boolean isFoodFavEnabled){
        this.isFoodFavEnabled=isFoodFavEnabled;
    }
    public String getFoodName(){
        return this.foodName;
    }
    public String getFoodPrice(){
        return this.foodPrice;
    }
    public int getFoodCount(){
        return this.foodCount;
    }
    public Drawable getFoodImage(){
        return this.foodImage;
    }
    public String getFoodDescription(){
        return this.foodDescription;
    }
    public boolean isFoodFavEnabled(){
        return this.isFoodFavEnabled;
    }
}
