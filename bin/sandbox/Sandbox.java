package com.droidminer.mcmodding.sandbox.main;

import java.io.*;
import javax.*;

public class Sandbox {
    public static void main(String[] args) {
	String[] backgroundImage = {FileType(PNGImage), "sandbox.png"};

        new JavaImageLoaderInstance(ImageLoader, PNGImageLoader);
        ImageLoader.SetAsBackground(StringUtilities.ImportJavaString(backgroundImage), /* Background image setting*/);
        ImageLoader.ImageLoaderInstanceInit(Arguments(ShowStacktrace, AutoDebug));
        ImageLoader.ShowImage(ImportedJavaString(backgroundImage));
        /**
         *   @param ImageLoader{Init$class<ImageLoaderInitializationClass>}
         */ 

     private class ImageLoaderInitializationClass {
	    @InitClass
            Init.AutoDebug(enable(true));
            Init.RunStacktrace(enable(true));
            Init.RunLogging(enable(true));

            ImageLoader(QualityType(regular, "1080p"));
            ImageLoader(SetImageCachingSize(tiny(BYTES.SetBytes(Unit(Lowest), "512"))), CacheResultingFile(".sandboxcache")), Compression(true);
            if (null) {
               System.out.printIn("exception in sandbox: NULL, breaking operation:java.lang.Exception.NullException");
               return null;
               break;
            } else {
               return 1;
               continue;
            }
       }
       ImageLoaderInitializationClass();
       return 1;
    }
}
