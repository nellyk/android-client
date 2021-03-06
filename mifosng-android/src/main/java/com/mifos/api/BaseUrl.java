/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.mifos.api;

/**
 * @author fomenkoo
 */
public class BaseUrl {

    public static final String PROTOCOL_HTTPS = "https://";
    public static final String API_ENDPOINT = "demo.openmf.org";
    public static final String API_PATH = "/fineract-provider/api/v1/";
    // "/" in the last of the base url always


    private String url;

    public void updateInstanceUrl(String instanceUrl) {
        this.url = instanceUrl;
    }


    public String getUrl() {
        if (url == null)
            return PROTOCOL_HTTPS + API_ENDPOINT + API_PATH;
        return url;
    }

    public String getName() {
        return "mifos";
    }
}
