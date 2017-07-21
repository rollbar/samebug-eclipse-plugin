package com.samebug.clients.http;

import org.eclipse.jdt.annotation.NonNull;

public final class NewSearch {
    public final String type;
    @NonNull
    public final String query;

    public NewSearch(@NonNull String query) {
        type = "new--search";
        this.query = query;
    }
}