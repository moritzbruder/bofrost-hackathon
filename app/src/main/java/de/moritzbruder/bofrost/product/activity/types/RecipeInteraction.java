package de.moritzbruder.bofrost.product.activity.types;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import de.moritzbruder.bofrost.R;
import de.moritzbruder.bofrost.layout.SpecialTheme;
import de.moritzbruder.bofrost.product.activity.UserInteraction;
import de.moritzbruder.bofrost.user.User;
import de.moritzbruder.bofrost.user.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by morit on 10.06.2017.
 */
public class RecipeInteraction extends UserInteraction.InteractionType {

    public static final RecipeInteraction shared = new RecipeInteraction();

    @Override
    public boolean is(String type) {
        return type.equals("recipe");
    }

    @Override
    public View getViewRepresentation(UserInteraction interaction, String data, String image, boolean friendHighlighted, SpecialTheme theme, Context context) {
        Log.d("QuestionInteraction", "it is");
        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.interaction_recipe, null, false);

        ((TextView) v.findViewById(R.id.textViewRecipe)).setText(data);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.178.28:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final UserAPI uApi = retrofit.create(UserAPI.class);

        uApi.getUser(interaction.getUserid()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                ((TextView) v.findViewById(R.id.textViewCaption)).setText(response.body().getName().split(" ")[0] + "'s Rezept");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        return v;
    }
}
