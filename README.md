[![](https://jitpack.io/v/maksim88/PasswordEditText.svg)](https://jitpack.io/#maksim88/PasswordEditText)


Password EditText
============

A simple extension to the standard Android EditText which shows an icon on the right side of the field and lets the user toggle the visibility of the password he puts in.


How does it look like?
--------

TODO: provide screenshots

Usage
--------
For a complete sample you can check out the sample project provided within.

In short:
Just include the `PasswordEditText` instead of the standard EditText and you are good to go.

```xml
<com.maksim88.passwordedittext.PasswordEditText
            android:id="@+id/input_password"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPassword"
            android:hint="Password" />
```

You can also wrap `PasswordEditText` inside a `TextInputLayout` to get a material design moving label on top:

```xml
<android.support.design.widget.TextInputLayout
        android:id="@+id/input_layout_password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <com.maksim88.passwordedittext.PasswordEditText
            android:id="@+id/input_password"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPassword"
            android:hint="Password" />
    </android.support.design.widget.TextInputLayout>

```
Note: be sure to include the design library to use `TextInputLayout`. (for more details see `sample`)


Download
--------
Just include `jitpack.io` inside of your root project `build.gradle`:

```groovy
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

After that you can easily include the library in your app `build.gradle`:

```groovy
dependencies {
	        compile 'com.github.maksim88:PasswordEditText:v0.1'
	}
```


Questions?
--------
If you have any questions feel free to open a github issue with a 'question' label


License
--------
The MIT License (MIT)

Copyright (c) 2016 Maksim

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.