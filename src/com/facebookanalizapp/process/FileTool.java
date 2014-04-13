/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facebookanalizapp.process;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author hp1
 */
public class FileTool {

    private static FileTool fileTool = null;

    private FileTool() {
    }

    public synchronized static FileTool instance() {
        if (fileTool == null) {
            fileTool = new FileTool();
        }
        return fileTool;
    }

    public void deleteDirectory(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            try {
                delete(directory);
            } catch (Exception e) {
                System.out.println("Error : " + e);
            }
        }
    }

    public void delete(File file)
            throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }

        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }
}
