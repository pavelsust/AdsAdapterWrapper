# AdsAdapterWrapper [![](https://jitpack.io/v/pavelsust/AdsAdapterWrapper.svg)](https://jitpack.io/#pavelsust/AdsAdapterWrapper)


<h4> One of the main earning sources for applications is monetization. The main two monetization network is Admob & Facebook. Sometimes we want to show Native Ads in recycleview so that we can show more Ads and can earn more. But showing Native Ads into recycleview is so hard and need to write bipolar codes. So in this library, I try to make these things easy for developers. Developers can easily integrate Admob and Facebook Native ads as well as Banner Ads into recycleview. 
The main feature of this library is
</h4>

<ul>
  <li>Facebook Native Ads</li>
  <li>Facebook Banner Ads</li>
  <li>Facebook Native Banner Ads</li>
  <li>Admob Native Ads</li>
  <li>Admob Banner Ads</li>
</ul>  



<table>
  <tr>
    <td>Home Page</td>
     <td>Admob Banner</td>
     <td>Facebook Banner</td>
     <td>Facebook Native</td>
     <td>Facebook Native Banner</td>
     <td>Admob Native</td>
  </tr>
  <tr>
    <td><img src="https://github.com/pavelsust/AdsAdapterWrapper/blob/master/screenshot/Screenshot_20220402_010923.png?raw=true" width=270 height=280></td>
    <td><img src="https://github.com/pavelsust/AdsAdapterWrapper/blob/master/screenshot/Screenshot_20220402_011001.png?raw=true" width=270 height=280></td>
    <td><img src="https://github.com/pavelsust/AdsAdapterWrapper/blob/master/screenshot/Screenshot_20220402_011019.png?raw=true" width=270 height=280></td>
      <td><img src="https://github.com/pavelsust/AdsAdapterWrapper/blob/master/screenshot/Screenshot_20220402_011034.png?raw=true" width=270 height=280></td>
      <td><img src="https://github.com/pavelsust/AdsAdapterWrapper/blob/master/screenshot/Screenshot_20220402_011657.png?raw=true" width=270 height=280></td>
    

   <td><img src="https://github.com/pavelsust/AdsAdapterWrapper/blob/master/screenshot/Screenshot_20220403_005726.png?raw=true" width=270 height=280></td>
  
  </tr>
 </table>


## Install

Add this dependency in your `build.gradle`: 


```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
		}
	}
```


```xml
dependencies {
    implementation 'com.github.pavelsust:AdsAdapterWrapper:1.0.4'
    implementation 'com.facebook.android:audience-network-sdk:6.+'  // facebook ads
    implementation 'com.google.android.gms:play-services-ads:20.6.0' // Google Ads
}
```



```java

public class AppController extends Application {

    App app;
    @Override
    public void onCreate() {
        super.onCreate();
	 AudienceNetworkAds.initialize(this)
         MobileAds.initialize(this)
    }
}
```

 -> Add Internet permissions and Application class in your Androidmanifest 

```xml

<uses-permission android:name="android.permission.INTERNET" />
 <application>
        android:name=".AppController"
         ......................
	 ......................
    </application>
 ```
 
 
  ### For Admob Banner Adapter 
 
 ```java
val admobBanner = AdmobBannerAdAdapter.Builder.with(requireActivity() , "AD UNIT ID", your adapter)
            .adItemInterval(5).build()
        binding.recycleview.adapter = admobBanner
```
 
 
  ### For Admob Native Adapter 
 
 // Here we have two type view 
  * medium
  * small
 
 ```java
    val admobNative = AdmobNativeAdAdapter.Builder.with("AD UNIT ID", your adapter, "medium")
            .adItemInterval(5).build()
        binding.recycleview.adapter = admobNative
```
 
 
 
 ### For Facebook Banner
 
 
 ```java
 
        val facebookBanner = FacebookBannerAdAdapter.Builder.with(requireActivity() , "FACEBOOK BANNER AD ID" , Your adapter)
            .adItemInterval(5).build()
        binding.recycleview.adapter = facebookBanner
```
 
 
 ### For Facebook Native
 
 
 ```java
 
      val facebookNative = FacebookNativeAdAdapter.Builder.with( "FACEBOOK NATIVE AD ID" , Your Adapter)
            .adItemInterval(5).build()
        binding.recycleview.adapter = facebookNative
```
 
  ### For Facebook Native Banner
 
 
 ```java
 
      val facebookNativeBanner = FacebookNativeBannerAdsAdapter.Builder.with( "FACEBOOK NATIVE BANNER AD ID" , Your Adapter)
            .adItemInterval(5).build()
        binding.recycleview.adapter = facebookNativeBanner
```
 


License
=======

Licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
