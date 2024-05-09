# VietnameseLunar

[![](https://jitpack.io/v/baolanlequang/VietnameseLunar-android.svg)](https://jitpack.io/#baolanlequang/VietnameseLunar-android)
![GitHub release (release name instead of tag name)](https://img.shields.io/github/v/release/baolanlequang/VietnameseLunar-android?include_prereleases&label=version)

Library for convert a day to Vietnamese lunar day (Tiếng Việt ở bên dưới)

This library is developed base on Hồ Ngọc Đức's algorithm https://www.informatik.uni-leipzig.de/~duc/amlich/calrules_en.html.

If you're using this library, please help me give a thank to Hồ Ngọc Đức.

If you like my works, you can <a href="https://www.buymeacoffee.com/baolanlequang" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" style="height: 30px !important;width: 117px !important;" ></a>

## How to use
This library is released under MIT license, you are free to use or modify it.

### Install with gradle
1. Add the JitPack repository to your build file
Add it in your root `build.gradle` at the end of repositories:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
  
2. Add the dependency
```gradle
dependencies {
  implementation 'com.github.baolanlequang:VietnameseLunar-android:1.0'
}
```
  
### Install with maven
1. Add the JitPack repository to your build file

```maven
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

2. Add the dependency

```maven
<dependency>
    <groupId>com.github.baolanlequang</groupId>
    <artifactId>VietnameseLunar-android</artifactId>
    <version>1.0</version>
</dependency>
```

### Use it in your project
```kotlin
val now = Date()
val vietnameseCalendar = VietnameseCalendar(now)

//This will be display a string, eg: "Mùng 1 Tết Nhâm Dần"
Log.d("vietnameselunar", vietnameseCalendar.vietnameseDate.toString())
```

# VietnameseLunar (Tiếng Việt)
Đây là thư viện để tính toán ngày âm lịch theo lịch Việt Nam

Thư viện này được phát triển dựa trên thuật toán của Hồ Ngọc Đức https://www.informatik.uni-leipzig.de/~duc/amlich/calrules_en.html.

Nếu bạn đang sử dụng thư viện này, xin vui lòng giúp tôi gửi một lời cám ơn đến Hồ Ngọc Đức.

## Cách sử dụng
TThư viện này được phát hành dưới giấy phép MIT, do đó bạn có thể tự do sử dụng và chỉnh sửa tuỳ ý.

### Cài đặt thông qua gradle
1. Thêm JitPack vào project của bạn
Mở file `build.gradle` và thêm vào như sau

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
  
2. Thêm thư viện `VietnameseLunar`
```gradle
dependencies {
  implementation 'com.github.baolanlequang:VietnameseLunar-android:1.0'
}
```
  
### Cài đặt thông qua maven
1. Thêm JitPack vào project của bạn

```maven
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

2. Thêm thư viện `VietnameseLunar`

```maven
<dependency>
    <groupId>com.github.baolanlequang</groupId>
    <artifactId>VietnameseLunar-android</artifactId>
    <version>1.0</version>
</dependency>
```

### Sử dụng trong project của bạn
```kotlin
val now = Date()
val vietnameseCalendar = VietnameseCalendar(now)

//Dòng này sẽ in ra ngày tương ứng, ví dụ: "Mùng 1 Tết Nhâm Dần"
Log.d("vietnameselunar", vietnameseCalendar.vietnameseDate.toString())
```
