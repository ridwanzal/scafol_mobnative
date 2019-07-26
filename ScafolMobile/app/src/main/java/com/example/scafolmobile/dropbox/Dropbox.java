package com.example.scafolmobile.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

// dropbox module
public class Dropbox {
    private static final String ACCESS_TOKEN = "<ACCESS TOKEN>";
    public static void main(String args[]) throws DbxException {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
    }
}
