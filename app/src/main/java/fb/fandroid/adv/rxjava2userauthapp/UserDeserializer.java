package fb.fandroid.adv.rxjava2userauthapp;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import fb.fandroid.adv.rxjava2userauthapp.model.User;

/**
 * Created by andrew on 11.05.2019.
 */

public class UserDeserializer implements JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();

        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonElement jsonData = jsonObject.get("data");
        final User user = gson.fromJson(jsonData.toString(), User.class);

        return user;
    }
}
