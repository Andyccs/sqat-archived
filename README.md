# Software Quality Analysis Tool
Software Quality Analysis Tool (SQAT) for my Final Year Project

# How To Build This Project
After cloning this repository, open up build.gradle file. In this file, you need to make sure that the path to JDK_HOME
is correct. For example:

```
// This path is only valid for MAC OS, with jdk 1.7.0 updates 71
// So change it to your path
compile files("/Library/Java/JavaVirtualMachines/jdk1.7.0_71.jdk/Contents/Home/lib/tools.jar")    
```

Then, open the file ./src/main/java/com.sqatntu.SQAT.java at line 14, change the "path" variable to the folder that you want to 
analyse. For example:

```java
String path = "/Users/andyccs/Desktop/com.sqatntu.SQAT/src/main/java";
```

Once you done this, you can build and run this project by using the following commands

```
gradlew run
```

# Contribution
Contact Andyccs andy0017@e.ntu.edu.sg for more details

# License
I still don't know about this yet