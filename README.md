# Introduction: JavaFrame
This is a basic collection of classes to quickly get setup with a Java game project using AWT and software rendering. The software rendering uses a [Bit Blit](https://en.wikipedia.org/wiki/Bit_blit).

The development of this project takes place predominantly during the Ludum Dare game jam events. It therefore misses the benefits of code review, comprehensive unit testing or the application of many clean code principles. It is strongly recommended to **not use this code ina production environment**.

# Getting the Repository
You can get the JavaFrame project by either by cloning the repository or by adding it as a submodule to an existing git repository.
If you want to add the Java-LDLibraryGL project to your existing git project, type the following at the git Bash:

```
git submodule add https://github.com/LintfordPickle/Java-LDLibraryGL.git <directory>
```

This will register the git repository as a submodule under the directory specified. This command will automatically clone the submodule into the repository. You can check the status of the submodules using:

```
git status
```

and you can pull the latest version of the submodule with the following command:

```
git submodule update
```

note: a git clone of a repository will not by default pull any submodules. In this case you need to submodule init and then submodule update.

# Linking the Library
Once you have cloned the JavaFrame repository, you can import it into your IDE workspace. 
You can then add it to your game project's build path. This can be done either using the project properties dialog (e.g. in Eclipse), or by added the following to the .classpath:


```
<classpathentry combineaccessrules="false" kind="src" path="/JavaFrame"/>
```
n.b. the path is the relative path to the library.

Once you have done this, you can now import JavaFrame packages and access the classes within.

# Using JavaFrame (Opening a window)

After you have linked the library to your game application's classpath, you need to create a subclass of the JavaBase class. This contains the code for opening an AWT window and preparing the buffered images and bitmaps for rendering to the window. For the minimal setup, use:

```Java
package com.ruse.javaframe.samples;

import com.ruse.javabase.JavaBase;

public class SampleWindow extends JavaBase {

	private static final long serialVersionUID = 1L;

	public SampleWindow(String pWindowTitle) {
		super(pWindowTitle);
	}

	public static void main(String[] args) {
		SampleWindow lSampleWindow = new SampleWindow("Sample Window Creation");
		lSampleWindow.openWindow();
	}

}

```

#Using JavaFrame (Gameloop)

JavaBase is an abstract class which allows you to quickly create a window. It also provides methods which can be overriden, allowing you to create you own interactive application. The methods which you can override are:

**loadResources()** This is called once at the start of your game. From here you can load any resources you need for your application (e.g. images, sounds etc.)

**handleInput()** This is called once per frame, before the update and render. It allows you to poll the keyboard and mouse for user input.

**update()** This is called once per frame, after handleInput but before render(). It is where you can update your application logic.

**render()** This is called once per frame. From here you can render you application to the backbuffer. At the end of the render() method, the backbuffer is presented to the window (and the previous window buffer is used as the new backbuffer).




