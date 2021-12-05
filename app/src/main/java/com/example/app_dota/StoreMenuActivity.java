package com.example.app_dota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.app_dota.interfaces.HeroApi;
import com.example.app_dota.interfaces.UserApi;
import com.example.app_dota.model.Heroes;
import com.example.app_dota.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreMenuActivity extends AppCompatActivity {

    private RecyclerView reciclerViewHeroes;
    private HeroesListAdapter heroesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_menu);

        reciclerViewHeroes = (RecyclerView)findViewById(R.id.recicler_dota_heroes);
        heroesAdapter = new HeroesListAdapter(this);
        reciclerViewHeroes.setAdapter(heroesAdapter);
        reciclerViewHeroes.setLayoutManager(new LinearLayoutManager(this));
        GetHeroesDota();
    }


    private void GetHeroesDota(){
        List<Heroes> listaHeroes;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.opendota.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HeroApi service = retrofit.create(HeroApi.class);

        Call<List<Heroes>> call = service.getHeroes();
        call.enqueue(new Callback<List<Heroes>>() {
            @Override
            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {

                if(response.isSuccessful()){
                    List<Heroes> heroesList = response.body();
                   heroesAdapter.adicionarLista(heroesList);

                }
            }

            @Override
            public void onFailure(Call<List<Heroes>> call, Throwable t) {

            }
        });
    }
}