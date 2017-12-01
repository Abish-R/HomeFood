package abish.veettusorudemo.network.serializer;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import abish.veettusorudemo.network.model.OrderData;

/**
 * Created by Abish on 11/26/2017.
 */

public class OrderSerializer implements JsonSerializer<OrderData> {

    @Override
    public JsonObject serialize(OrderData data, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("courseId", data.getCourseId());
        jsonObject.addProperty("foodQuantity", data.getFoodQuantity());
        jsonObject.addProperty("offer_id", data.getOffer_id());
        jsonObject.addProperty("type", data.getType());
        return jsonObject;
    }
}
