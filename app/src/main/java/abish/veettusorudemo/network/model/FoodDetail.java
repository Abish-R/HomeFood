package abish.veettusorudemo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abish on 9/4/2017.
 * </p>
 */

public class FoodDetail implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("food_category_id")
    private String foodCategoryId;

    @SerializedName("food_category")
    private String foodCategory;

    @SerializedName("food_name")
    private String foodName;

    @SerializedName("food_short_name")
    private String foodShortName;

    @SerializedName("short_description")
    private String shortDescription;

    @SerializedName("full_description")
    private String fullDescription;

    @SerializedName("price")
    private String price;

    @SerializedName("food_image_url")
    private String foodImageUrl;

    @SerializedName("today_special")
    private String todaySpecial;

    @SerializedName("available_today")
    private String availableToday;

    @SerializedName("available_only_today")
    private String availableOnlyToday;

    @SerializedName("added_date")
    private String addedDate;

    @SerializedName("favourite_course")
    private String favouriteCourse;

    @SerializedName("free")
    private String freeFood;

    @SerializedName("main_course_id")
    private String mainCourseId;

    @SerializedName("offer_details")
    private List<OfferDetail> offerDetails;

    private transient int selectedFoodCount = 0;
    private transient boolean isChecked;
    private transient int priceAfterOffer;
    private transient String isReallyFree;
    private transient int extraFreeFoodCount;

    public FoodDetail() {
    }

    protected FoodDetail(Parcel in) {
        id = in.readInt();
        foodCategoryId = in.readString();
        foodCategory = in.readString();
        foodName = in.readString();
        foodShortName = in.readString();
        shortDescription = in.readString();
        fullDescription = in.readString();
        price = in.readString();
        foodImageUrl = in.readString();
        todaySpecial = in.readString();
        availableToday = in.readString();
        availableOnlyToday = in.readString();
        addedDate = in.readString();
        favouriteCourse = in.readString();
        freeFood = in.readString();
        mainCourseId = in.readString();
        selectedFoodCount = in.readInt();
        priceAfterOffer = in.readInt();
        isReallyFree = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(foodCategoryId);
        parcel.writeString(foodCategory);
        parcel.writeString(foodName);
        parcel.writeString(foodShortName);
        parcel.writeString(shortDescription);
        parcel.writeString(fullDescription);
        parcel.writeString(price);
        parcel.writeString(foodImageUrl);
        parcel.writeString(todaySpecial);
        parcel.writeString(availableToday);
        parcel.writeString(availableOnlyToday);
        parcel.writeString(addedDate);
        parcel.writeString(favouriteCourse);
        parcel.writeString(freeFood);
        parcel.writeString(mainCourseId);
        parcel.writeInt(selectedFoodCount);
        parcel.writeInt(priceAfterOffer);
        parcel.writeString(isReallyFree);
    }

    public static final Creator<FoodDetail> CREATOR = new Creator<FoodDetail>() {
        @Override
        public FoodDetail createFromParcel(Parcel in) {
            return new FoodDetail(in);
        }

        @Override
        public FoodDetail[] newArray(int size) {
            return new FoodDetail[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(String foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodShortName() {
        return foodShortName;
    }

    public void setFoodShortName(String foodShortName) {
        this.foodShortName = foodShortName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String offerPrice) {
        this.addedDate = offerPrice;
    }

    public String getFoodImageUrl() {
        //return "http://www.michaelray.com/wordpress/wp-content/pittsburgh-photographer/PA-food-photographer.jpg";
        return foodImageUrl;
    }

    public void setFoodImageUrl(String foodImageUrl) {
        this.foodImageUrl = foodImageUrl;
    }

    public String getTodaySpecial() {
        return todaySpecial;
    }

    public void setTodaySpecial(String todaySpecial) {
        this.todaySpecial = todaySpecial;
    }

    public String getAvailableToday() {
        return availableToday;
    }

    public void setAvailableToday(String availableToday) {
        this.availableToday = availableToday;
    }

    public String getAvailableOnlyToday() {
        return availableOnlyToday;
    }

    public void setAvailableOnlyToday(String availableOnlyToday) {
        this.availableOnlyToday = availableOnlyToday;
    }

    public boolean isFavourite() {
        return favouriteCourse != null && "1".equals(favouriteCourse);
    }

    public void setFavourite(boolean isFavourite) {
        this.favouriteCourse = isFavourite ? "1" : "0";
    }

    public int getSelectedFoodCountNumber() {
        return selectedFoodCount;
    }

    public String getSelectedFoodCount() {
        return selectedFoodCount + "";
    }

    public void setSelectedFoodCount(int selectedFoodCount) {
        this.selectedFoodCount = selectedFoodCount;
    }

    public String getFreeFood() {
        return freeFood;
    }

    public void setFreeFood(String freeFood) {
        this.freeFood = freeFood;
    }

    public String getExtraFreeFoodCountString() {
        return extraFreeFoodCount + "";
    }

    public int getExtraFreeFoodCount() {
        return extraFreeFoodCount;
    }

    public void setExtraFreeFoodCount(int extraFreeFoodCount) {
        this.extraFreeFoodCount = extraFreeFoodCount ;
    }

    public boolean isFreeFood() {
        return freeFood != null && Integer.valueOf(freeFood) == 1;
    }

    public void setReallyFree(String isReallyFree) {
        this.isReallyFree = isReallyFree;
    }

    public boolean isReallyFree() {
        return isReallyFree != null && isReallyFree.equals("S");
    }

    public String getMainCourseId() {
        return mainCourseId;
    }

    public void setMainCourseId(String mainCourseId) {
        this.mainCourseId = mainCourseId;
    }

    public List<OfferDetail> getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(List<OfferDetail> offerDetails) {
        this.offerDetails = offerDetails;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setPriceAfterOffer(String finalFoodPrice) {
        this.priceAfterOffer = Integer.valueOf(finalFoodPrice);
    }

    public int getPriceAfterOffer() {
        return priceAfterOffer;
    }
}
