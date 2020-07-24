# MCPixelDrawing
一个由图片得到可生成像素画的mcfunction文件的小玩意

### 前言

* 这东西并不是直接修改存档，而是生成mcfunction文件（大概是mc的shell），然后进入游戏运行这文件中的指令才会生成像素画。

* 源码在src文件夹中（需要gson）
* 编译好的jar包在MCPixelDrawing-jar文件夹里

### 测试环境

Minecraft 1.16.1

jdk1.8.0_231

### 效果

原图（pixiv id : 63067913）

![63067913_p0.jpg](https://i.loli.net/2020/07/19/uoE8PD93mGsN7Sz.jpg)

像素画

![result.jpg](https://i.loli.net/2020/07/19/QrV7TMDqJuiOaLU.jpg)

### 用法/Usage

1. 下载MCPixelDrawing-jar.7z并解压到MCPixelDrawing-jar文件夹

   （正常的话会包含gson-2.8.6.jar，MCPixelDrawing.jar，mc_block_list.json）

2. MCPixelDrawing-jar文件夹中运行命令行指令

   `java -jar 待转换图片路径 图片转换比例 生成方向(x或z) 输出文件路径`

   比如`java -jar MCPixelDrawing.jar ./1.jpg 0.1 x ./test.mcfunction`就是把MCPixelDrawing-jar文件夹中名为1.jpg的文件按宽高比例缩小到原来的10%后，以自己为原点，x和y轴所在平面生成像素画，并保存到文件夹下名为test.mcfunction的文件

3. 把生成的mcfunction文件复制到`.minecraft\saves\worldname\datapacks\namespace\data\custom\functions\example`目录下（worldname是一个存档的名字，如果没有某一级文件夹就新建）

4. `.minecraft\saves\worldname\datapacks\namespace\`目录下新建一个名为`pack.txt`的文本，然后内容如下：

   ```txt
   {
     "pack":{
       "pack_format": 1,
       "description": "a description for the datapack"
     }
   }
   ```

   修改完保存后**重新命名**`pack.txt`为`pack.mcmeta`（！第四步很重要，不然会无法识别mcfunction文件！）

5. 进入游戏，运行指令`/function custom:example/test`，卡一下就自动生成了

### 注意事项

1. 关于图片的转换比利，建议自己简单算一下，不要超过高度限制
2. mc_block_list.json这个文件存储的是各个方块的名称和颜色值，所以如果发现方块缺失可以看下是不是版本没有加入其中的某些方块或者名次错误，删除即可。另外里面的颜色都是我随便根据贴图选的，不准也是正常的，逃~！
3. 生成的像素画都是竖立的，生成方向只能是x或者z，生成位置是以自己为原点，往x或者z轴的负方向生成的
4. `/function`这条指令低版本没有加入
5. 输出的文件后缀**一定要是mcfunction**