package fr.hgerard.services;

import java.util.List;

import fr.hgerard.model.Book;
import retrofit.Call;
import retrofit.http.GET;

public interface HenriPotierService {

   @GET("books")
   Call<List<Book>> listBooks();

}
