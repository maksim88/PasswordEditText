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
Licensed under the MIT license. See [LICENSE](LICENSE.md).
