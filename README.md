[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Password%20EditText-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3048)
 [ ![Download](https://api.bintray.com/packages/maksim88/PasswordEditText/PasswordEditText/images/download.svg) ](https://bintray.com/maksim88/PasswordEditText/PasswordEditText/_latestVersion)
 [![](https://jitpack.io/v/maksim88/PasswordEditText.svg)](https://jitpack.io/#maksim88/PasswordEditText)

Password EditText
============

A simple extension to the standard Android EditText which shows an icon on the right side of the field and lets the user toggle the visibility of the password he puts in.


NOTE
--------
Support-library versions `24.2.0` and upwards now have built-in functionality to show an eye icon and toggle password visibility (For more info see [docs](https://developer.android.com/reference/android/support/design/widget/TextInputLayout.html#attr_android.support.design:passwordToggleEnabled)).
You can nevertheless still use this lib, maybe because you want some of the features that the built-in approach does not have.


How does it look like?
--------

![alt tag](https://raw.github.com/maksim88/PasswordEditText/master/media/screenshot.png)

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


You can also use `TextInputLayout` to achieve an even prettier `setError()` dialog using `setErrorEnabled(true)` on the outer `TextInputLayout`
and then calling `setError()` on it. This underlines the text and shows an error message underneath the text.


Customize
--------

You can add your own custom icons which are shown on the right side of the `EditText`.

Do this by first adding the custom namespace to your root layout, e.g.:
```xml
<LinearLayout
    [...]
    xmlns:app="http://schemas.android.com/apk/res-auto"
    [...]>
</Linearlayout>
```

After that you can add the icons with the attributes `app:pet_iconShow` and `app:pet_iconHide`:

```xml
<com.maksim88.passwordedittext.PasswordEditText
    [...]
    app:pet_iconShow="@drawable/..."
    app:pet_iconHide="@drawable/..."
    [...]
/>
```

You can also set toggle the monospace Font inside the `PasswordEditTexts` with `app:pet_nonMonospaceFont`:

```xml
<com.maksim88.passwordedittext.PasswordEditText
    [...]
    app:pet_nonMonospaceFont="true"
    [...]
/>
```

Another customization is to just toggle the visibility of the password when the icon is hovered with `app:pet_hoverShowsPw`:

```xml
<com.maksim88.passwordedittext.PasswordEditText
    [...]
    app:pet_hoverShowsPw="true"
    [...]
/>
```

If you do not like the alpha, that is set to all the icons, you can disable it using `app:pet_disableIconAlpha`

NOTE: alpha values are taken from the material design guidelines (https://www.google.com/design/spec/components/text-fields.html#text-fields-password-input)

```xml
<com.maksim88.passwordedittext.PasswordEditText
    [...]
    app:pet_disableIconAlpha="true"
    [...]
/>
```


For a working example of the different customizations check out the `activity_main.xml` inside the `sample` project.

Download
--------

The library is available from `jcenter()`, so all you need to do is include it in your apps `build.gradle`:

```groovy
dependencies {
          compile 'com.maksim88:PasswordEditText:v0.9'
  }
```


Alternatively you can use `jitpack.io`:
More info here: https://jitpack.io/#maksim88/PasswordEditText


Questions?
--------
If you have any questions feel free to open a github issue with a 'question' label


License
--------
Licensed under the MIT license. See [LICENSE](LICENSE.md).
