remotedeploy
============

eclipse 远程文件实时同步，eclipse远程部署插件

eclipse 远程文件实时同步，eclipse远程部署插件
github地址：https://github.com/zhwj184/remotedeploy
 
在开发过程中经常修改一些文件及时部署到测试环境中，比如apache静态资源文件，velocity模板文件等，那么我们可以通过这个eclipse
 
插件设置好服务器信息，及时将本地修改同步到测试环境中，及时生效。
 
将当前目录下的jar包放到eclipse的plugin目录下，eclipse要求3.4以上，jdk1.7.
 
原理：利用jdk1.7的文件修改监听API将配置目录下的文件（一般为windows）如果修改添加，则实时使用ssh jar包的api同步到远程测试服务器上（一般linux）
 
