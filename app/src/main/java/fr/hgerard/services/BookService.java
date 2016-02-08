package fr.hgerard.services;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import timber.log.Timber;

/**
 * Created by hadri on 27/01/2016.
 */
public class BookService {

    private static HenriPotierService instance;

    public static HenriPotierService getInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://henri-potier.xebia.fr/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(HenriPotierService.class);
        }
        return instance;
    }

}
