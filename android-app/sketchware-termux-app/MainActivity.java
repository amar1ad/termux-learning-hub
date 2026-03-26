package com.termux.learning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.termux.learning.adapter.CategoryAdapter;
import com.termux.learning.database.DatabaseHelper;
import com.termux.learning.database.FirebaseHelper;

import java.util.HashMap;
import java.util.List;

/**
 * MainActivity - Main Activity for Termux Learning App
 * Displays all categories with Material Design
 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private DatabaseHelper databaseHelper;
    private FirebaseHelper firebaseHelper;
    private SharedPreferences preferences;
    private String currentLanguage = "en";
    private String userId = "user_default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize
        initializeComponents();
        loadCategories();
        setupRecyclerView();
    }

    private void initializeComponents() {
        // Database
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();

        // Firebase
        firebaseHelper = new FirebaseHelper();

        // SharedPreferences
        preferences = getSharedPreferences("TermuxApp", MODE_PRIVATE);
        currentLanguage = preferences.getString("language", "en");
        userId = preferences.getString("user_id", "user_default");

        // UI
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        
        adapter = new CategoryAdapter(this, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(HashMap<String, String> category) {
                openCategory(category);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadCategories() {
        List<HashMap<String, String>> categories = databaseHelper.getAllCategories();
        if (adapter != null) {
            adapter.setCategories(categories);
        }
    }

    private void openCategory(HashMap<String, String> category) {
        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
        intent.putExtra("category_id", category.get("id"));
        intent.putExtra("category_title_ar", category.get("title_ar"));
        intent.putExtra("category_title_en", category.get("title_en"));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_favorites) {
            openFavorites();
            return true;
        } else if (id == R.id.action_settings) {
            openSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchCommands(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            loadCategories();
        } else {
            searchCommands(newText);
        }
        return true;
    }

    private void searchCommands(String query) {
        List<HashMap<String, String>> results = databaseHelper.searchCommands(query);
        
        if (results.isEmpty()) {
            Toast.makeText(this, currentLanguage.equals("ar") ? "لا توجد نتائج" : "No results found", 
                    Toast.LENGTH_SHORT).show();
        }
        
        // Show search results
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("search_query", query);
        startActivity(intent);
    }

    private void openFavorites() {
        Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
        startActivity(intent);
    }

    private void openSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.closeDatabase();
        }
    }
}
