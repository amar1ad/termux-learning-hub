package com.termux.learning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.termux.learning.adapter.ExampleAdapter;
import com.termux.learning.adapter.TipAdapter;
import com.termux.learning.adapter.ErrorAdapter;
import com.termux.learning.database.DatabaseHelper;
import com.termux.learning.database.FirebaseHelper;

import java.util.HashMap;
import java.util.List;

/**
 * CommandDetailActivity - Display full command details
 */
public class CommandDetailActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private TextView titleView, descriptionView, syntaxView;
    private Button copyButton, shareButton, favoriteButton;
    private RecyclerView examplesRecyclerView, tipsRecyclerView, errorsRecyclerView;
    private LinearLayout examplesContainer, tipsContainer, errorsContainer;

    private DatabaseHelper databaseHelper;
    private FirebaseHelper firebaseHelper;
    private SharedPreferences preferences;
    private String currentLanguage = "en";
    private String userId = "user_default";
    private String commandId;
    private String commandTitleAr;
    private String commandTitleEn;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_detail);

        // Get data from intent
        Intent intent = getIntent();
        commandId = intent.getStringExtra("command_id");
        commandTitleAr = intent.getStringExtra("command_title_ar");
        commandTitleEn = intent.getStringExtra("command_title_en");

        // Initialize
        initializeComponents();
        setupActionBar();
        loadCommandDetails();
        setupButtons();
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
        scrollView = findViewById(R.id.scrollView);
        titleView = findViewById(R.id.titleView);
        descriptionView = findViewById(R.id.descriptionView);
        syntaxView = findViewById(R.id.syntaxView);
        copyButton = findViewById(R.id.copyButton);
        shareButton = findViewById(R.id.shareButton);
        favoriteButton = findViewById(R.id.favoriteButton);

        examplesRecyclerView = findViewById(R.id.examplesRecyclerView);
        tipsRecyclerView = findViewById(R.id.tipsRecyclerView);
        errorsRecyclerView = findViewById(R.id.errorsRecyclerView);

        examplesContainer = findViewById(R.id.examplesContainer);
        tipsContainer = findViewById(R.id.tipsContainer);
        errorsContainer = findViewById(R.id.errorsContainer);
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            String title = currentLanguage.equals("ar") ? commandTitleAr : commandTitleEn;
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadCommandDetails() {
        // Get command details
        HashMap<String, String> command = databaseHelper.getCommandDetails(commandId);

        if (!command.isEmpty()) {
            // Set title and description
            if (currentLanguage.equals("ar")) {
                titleView.setText(command.get("title_ar"));
                descriptionView.setText(command.get("description_ar"));
            } else {
                titleView.setText(command.get("title_en"));
                descriptionView.setText(command.get("description_en"));
            }

            // Set command syntax
            syntaxView.setText(command.get("command_syntax"));

            // Update usage count
            databaseHelper.updateUsageCount(commandId);

            // Load examples
            loadExamples();

            // Load tips
            loadTips();

            // Load errors
            loadErrors();

            // Check if favorite
            checkIfFavorite();
        }
    }

    private void loadExamples() {
        List<HashMap<String, String>> examples = databaseHelper.getCommandExamples(commandId);

        if (!examples.isEmpty()) {
            examplesContainer.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            examplesRecyclerView.setLayoutManager(layoutManager);

            ExampleAdapter adapter = new ExampleAdapter(this, examples, currentLanguage);
            examplesRecyclerView.setAdapter(adapter);
        } else {
            examplesContainer.setVisibility(View.GONE);
        }
    }

    private void loadTips() {
        List<HashMap<String, String>> tips = databaseHelper.getCommandTips(commandId);

        if (!tips.isEmpty()) {
            tipsContainer.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            tipsRecyclerView.setLayoutManager(layoutManager);

            TipAdapter adapter = new TipAdapter(this, tips, currentLanguage);
            tipsRecyclerView.setAdapter(adapter);
        } else {
            tipsContainer.setVisibility(View.GONE);
        }
    }

    private void loadErrors() {
        List<HashMap<String, String>> errors = databaseHelper.getCommandErrors(commandId);

        if (!errors.isEmpty()) {
            errorsContainer.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            errorsRecyclerView.setLayoutManager(layoutManager);

            ErrorAdapter adapter = new ErrorAdapter(this, errors, currentLanguage);
            errorsRecyclerView.setAdapter(adapter);
        } else {
            errorsContainer.setVisibility(View.GONE);
        }
    }

    private void checkIfFavorite() {
        isFavorite = databaseHelper.isFavorite(userId, commandId);
        updateFavoriteButton();
    }

    private void setupButtons() {
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCommand();
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });
    }

    private void copyToClipboard() {
        String syntax = syntaxView.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("command", syntax);
        clipboard.setPrimaryClip(clip);

        String message = currentLanguage.equals("ar") ? "تم النسخ إلى الحافظة" : "Copied to clipboard";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void shareCommand() {
        String syntax = syntaxView.getText().toString();
        String title = currentLanguage.equals("ar") ? commandTitleAr : commandTitleEn;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Command: " + syntax + "\n\nFrom Termux Learning App");
        startActivity(Intent.createChooser(shareIntent, "Share Command"));
    }

    private void toggleFavorite() {
        if (isFavorite) {
            databaseHelper.removeFavorite(userId, commandId);
            firebaseHelper.removeFavorite(userId, commandId);
            isFavorite = false;
        } else {
            databaseHelper.addFavorite(userId, commandId);
            firebaseHelper.addFavorite(userId, commandId);
            isFavorite = true;
        }
        updateFavoriteButton();

        String message = isFavorite ?
                (currentLanguage.equals("ar") ? "تمت الإضافة إلى المفضلة" : "Added to favorites") :
                (currentLanguage.equals("ar") ? "تمت الإزالة من المفضلة" : "Removed from favorites");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateFavoriteButton() {
        String text = isFavorite ?
                (currentLanguage.equals("ar") ? "✓ مفضل" : "✓ Favorite") :
                (currentLanguage.equals("ar") ? "☆ أضف إلى المفضلة" : "☆ Add to Favorites");
        favoriteButton.setText(text);
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
