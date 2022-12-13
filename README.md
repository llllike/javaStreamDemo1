## 如果内容对你有帮助的话，点一个免费的star吧，非常感谢!

# javaStream操作

流操作能够使得代码更简洁

# Sream操作分为三种

## 创建流、流中间处理、终止流

![a37dcc841481414289fc98990626851b~tplv-k3u1fbpfcp-zoom-in-crop-mark.jpg](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/a37dcc841481414289fc98990626851b~tplv-k3u1fbpfcp-zoom-in-crop-mark.jpg)

## 开始流

| API              | **功能说明**                                     |
| ---------------- | ------------------------------------------------ |
| stream()         | 创建出一个新的stream串行流对象                   |
| parallelStream() | 创建出一个可并行执行的stream流对象               |
| Stream.of()      | 通过给定的一系列元素创建一个新的Stream串行流对象 |

## 中间处理方法

| API        | 功能说明                                                     |
| ---------- | ------------------------------------------------------------ |
| filter()   | 按照条件过滤符合要求的元素， 返回新的stream流                |
| map()      | 将已有元素转换为另一个对象类型，一对一逻辑，返回新的stream流 |
| flatMap()  | 将已有元素转换为另一个对象类型，一对多逻辑，即原来一个元素对象可能会转换为1个或者多个新类型的元素，返回新的stream流 |
| limit()    | 仅保留集合前面指定个数的元素，返回新的stream流               |
| skip()     | 跳过集合前面指定个数的元素，返回新的stream流                 |
| concat()   | 将两个流的数据合并起来为1个新的流，返回新的stream流          |
| distinct() | 对Stream中所有元素进行去重，返回新的stream流                 |
| sorted()   | 对stream中所有的元素按照指定规则进行排序，返回新的stream流   |
| peek()     | 对stream流中的每个元素进行逐个遍历处理，返回处理后的stream流 |

## 终止方法

| API         | 功能说明                                                     |
| ----------- | ------------------------------------------------------------ |
| count()     | 返回stream处理后最终的元素个数                               |
| max()       | 返回stream处理后的元素最大值                                 |
| min()       | 返回stream处理后的元素最小值                                 |
| findFirst() | 找到第一个符合条件的元素时则终止流处理                       |
| findAny()   | 找到任何一个符合条件的元素时则退出流处理，这个**对于串行流时与findFirst相同，对于并行流时比较高效**，任何分片中找到都会终止后续计算逻辑 |
| anyMatch()  | 返回一个boolean值，类似于isContains(),用于判断是否有符合条件的元素 |
| allMatch()  | 返回一个boolean值，用于判断是否所有元素都符合条件            |
| noneMatch() | 返回一个boolean值， 用于判断是否所有元素都不符合条件         |
| collect()   | 将流转换为指定的类型，通过Collectors进行指定                 |
| toArray()   | 将流转换为数组                                               |

# map与flatmap

`map`与`flatMap`都是用于转换已有的元素为其它元素，区别点在于：

- map **必须是一对一的**，即每个元素都只能转换为1个新的元素
- flatMap **可以是一对多的**，即每个元素都可以转换为1个或者多个新的元素

![4f533b7bbc814b6d9b3beb22b0bed9a5.awebp.jpg](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/4f533b7bbc814b6d9b3beb22b0bed9a5.awebp.jpg)

```java

/**
 * 演示map的用途：一对一转换
 */
public void stringToIntMap() {
    List<String> ids = Arrays.asList("205", "105", "308", "469", "627", "193", "111");
    // 使用流操作
    List<User> results = ids.stream()
            .map(id -> {
                User user = new User();
                user.setId(id);
                return user;
            })
            .collect(Collectors.toList());
    System.out.println(results);
}
```

再比如：**现有一个句子列表，需要将句子中每个单词都提取出来得到一个所有单词列表**。这种情况用map就搞不定了，需要`flatMap`上场了：

```java

public void stringToIntFlatmap() {
    List<String> sentences = Arrays.asList("hello world","Jia Gou Wu Dao");
    // 使用流操作
    List<String> results = sentences.stream()
            .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
            .collect(Collectors.toList());
    System.out.println(results);
}
```

