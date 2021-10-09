<center>
<h1>DouyinSpider🕷️</h1>
<h3>一个简易的抖音视频爬虫</h3>
<br>
</center>
<center>
<img src="https://img.shields.io/badge/licence-MIT-brightgreen?style=for-the-badge">
<img src="https://img.shields.io/badge/JDK-8+-blue?style=for-the-badge">
<img src="https://img.shields.io/badge/version-0.3.0-red?style=for-the-badge">
</center>



# ⌨️安装

### 在项目的pom.xml中添加如下坐标

```xml
<dependency>
  <groupId>io.github.xiaofeng2233</groupId>
  <artifactId>DouyinSpider</artifactId>
  <version>0.3.0</version>
</dependency>
```





# 🔥使用方法

### 1.直接下载编译后的jar包运行

##### 点击<a href="1">这里</a>下载最新的编译jar包

##### 在控制台中运行

```shell
java -jar DouyinSpider.jar
```

##### 出现下图所示,按照指引使用即可

![1.png](https://i.loli.net/2021/10/09/XFTzvGBp8e6EVPj.png)





### 2.添加maven坐标并在项目中使用

##### 多视频方式

```java
//实例化一个爬虫对象 传入用户链接以及需要爬取的视频个数
        MultiVideoSpider multiVideoSpider  = new MultiVideoSpider("抖音用户主页URL 例如:https://v.douyin.com/dqkGuTS/",100);
        try {
            //返回爬取到的视频对象集合
            List<Video> videoList = multiVideoSpider.getVideoList();
        } catch (IOException e) {
            e.printStackTrace();
        }
```

##### 单视频方式

```java
 //实例化一个爬虫对象  传入需要爬取的视频链接
        VideoSpider videoSpider = new VideoSpider("抖音的视频URL 例如:https://v.douyin.com/d4AHcm1/");
        try {
            //返回爬取到的视频对象
            Video video = videoSpider.getVideo();
        } catch (IOException e) {
            e.printStackTrace();
        }
```

