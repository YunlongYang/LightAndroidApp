package online.heyworld.android.light.library.reflect;

import android.content.Context;

import com.google.common.base.CharMatcher;
import com.google.common.reflect.Reflection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

public class ClassUtil{

    public static final List<ClassInfo> getClasses(Context context,String packageName,boolean topOnly){
        List<ClassInfo> classInfoList = new ArrayList<>();
        try {
            DexFile dex = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = dex.entries();
            while (entries.hasMoreElements()) {
                String className = entries.nextElement();
                if(className.startsWith(packageName)){
                    ClassInfo classInfo = new ClassInfo(className);
                    if(topOnly){
                        if(classInfo.getPackageName().equals(packageName) && (!className.contains("$"))){
                            classInfoList.add(new ClassInfo(className));
                        }
                    }else{
                        classInfoList.add(new ClassInfo(className));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classInfoList;
    }

    public static class ClassInfo {
        private final String className;

        public ClassInfo(String className) {
            this.className = className;
        }

        /**
         * Returns the package name of the class, without attempting to load the class.
         *
         * <p>Behaves identically to {@link Package#getName()} but does not require the class (or
         * package) to be loaded.
         */
        public String getPackageName() {
            return Reflection.getPackageName(className);
        }

        /**
         * Returns the simple name of the underlying class as given in the source code.
         *
         * <p>Behaves identically to {@link Class#getSimpleName()} but does not require the class to be
         * loaded.
         */
        public String getSimpleName() {
            int lastDollarSign = className.lastIndexOf('$');
            if (lastDollarSign != -1) {
                String innerClassName = className.substring(lastDollarSign + 1);
                // local and anonymous classes are prefixed with number (1,2,3...), anonymous classes are
                // entirely numeric whereas local classes have the user supplied name as a suffix
                return CharMatcher.digit().trimLeadingFrom(innerClassName);
            }
            String packageName = getPackageName();
            if (packageName.isEmpty()) {
                return className;
            }

            // Since this is a top level class, its simple name is always the part after package name.
            return className.substring(packageName.length() + 1);
        }

        /**
         * Returns the fully qualified name of the class.
         *
         * <p>Behaves identically to {@link Class#getName()} but does not require the class to be
         * loaded.
         */
        public String getName() {
            return className;
        }
    }
}