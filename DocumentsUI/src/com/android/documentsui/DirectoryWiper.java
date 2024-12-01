package com.android.documentsui;
import java.io.File;
import android.util.Log;


public class DirectoryWiper{
    private static final String TAG = "BaseActivity";
    public static boolean wipeDirectory(File directory){
        // If Directory specified is invalid
        if(directory == null || !directory.exists()){
                Log.d(TAG, "Directory not found!!");
                return false;
        }
        // If the directory specified is actually just a file
        if(!directory.isDirectory()){
            directory.delete();
        }


        File[] files = directory.listFiles();

        if (files != null){
            for (File file: files){
                Log.d(TAG, "File inside directory is: " + file.getName());
                if (file.isDirectory()){
                    Log.d(TAG, "Found a directory. Attempting Recursive delete of " + file);
                    // Recursive deletion of directories
                    wipeDirectory(file);
                    Log.d(TAG, "Recursive delete should have been done by now!");
                    try{
                        String name = file.getName();
                        file.delete();
                        Log.d(TAG,"Following Directory was deleted: " + name);
                    } catch (java.lang.Exception e) {
                        Log.d(TAG, "Failed to delete directory. retrying!");
                        if (!file.delete()){
                            Log.d(TAG, "Failed Again! Because of: " + e);

                        }
                        else{
                            Log.d(TAG, "Directory was deleted this time!");
                        }
                    }
                }
                else {
                    try{
                        String name = file.getName();
                        file.delete();
                        Log.d(TAG, "DELETED :" + name);
                    } catch (Exception e) {
                        Log.d(TAG, "Following error caught:" + e);
                    }
                }
                if (!file.delete() && !file.isDirectory()){
                    // If deletion of a file failed.
                    Log.d(TAG, "Failed to delete following file: " + file);
                    file.delete();
                }
                else{

                }
            }
        }

        //Deleting the parent folder itself!
        directory.delete();

        return true; // If all deletion of files were successful.
    }

}