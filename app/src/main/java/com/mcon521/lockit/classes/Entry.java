package com.mcon521.lockit.classes;

/*import com.google.gson.Gson;*/


public class Entry {
    protected String site;
    protected String username;
    protected String password;

    public Entry(String site, String username) {
        this.site = site;
        this.username = username;
        password = newPassword();
    }

    private String newPassword() {
        Password pass = new Password();
        String word = "";
        for (char x : pass.getPassword()) {
            word += Character.toString(x);
        }
        return word;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = newPassword();
    }

    public String toString() {
        return String.format("Site = %s\nUsername = %s\nPassword = %s", site, username, password);
    }

}

/**
 * Reverses the game object's serialization as a String
 * back to a ThirteenStones game object
 *
 * @param json The serialized String of the game object
 * @return The game object
 *//*

    public static Entry getGameFromJSON (String json)
    {
        Gson gson = new Gson ();
        return gson.fromJson (json, Entry.class);
    }

    *//**
 * Serializes the game object to a JSON-formatted String
 *
 * @param obj Game Object to serialize
 * @return JSON-formatted String
 *//*
    public static String getJSONFromGame (Entry obj)
    {
        Gson gson = new Gson ();
        return gson.toJson (obj);
    }

    public String getJSONFromCurrentGame()
    {
        return getJSONFromGame(this);
    }
}
*/