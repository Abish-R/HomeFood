package abish.veettusorudemo.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abish on 9/4/2017.
 */

public class OfferDetail implements Parcelable {

    @SerializedName("offer_id")
    private String offer_id;

    @SerializedName("offer_name")
    private String offerName;

    @SerializedName("offer_description")
    private String offerDescription;

    @SerializedName("offer_price")
    private String offerPrice;

    @SerializedName("offer_price_percentage")
    private String offerPricePercentage;

    @SerializedName("created_date")
    private String createdDate;

    public OfferDetail(){
        // Empty Constructor
    }

    protected OfferDetail(Parcel in) {
        offer_id = in.readString();
        offerName = in.readString();
        offerDescription = in.readString();
        offerPrice = in.readString();
        offerPricePercentage = in.readString();
        createdDate = in.readString();
    }

    public static final Creator<OfferDetail> CREATOR = new Creator<OfferDetail>() {
        @Override
        public OfferDetail createFromParcel(Parcel in) {
            return new OfferDetail(in);
        }

        @Override
        public OfferDetail[] newArray(int size) {
            return new OfferDetail[size];
        }
    };

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferPricePercentage() {
        return offerPricePercentage;
    }

    public void setOfferPricePercentage(String offerPricePercentage) {
        this.offerPricePercentage = offerPricePercentage;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(offer_id);
        parcel.writeString(offerName);
        parcel.writeString(offerDescription);
        parcel.writeString(offerPrice);
        parcel.writeString(offerPricePercentage);
        parcel.writeString(createdDate);
    }
}
