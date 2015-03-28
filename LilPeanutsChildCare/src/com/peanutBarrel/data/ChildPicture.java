package com.peanutBarrel.data;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.InputStream;

public class ChildPicture extends Image
{

    private String filePath;
    private InputStream inputStream;

    public ChildPicture(Image img, InputStream inputStream)
    {
        filePath = "DB";
        this.inputStream = inputStream;
    }

    public ChildPicture(String path)
    {
        filePath = path;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public InputStream getInputStream()
    {
        return inputStream;
    }

    public String toString()
    {
        String str = (new StringBuilder("File Path: ")).append(filePath).append("\n").toString();
        str = (new StringBuilder(String.valueOf(str))).append("Superclass.toString(): ").append(super.toString()).toString();
        return str;
    }

    public Graphics getGraphics()
    {
        return null;
    }

    public int getHeight(ImageObserver observer)
    {
        return 0;
    }

    public Object getProperty(String name, ImageObserver observer)
    {
        return null;
    }

    public ImageProducer getSource()
    {
        return null;
    }

    public int getWidth(ImageObserver observer)
    {
        return 0;
    }
}
