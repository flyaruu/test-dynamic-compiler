== Small example using the JavaCompiler API from OSGi ==

This simple bundle allows you to dynamically compile java code in a dynamic OSGi environment.
It will find all classes / resources in all bundles loaded. It explicitly does *not* honor the what is exported/imported or not by bundles.

 - I create a custom JavaFileManager
 - Whenever the compiler requests a package I'll either get it from cache or I'll traverse all bundles, and their BundleWiring to expose all classes.
 - That part is not optimized, I think it can be done much faster

Some things to remember:

 - It's not particularly memory efficient, a lot of stuff gets cached in memory.
 - As it is now, it only compiles single files (although that would be easy to do)
 - It only works with Sun / OpenJDK compiler. The Eclipse java compiler doesn't work at all. I'll file a bug about this, because it doesn't actually implement JSR199

 I'll mention Atamur:
 
 http://atamur.blogspot.nl/2009/10/using-built-in-javacompiler-with-custom.html
 
 His example got me going.