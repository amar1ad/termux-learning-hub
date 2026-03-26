package com.termux.learning.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Firebase Database Helper for Termux Learning App
 * Handles cloud synchronization and data backup
 * Version: 1.0.0
 */
public class FirebaseHelper {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    // ==================== Categories ====================

    /**
     * Upload categories to Firebase
     */
    public void uploadCategories(List<HashMap<String, String>> categories) {
        DatabaseReference categoriesRef = databaseReference.child("categories");
        for (HashMap<String, String> category : categories) {
            String categoryId = category.get("id");
            categoriesRef.child(categoryId).setValue(category);
        }
    }

    /**
     * Get categories from Firebase
     */
    public void getCategories(final FirebaseCallback callback) {
        DatabaseReference categoriesRef = databaseReference.child("categories");
        categoriesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, String>> categories = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> category = (HashMap<String, String>) snapshot.getValue();
                    if (category != null) {
                        categories.add(category);
                    }
                }
                callback.onSuccess(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // ==================== Commands ====================

    /**
     * Upload commands to Firebase
     */
    public void uploadCommands(List<HashMap<String, String>> commands) {
        DatabaseReference commandsRef = databaseReference.child("commands");
        for (HashMap<String, String> command : commands) {
            String commandId = command.get("id");
            commandsRef.child(commandId).setValue(command);
        }
    }

    /**
     * Get commands by category from Firebase
     */
    public void getCommandsByCategory(String categoryId, final FirebaseCallback callback) {
        DatabaseReference commandsRef = databaseReference.child("commands");
        commandsRef.orderByChild("category_id").equalTo(categoryId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<HashMap<String, String>> commands = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HashMap<String, String> command = (HashMap<String, String>) snapshot.getValue();
                            if (command != null) {
                                commands.add(command);
                            }
                        }
                        callback.onSuccess(commands);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onError(databaseError.getMessage());
                    }
                });
    }

    /**
     * Get command details from Firebase
     */
    public void getCommandDetails(String commandId, final FirebaseCallback callback) {
        DatabaseReference commandRef = databaseReference.child("commands").child(commandId);
        commandRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> command = (HashMap<String, String>) dataSnapshot.getValue();
                if (command != null) {
                    callback.onSuccess(command);
                } else {
                    callback.onError("Command not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // ==================== Examples ====================

    /**
     * Upload command examples to Firebase
     */
    public void uploadExamples(String commandId, List<HashMap<String, String>> examples) {
        DatabaseReference examplesRef = databaseReference.child("commands").child(commandId).child("examples");
        for (int i = 0; i < examples.size(); i++) {
            examplesRef.child(String.valueOf(i)).setValue(examples.get(i));
        }
    }

    /**
     * Get command examples from Firebase
     */
    public void getExamples(String commandId, final FirebaseCallback callback) {
        DatabaseReference examplesRef = databaseReference.child("commands").child(commandId).child("examples");
        examplesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, String>> examples = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> example = (HashMap<String, String>) snapshot.getValue();
                    if (example != null) {
                        examples.add(example);
                    }
                }
                callback.onSuccess(examples);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // ==================== Tips ====================

    /**
     * Upload command tips to Firebase
     */
    public void uploadTips(String commandId, List<HashMap<String, String>> tips) {
        DatabaseReference tipsRef = databaseReference.child("commands").child(commandId).child("tips");
        for (int i = 0; i < tips.size(); i++) {
            tipsRef.child(String.valueOf(i)).setValue(tips.get(i));
        }
    }

    /**
     * Get command tips from Firebase
     */
    public void getTips(String commandId, final FirebaseCallback callback) {
        DatabaseReference tipsRef = databaseReference.child("commands").child(commandId).child("tips");
        tipsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, String>> tips = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> tip = (HashMap<String, String>) snapshot.getValue();
                    if (tip != null) {
                        tips.add(tip);
                    }
                }
                callback.onSuccess(tips);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // ==================== Errors ====================

    /**
     * Upload command errors to Firebase
     */
    public void uploadErrors(String commandId, List<HashMap<String, String>> errors) {
        DatabaseReference errorsRef = databaseReference.child("commands").child(commandId).child("errors");
        for (int i = 0; i < errors.size(); i++) {
            errorsRef.child(String.valueOf(i)).setValue(errors.get(i));
        }
    }

    /**
     * Get command errors from Firebase
     */
    public void getErrors(String commandId, final FirebaseCallback callback) {
        DatabaseReference errorsRef = databaseReference.child("commands").child(commandId).child("errors");
        errorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, String>> errors = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> error = (HashMap<String, String>) snapshot.getValue();
                    if (error != null) {
                        errors.add(error);
                    }
                }
                callback.onSuccess(errors);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // ==================== Favorites ====================

    /**
     * Add command to favorites
     */
    public void addFavorite(String userId, String commandId) {
        DatabaseReference favoritesRef = databaseReference.child("users").child(userId).child("favorites");
        favoritesRef.child(commandId).setValue(true);
    }

    /**
     * Remove command from favorites
     */
    public void removeFavorite(String userId, String commandId) {
        DatabaseReference favoritesRef = databaseReference.child("users").child(userId).child("favorites");
        favoritesRef.child(commandId).removeValue();
    }

    /**
     * Get user favorites from Firebase
     */
    public void getFavorites(String userId, final FirebaseCallback callback) {
        DatabaseReference favoritesRef = databaseReference.child("users").child(userId).child("favorites");
        favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> favorites = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    favorites.add(snapshot.getKey());
                }
                callback.onSuccess(favorites);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    /**
     * Check if command is favorite
     */
    public void isFavorite(String userId, String commandId, final FirebaseCallback callback) {
        DatabaseReference favRef = databaseReference.child("users").child(userId).child("favorites").child(commandId);
        favRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isFavorite = dataSnapshot.exists();
                callback.onSuccess(isFavorite);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // ==================== Search History ====================

    /**
     * Add search query to history
     */
    public void addSearchHistory(String userId, String query, String language) {
        DatabaseReference historyRef = databaseReference.child("users").child(userId).child("search_history");
        HashMap<String, Object> searchEntry = new HashMap<>();
        searchEntry.put("query", query);
        searchEntry.put("language", language);
        searchEntry.put("timestamp", System.currentTimeMillis());
        historyRef.push().setValue(searchEntry);
    }

    /**
     * Get search history for user
     */
    public void getSearchHistory(String userId, final FirebaseCallback callback) {
        DatabaseReference historyRef = databaseReference.child("users").child(userId).child("search_history");
        historyRef.limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, String>> history = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> entry = (HashMap<String, String>) snapshot.getValue();
                    if (entry != null) {
                        history.add(entry);
                    }
                }
                callback.onSuccess(history);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // ==================== User Settings ====================

    /**
     * Save user settings (language, theme, etc.)
     */
    public void saveUserSettings(String userId, HashMap<String, Object> settings) {
        DatabaseReference settingsRef = databaseReference.child("users").child(userId).child("settings");
        settingsRef.setValue(settings);
    }

    /**
     * Get user settings
     */
    public void getUserSettings(String userId, final FirebaseCallback callback) {
        DatabaseReference settingsRef = databaseReference.child("users").child(userId).child("settings");
        settingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> settings = (HashMap<String, Object>) dataSnapshot.getValue();
                if (settings != null) {
                    callback.onSuccess(settings);
                } else {
                    callback.onError("Settings not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // ==================== Search ====================

    /**
     * Search commands in Firebase
     */
    public void searchCommands(String query, final FirebaseCallback callback) {
        DatabaseReference commandsRef = databaseReference.child("commands");
        commandsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, String>> results = new ArrayList<>();
                String lowerQuery = query.toLowerCase();
                
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> command = (HashMap<String, String>) snapshot.getValue();
                    if (command != null) {
                        String titleAr = command.get("title_ar") != null ? command.get("title_ar").toLowerCase() : "";
                        String titleEn = command.get("title_en") != null ? command.get("title_en").toLowerCase() : "";
                        String descAr = command.get("description_ar") != null ? command.get("description_ar").toLowerCase() : "";
                        String descEn = command.get("description_en") != null ? command.get("description_en").toLowerCase() : "";
                        String syntax = command.get("command_syntax") != null ? command.get("command_syntax").toLowerCase() : "";
                        
                        if (titleAr.contains(lowerQuery) || titleEn.contains(lowerQuery) ||
                            descAr.contains(lowerQuery) || descEn.contains(lowerQuery) ||
                            syntax.contains(lowerQuery)) {
                            results.add(command);
                        }
                    }
                }
                callback.onSuccess(results);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // ==================== Sync ====================

    /**
     * Sync local database with Firebase
     */
    public void syncDatabase(DatabaseHelper dbHelper, String userId) {
        // Sync categories
        uploadCategories(dbHelper.getAllCategories());
        
        // Sync favorites
        uploadFavorites(userId, dbHelper);
    }

    /**
     * Upload favorites to Firebase
     */
    private void uploadFavorites(String userId, DatabaseHelper dbHelper) {
        List<HashMap<String, String>> favorites = dbHelper.getFavorites(userId);
        for (HashMap<String, String> favorite : favorites) {
            String commandId = favorite.get("id");
            addFavorite(userId, commandId);
        }
    }

    // ==================== Callback Interface ====================

    public interface FirebaseCallback {
        void onSuccess(Object data);
        void onError(String error);
    }
}
