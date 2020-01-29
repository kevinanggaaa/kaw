# Presensi online dengan QR Code
Aplikasi untuk melakukan presensi ukm dengan QR Code dan android

## Requirement

1. Android Studio with newest Gradle version 
2. Retrofit library (https://square.github.io/retrofit/)
3. GSON decoder and encoder (https://square.github.io/retrofit/)
4. ZXing QR and Barcode scanner (https://github.com/zxing/zxing)
5. Java Development Kit (JDK, bundled with android studio)

## Installation
1) Android studio
Untuk android studio, bisa dilihat di https://www.niagahoster.co.id/blog/cara-install-android-studio-tutorial-lengkap/
2) GSON, Retrofit, and ZXing
Ganti project ke android lalu buka Gradle Scripts/build.gradle (Module:app) kemudian taruh ketiga gradle dependency berikut
```java
dependencies {  
  implementation 'me.dm7.barcodescanner:zxing:1.9.8'  
  implementation 'com.squareup.retrofit2:retrofit:2.6.2'  
  implementation 'com.squareup.retrofit2:converter-gson:2.6.0'  
  implementation 'com.squareup.okhttp3:logging-interceptor:3.8.0'  
}
```

## Usage
