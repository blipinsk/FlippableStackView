FlippableStackView
===============
An Android library that allows to create a stack of views with the flippable first item of the stack.

![ ](/FlippableStackView.gif)


Usage
=====
*For a working implementation of this library see the `sample/` folder.*

  1. Include the view inside your layout xml

        <com.bartoszlipinski.flippablestackview.FlippableStackView
            android:id="@+id/stack"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

  2. `FlippableStackView` is based on the specific `PageTransformer` used with the `VerticalViewPager`. Therefore to fill the `View` you can use just a typical implementation of a [`PagerAdapter`][3]. In your `onCreate` method (or `onCreateView` for a fragment), setup all the parameters of the `FlippableStackView`.

        FlippableStackView stack = (FlippableStackView) findViewById(R.id.stack);
        stack.initStack(2);
        stack.setAdapter(mStackAdapter);
        	//assuming mStackAdapter contains your initialized adapter

**Important Note:**
The current implementation of the library will display the elements from the `Adapter` in the reverse order. In other words: view at position 0 of your adapter will be displayed at the bottom of the stack and view at position `adapter.getCount()-1` will be visible first (available for the first flip).

Customization
-------------
The `FlippableStackView` is highly customizable to provide you with just the visual effect you really wanted.

There are two methods that allows for initialization of the stack:

  1. The one that sets the stuck in the default way (scale-wise): 
 
        public void initStack(int numberOfStacked)
 
  2. And the other one... a bit more advanced (lets you customize all the scale-related and alignment-related parameters):
 
        public void initStack(int numberOfStacked, float currentPageScale, float topStackedScale, float overlapFactor, StackPageTransformer.Gravity gravity) 

 Be sure to read about all the parameters in `JavaDoc` before using the latter one.

Including In Your Project
-------------------------
Currently not available via Maven Central. *yet...*

Fork the repository or add a submodule to your repo and include the 'library' module to your project.

Developed by
==========
 * Bartosz Lipiński

Credits
-------
This library is based on the vertical version of Android `ViewPager`. 
The implementation used in the library has been developed by [Antoine Merle][1], so all the credits for the [`VerticalViewPager`][2] go to him.


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


 [1]: https://github.com/castorflex
 [2]: https://github.com/castorflex/VerticalViewPager
 [3]: http://developer.android.com/reference/android/support/v4/view/PagerAdapter.html
