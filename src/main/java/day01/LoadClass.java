package day01;


import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class LoadClass {
    private static final Method addURL = initAddMethod();
    private static final URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    private static Method initAddMethod() {
        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            add.setAccessible(true);
            return add;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadClasspath() {
        List<String> files = getJarFiles();
        for (String f : files) {
            System.out.println(f);
            loadClasspath(f);
        }
    }

    private static void loadClasspath(String filepath) {
        File file = new File(filepath);
        loopDirs(file);
    }

    private static void loopDirs(File file) {
        if (file.isDirectory()) {
            addURL(file);
            File[] tmps = file.listFiles();
            if(tmps!=null) {
                for (File tmp : tmps) {
                    loopDirs(tmp);
                }
            }
        }
    }

    private static void loopFiles(File file) {
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            if(tmps!=null){
                for (File tmp : tmps) {
                    loopFiles(tmp);
                }
            }
        } else {
            if (file.getAbsolutePath().endsWith("jar") || file.getAbsolutePath().endsWith(".zip")) {
                addURL(file);
            }
        }
    }

    private static void addURL(File file){
        try{
            addURL.invoke(classLoader,new Object[]{file.toURI().toURL()});
        }catch(Exception e) {
        }
    }

    private static List<String> getJarFiles(){
        File[] files = new File(".").listFiles();
        List<String> list = new ArrayList<>();
        if(files!=null){
            for(File file:files){
                list.add(file.getPath());
            }
        }
        return list;
    }

}
