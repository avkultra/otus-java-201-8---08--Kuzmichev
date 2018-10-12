package ru.otus.H041;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class RefHlp {
    public static ArrayList<Class> getPackageClss(final String pckName)    throws IOException, URISyntaxException, ClassNotFoundException{
        ArrayList<String> classNames = getPackClass(pckName);
        ArrayList<Class> result = new ArrayList<Class>();

        for (String className : classNames) {
            String allName = pckName + "." + className;
            Class cls = Class.forName(allName);
            result.add(cls);
        }

        return result;
    }

    private static ArrayList<String> getPackClass(final String pckName)
            throws IOException, URISyntaxException {
        ArrayList<String> names = new ArrayList<String>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String pckUrl = pckName.replace(".", "/");
        URL url = classLoader.getResource(pckUrl);

        if (/*url.getProtocol().contains("jar")*/ true) {
            String Name;

            String jarFile = URLDecoder.decode(url.getFile(), "UTF-8");

            jarFile = jarFile.substring(5, jarFile.indexOf("!"));

            JarFile jf = new JarFile(jarFile);

            Enumeration<JarEntry> entries = jf.entries();

            while (entries.hasMoreElements()) {
                Name = entries.nextElement().getName();

                if (Name.startsWith(pckUrl) && Name.length() > pckUrl.length() + 5) {
                    Name = Name.substring(pckUrl.length() + 1, Name.lastIndexOf('.'));
                    names.add(Name);
                }
            }

        } else {


            URI uri = new URI(url.toString());
            File folder = new File(uri.getPath());
            File[] files = folder.listFiles();
            String Name;

            for (File fl : files) {
                Name = fl.getName();
                Name = Name.substring(0, Name.lastIndexOf('.'));
                names.add(Name);
            }
        }
        return names;
    }
}
