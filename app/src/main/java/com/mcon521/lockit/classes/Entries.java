package com.mcon521.lockit.classes;

import com.google.gson.Gson;

import java.util.ArrayList;


public class Entries {


    protected ArrayList<Entry> passwordList = new ArrayList<Entry>();


    public ArrayList<Entry> getPasswordList(){
        return passwordList;
    }

    public void addEntryToList(Entry submission){
        passwordList.add(submission);
    }

    public Integer getSizeOfPassWordList(){
        return passwordList.size();
    }


    /*delete password from list*/
    public boolean delteAPassword(String title){

        if (passwordList.size() > 0){
            for(int i = 0; i <passwordList.size(); i++ ){
                if(passwordList.get(i).getSite().equals(title)){
                    passwordList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /*edit password from list*/

    public boolean editAnEntry(String title, String newTitle, String newUser, String newPassword){
        if (passwordList.size() > 0){
            for(int i = 0; i < passwordList.size(); i++ ){
                if(passwordList.get(i).getSite().equals(title)){
                    Entry newEdit = passwordList.get(i);
                    newEdit.setSite(newTitle);
                    newEdit.setUsername(newUser);
                    newEdit.setPassword(newPassword);
                    return true;
                }
            }
        }
        return false;
    }

    public static String getJSONFromEntries (Entries obj)
    {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }

    /*public static Entries getEntriesFromJSON (String json)
    {
        Gson gson = new Gson ();
        return gson.fromJson (json, Entries.class);
    }

    public String getJSONFromEntriesList()
    {
        return getJSONFromEntries(this);
    }
*/



}
