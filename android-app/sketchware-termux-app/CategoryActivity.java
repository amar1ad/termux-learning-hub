package com.termux.learning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.termux.learning.adapter.CommandAdapter;
import com.termux.learning.database.DatabaseHelper;

import java.util.HashMap;
import java.util.List;

/**
 * CategoryActivity - Display commands in a category
 */
public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommandAdapter adapter;
    private DatabaseHelper databaseHelper;
    private SharedPreferences preferences;
    private String currentLanguage = "en";
    private String categoryId;
    private String categoryTitleAr;
    private String categoryTitleEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Get data from intent
        Intent intent = getIntent();
        categoryId = intent.getStringExtra("category_id");
        categoryTitleAr = intent.getStringExtra("category_title_ar");
        categoryTitleEn = intent.getStringExtra("category_title_en");

        // Initialize
        initializeComponents();
        setupActionBar();
        loadCommands();
        setupRecyclerView();
    }

    private void initializeComponents() {
        // Database
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();

        // SharedPreferences
        preferences = getSharedPreferences("TermuxApp", MODE_PRIVATE);
        currentLanguage = preferences.getString("language", "en");

        // UI
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            String title = currentLanguage.equals("ar") ? categoryTitleAr : categoryTitleEn;
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CommandAdapter(this, new CommandAdapter.OnCommandClickListener() {
            @Override
            public void onCommandClick(HashMap<String, String> command) {
                openCommandDetail(command);
            }
        }, currentLanguage);
        recyclerView.setAdapter(adapter);
    }

    private void loadCommands() {
        List<HashMap<String, String>> commands = databaseHelper.getCommandsByCategory(categoryId);
        if (adapter != null) {
            adapter.setCommands(commands);
        }
    }

    private void openCommandDetail(HashMap<String, String> command) {
        Intent intent = new Intent(CategoryActivity.this, CommandDetailActivity.class);
        intent.putExtra("command_id", command.get("id"));
        intent.putExtra("command_title_ar", command.get("title_ar"));
        intent.putExtra("command_title_en", command.get("title_en"));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.closeDatabase();
        }
    }
}
