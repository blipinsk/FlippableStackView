FlippableStackView
===============

[![License](https://img.shields.io/github/license/blipinsk/FlippableStackView.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FlippableStackView-green.svg?style=flat)](http://android-arsenal.com/details/1/1854)
[![Maven Central](https://img.shields.io/maven-central/v/com.bartoszlipinski.flippablestackview/library.svg)](http://gradleplease.appspot.com/#flippablestackview)

An Android library introducing a stack of Views with the first item being flippable.

`Views` inside the stack remain the aspect ratio of the `FlippableStackView`.

![ ](/FlippableStackView.png)


Library in action
=================

![ ](/FlippableStackView.gif)

Usage
=====
*For a working implementation of this library see the `sample/` folder.*

  1. Include the view inside your layout xml
  
      ```xml
      <com.bartoszlipinski.flippablestackview.FlippableStackView
        android:id="@+id/stack"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
      ```

  2. `FlippableStackView` is based on the specific `PageTransformer` used with the `ViewPager`. Therefore to fill the `View` you can use just a typical implementation of a [`PagerAdapter`][1]. In your `onCreate` method (or `onCreateView` for a fragment), setup all the parameters of the `FlippableStackView`.

      ```java
      FlippableStackView stack = (FlippableStackView) findViewById(R.id.stack);
      stack.initStack(2);
      stack.setAdapter(mStackAdapter); //assuming mStackAdapter contains your initialized adapter
      ```
      
**Important Note:**
The current implementation of the library will display the elements from the `Adapter` in the reverse order. In other words: view at position 0 of your adapter will be displayed at the bottom of the stack and view at position `adapter.getCount()-1` will be visible first (available for the first flip).

Customization
-------------
The `FlippableStackView` is highly customizable to provide you with just the visual effect you really wanted.

There are three methods that allows for initialization of the stack:

  1. First one sets up the stack in the default way (scale-wise and orientation-wise):
 
       ```java
       public void initStack(int numberOfStacked)
       ```

  2. The second one sets up the stack in the default way (scale-wise) but let's you choose the orientation of it:

      ```java
      public void initStack(int numberOfStacked, StackPageTransformer.Orientation orientation)
      ```

  2. And the last one... a bit more advanced (lets you customize all the scale-related, orientation-related and alignment-related parameters):
  
        ```java
        public void initStack(int numberOfStacked,
                              StackPageTransformer.Orientation orientation,
                              float currentPageScale,
                              float topStackedScale,
                              float overlapFactor,
                              StackPageTransformer.Gravity gravity)
        ```
 
 Be sure to read about all the parameters in `Javadoc` before using the last one.

Including In Your Project
-------------------------
You can grab the library via Maven Central. Just add a proper dependency inside your `build.gradle`. Like this:

```xml
dependencies {
    compile 'com.bartoszlipinski.flippablestackview:library:1.2.1'
}
```

Developed by
==========
 * Bartosz Lipiński

Credits
-------
Maven Central deployment was performed using an awesome Gradle script by [Chris Banes][2]. [This][3] made things so much easier.

License
======

    Copyright 2015 Bartosz Lipiński
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: http://developer.android.com/reference/android/support/v4/view/PagerAdapter.html
 [2]: https://chris.banes.me/2013/08/27/pushing-aars-to-maven-central/
 [3]: https://github.com/chrisbanes/gradle-mvn-push
