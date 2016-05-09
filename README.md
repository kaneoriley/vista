[![Release](https://jitpack.io/v/com.github.oriley-me/vista.svg)](https://jitpack.io/#com.github.oriley-me/vista) [![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Build Status](https://travis-ci.org/oriley-me/vista.svg?branch=master)](https://travis-ci.org/oriley-me/vista) [![Dependency Status](https://www.versioneye.com/user/projects/56b73e38f6e506003a88f1cd/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56b73e38f6e506003a88f1cd)

# Vista
![Logo](artwork/icon.png)

Vista is a simple library that allows using Material design style edge effects on all Android devices running 4.0+. It
is ported from the Android Launcher source code, which means you also have the added benefit of the edges not appearing
to be cut off, which is much nicer in my honest opinion.


## Usage


Vista supports 6 view types. You need to replace the view in your layout XML with the corresponding Vista version in
order to see the new edge effects. The following are the names of the supported types, and their corresponding Vista
counterparts:

`android.widget.GridView`: Replace with `me.oriley.vista.VistaGridView`  
`android.widget.ListView`: Replace with `me.oriley.vista.VistaListView`  
`android.widget.HorizontalScrollView`: Replace with `me.oriley.vista.VistaHorizontalScrollView`  
`android.widget.ScrollView`: Replace with `me.oriley.vista.VistaScrollView`  
`android.support.v4.view.ViewPager`: Replace with `me.oriley.vista.VistaViewPager`  
`android.support.v4.widget.NestedScrollView`: Replace with `me.oriley.vista.VistaNestedScrollView`  
`android.support.v7.widget.RecyclerView`: Replace with `me.oriley.vista.VistaRecyclerView`  

So, your XML will look something like this:

```xml
    ...

    <me.oriley.vista.VistaViewPager
        android:id="@+id/vista_view_pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    ...
```

To customise the edge effect colour, add the `vistaEdgeEffectColor` attribute like so:

```xml
    ...

    <me.oriley.vista.VistaViewPager
        android:id="@+id/vista_view_pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:vistaEdgeEffectColor="@color/my_edge_effect_color"/>

    ...
```

And that's it! You now have Material Design edge effect glows available to many more users, and don't have the ugly
cut-off arc when your views aren't filling the available space.


## Gradle Dependency


 * Add JitPack.io repo to your buildscript:

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

 * Add the library to your module dependencies:

```gradle
dependencies {
    compile 'com.github.oriley-me:vista:0.3.1'
}
```

If you would like to check out the latest development version, please substitute all versions for `develop-SNAPSHOT`.
Keep in mind that it is very likely things could break or be unfinished, so stick the official releases if you want
things to be more predictable.

Please checkout the sample application to familiarise yourself with the implementation details. Don't be afraid to
make an issue or contact me if you have any problems or feature suggestions.