![4247e57b025f4f9888a0a8b949e7eca9123.jpg](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/4247e57b025f4f9888a0a8b949e7eca9123.jpg)

# `peek`和`foreach`

都可以用于对元素进行遍历然后逐个的进行处理。

但是peek是中间处理方法，需要有终止方式，才会执行遍历。

而对于foreach是终止方法

```java

public void testPeekAndforeach() {
    List<String> sentences = Arrays.asList("hello world","Jia Gou Wu Dao");
    // 演示点1： 仅peek操作，最终不会执行
    System.out.println("----before peek----");
    sentences.stream().peek(sentence -> System.out.println(sentence));
    System.out.println("----after peek----");
    // 演示点2： 仅foreach操作，最终会执行
    System.out.println("----before foreach----");
    sentences.stream().forEach(sentence -> System.out.println(sentence));
    System.out.println("----after foreach----");
    // 演示点3： peek操作后面增加终止操作，peek会执行
    System.out.println("----before peek and count----");
    sentences.stream().peek(sentence -> System.out.println(sentence)).count();
    System.out.println("----after peek and count----");
}
```

# 需要注意的是：

这里需要补充提醒下，**一旦一个Stream被执行了终止操作之后，后续便不可以再读这个流执行其他的操作**了，否则会报错，看下面示例：

```java

public void testHandleStreamAfterClosed() {
    List<String> ids = Arrays.asList("205", "10", "308", "49", "627", "193", "111", "193");
    Stream<String> stream = ids.stream().filter(s -> s.length() > 2);
    // 统计stream操作后剩余的元素个数
    System.out.println(stream.count());
    System.out.println("-----下面会报错-----");
    // 判断是否有元素值等于205
    try {
        System.out.println(stream.anyMatch("205"::equals));
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println("-----上面会报错-----");
}
```

```java

6
-----下面会报错-----
java.lang.IllegalStateException: stream has already been operated upon or closed
	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:229)
	at java.util.stream.ReferencePipeline.anyMatch(ReferencePipeline.java:449)
	at com.veezean.skills.stream.StreamService.testHandleStreamAfterClosed(StreamService.java:153)
	at com.veezean.skills.stream.StreamService.main(StreamService.java:176)
-----上面会报错-----
```

因为stream已经被执行`count()`终止方法了，所以对stream再执行`anyMatch`方法的时候，就会报错`stream has already been operated upon or closed`，这一点在使用的时候需要特别注意。

收集流成集合：

```java

public void testCollectStopOptions() {
    List<Dept> ids = Arrays.asList(new Dept(17), new Dept(22), new Dept(23));
    // collect成list
    List<Dept> collectList = ids.stream().filter(dept -> dept.getId() > 20)
            .collect(Collectors.toList());
    System.out.println("collectList:" + collectList);
    // collect成Set
    Set<Dept> collectSet = ids.stream().filter(dept -> dept.getId() > 20)
            .collect(Collectors.toSet());
    System.out.println("collectSet:" + collectSet);
    // collect成HashMap，key为id，value为Dept对象
    Map<Integer, Dept> collectMap = ids.stream().filter(dept -> dept.getId() > 20)
            .collect(Collectors.toMap(Dept::getId, dept -> dept));
    System.out.println("collectMap:" + collectMap);
}
```

```java

collectList:[Dept{id=22}, Dept{id=23}]
collectSet:[Dept{id=23}, Dept{id=22}]
collectMap:{22=Dept{id=22}, 23=Dept{id=23}}
```

# 并行流处理

![97321b73e3a946859e677aea15885acca.jpg](https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/img/97321b73e3a946859e677aea15885acca.jpg)

`ParallelStream`底层使用了`Fork/Join`框架实现

使用并行流一定要切记，这里面极容易抛异常。

当业务逻辑过于复杂，建议不使用并行流。

# 下载示例代码

阿里云OSS：https://hhuhahaha.oss-cn-hangzhou.aliyuncs.com/code/javaStreamDemo1.zip

Github：https://github.com/llllike/javaStreamDemo1

Gitee：https://gitee.com/jk_2_yu/javaStreamDemo1
