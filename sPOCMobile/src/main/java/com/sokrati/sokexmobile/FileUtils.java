package com.sokrati.sokexmobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.os.Bundle;

public class FileUtils
{
    private File uploadDir;
    private File tempDir;
    private File tempFile;
    private Long maxFileSize; // in bytes
    private Long maxFileCount;

    public FileUtils(String uploadDir, String tempDir,
                     Long maxFileSize, Long maxFileCount)
        throws IOException
    {
        this.uploadDir = new File(uploadDir);
        if (!this.uploadDir.exists())
        {
            this.uploadDir.mkdir();
        }
        this.tempDir = new File(tempDir);
        if (!this.tempDir.exists())
        {
            this.tempDir.mkdir();
        }
        // check in the tempDir
        if (this.tempDir.list().length > 0)
        {
            tempFile = new File(tempDir, this.tempDir.list()[0]);
        }
        else
        {
            tempFile = File.createTempFile("prefix", "suffix", this.tempDir);
        }
        this.maxFileSize = maxFileSize;
        this.maxFileCount = maxFileCount;
    }

    /*
     * Add lead params to file.
     */
    public void addParams(Bundle bundle)
        throws IOException
    {
        if (null == tempFile)
        {
            tempFile = File.createTempFile("prefix", "suffix", tempDir);
        }
        else if (tempFile.length() > maxFileSize)
        {
            if (uploadDir.list().length >= maxFileCount)
            {
                // max no of files reached
                // send the data
            }
            tempFile.renameTo(
                new File(uploadDir.getAbsolutePath() +
                         "/" + tempFile.getName())
                );
            tempFile = File.createTempFile("prefix", "suffix", tempDir);
        }
        FileWriter writer = new FileWriter(tempFile, true);
        writer.write(bundle.toString());
        writer.close();
    }

    /*
     * Read lead params from file
     */
    public ArrayList<String> getParams()
    {
        ArrayList<String> out = new ArrayList<String>();
        if (isPresent())
        {
            String fileName =
                uploadDir.getAbsoluteFile() + "/" + uploadDir.list()[0];
            File file = new File(fileName);
            InputStream instream = null;
            try
            {
                instream = new FileInputStream(fileName);
                if (instream != null)
                {
                    // prepare the file for reading
                    InputStreamReader inputreader =
                        new InputStreamReader(instream);
                    BufferedReader buffreader =
                        new BufferedReader(inputreader);
                    String line;
                    while (buffreader.ready())
                    {
                        line = buffreader.readLine();
                        out.add(line);
                    }
                }
                // Deleting the file after reading
                file.delete();
            }
            catch (Exception ex)
            {
                System.out.println(ex.getLocalizedMessage());
            }
            finally
            {
                // close the file.
                try
                {
                    instream.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else
        {
            // No data is present to upload.
        }
        return out;
    }

    /*
     * Checks the file is present in the directory.
     */
    public boolean isPresent()
    {
        boolean flag = false;
        if (null != uploadDir && uploadDir.list().length > 0)
        {
            flag = true;
        }
        return flag;
    }
}
