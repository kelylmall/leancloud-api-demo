# leancloud-api-demo
mac 

gcc -dynamiclib -I /Library/Java/JavaVirtualMachines/jdk1.8.0_65.jdk/Contents/Home/include 
HelloWorldImpl.c -o libhello.jnilib

linux

gcc -fPIC -shared -D_REENTRANT -I $JAVA_HOME/include -I $JAVA_HOME/include/linux HelloWorldImpl.c -o libHello.so
