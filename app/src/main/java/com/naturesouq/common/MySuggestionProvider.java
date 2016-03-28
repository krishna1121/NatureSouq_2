package com.naturesouq.common;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by SI_Android_Binit on 2/9/2016.
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.naturesouq.common.MySuggestionProvider";
   // public final static String AUTHORITY = MySuggestionProvider.class.getName();
    //public final static int MODE = DATABASE_MODE_QUERIES;
    public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
