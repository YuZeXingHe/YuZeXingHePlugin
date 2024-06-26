# YuZeXingHePlugin
### 插件简介
专门为了Minecraft轻语生存服务器而写的一个插件，集UI面板、随身工作台、快捷管理功能为一体的插件（代码逻辑目前来说相对垃圾和混乱，目前该插件还在不断测试、优化、完善中）。插件源代码完全放出，完全免费提供下载使用，如果有好的优化建议，也可以帮忙优化一下（实际上作者懒得优化OUO）。注意事项：本插件经过测试，目前支持并流畅运行于1.20+的游戏版本，该插件安装需要服务器支持bukkit插件（非常推荐使用Paper端）！
### 插件所需前置插件
    Expansion-LuckPerms：https://ci.lucko.me/job/LuckPermsPlaceholders/
    PlaceholderAPI：https://www.spigotmc.org/resources/placeholderapi.6245/
    LuckPerms：https://luckperms.net/
前置插件安装方式：您需要先将PlaceholderAPI，LuckPerm放入服务器中的 plugins文件夹并启动服务器，然后就需要等待您的服务器启动完成，服务器启动完成后再关闭您的服务器。在您的服务器关闭后，您应该可以在PlaceholderAPI插件文件夹中的expansions内加入第三个前置插件：Expansion-LuckPerms。最后启动您的服务器即可。
### 重要：数据库导入和更新1.9.1版本后需要做的事
    MySQL官网：https://www.mysql.com/
在更新1.9.1版本后，您将需要MySQL的支持。请您自行前往百度搜索MySQL如何下载和安装，以便您能支持最新的等级系统。在您更新1.9.1版本插件后，您需要删除原来plugins文件夹中关于本插件所有的配置文件，以便插件能够重新生成新的配置文件。
### 插件导入方法
下载完成后，直接扔进服务器中的Plugin文件夹即可。
### 插件基础指令
    /open ui：打开本插件的UI面板（内含一键清空背包，快捷返回出生点回家功能，随身工作台和一些管理员功能）。
    /open help：服务器重要指令的一些帮助（仅一些本插件相关指令和Minecraft轻语生存服务器一些其他重要的指令）。
    /configs start：加载配置文件（须在服务器中以管理员身份使用该指令）。
    /configs change：修改配置文件（须在服务器中以管理员身份使用该指令，修改完指令后重启服务器即可生效）。
    /level list：在聊天栏中显示所有玩家的活跃等级信息，等级信息会自动排序（相当于一个活跃等级排行榜）。
    /levelconfig help：获取等级相关配置文件修改帮助。
### 配置文件相关
注意事项：修改配置文件必须满足管理员身份和玩家身份，无法在服务器控制台更改配置文件！将本插件导入到您的服务器后，您可以在服务器中的plugin/YuZeXingHePlugin文件夹看到本插件的配置文件。配置文件中大部分功能相关内容提供了注释来帮助使用者了解配置功能。如果您在使用过程中发现无法修改配置文件，修改配置文件不生效，装入插件失败的问题，请联系作者反馈您的问题。
### 轻语生存服务器相关
既然本插件专为Minecrft轻语生存服务器开发，那就顺带帮服主宣传一下服务器，Minecraft轻语生存服务器是由某位摆烂大佬自己闲的没事干然后突发奇想搞的一个生电服务器，服务器目前处于停服维护阶段。轻语生存服务器的版本为Java版1.20.1+版本（Java版和Bedrock版互通）。
### 直接下载本插件
    可以直接从仓库中的Plugins文件夹中下载，亦或者在Releases下载。
### 使用git下载本插件
    git clone https://github.com/YuZeXingHe/YuZeXingHePlugin.git
当您使用git下载完成后，您可以在Plugin文件夹中找到插件的JAR文件，在YuZeXingHePlugin文件夹中您可以找到本插件的所有源码。
### 关于作者
    插件问题反馈方式：xingguangyize@gmail.com
    服务器相关问题反馈：LuoLuoNotFind@outlook.com
插件作者：YuZeXingHe，一个极其摆烂的摸鱼怪，该插件由作者本人独自完成，创作灵感来源：摆烂到发霉后的突发奇想，然后从床上光速爬起来就有了这项灵感。
