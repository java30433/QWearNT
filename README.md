# QWearNT

### 如何构建

在`app/src/main`下创建文件夹`apkhook`，在里面放入`apktool.jar`的解包产物，之后运行名为`hookapk`的`gradle task` ，编译完成的包会输出到`app/hook`文件夹下。注意，输出的 APK 是**没有签名**且**不会自动去除签名校验**的，请自行在`apkhook`中放入去签名校验后安装包的解包产物或使用核心破解。

### 本项目以 GNU General Public License v3.0 (GPLv3) 协议开源，请注意

如有侵权，请联系本人QQ2159116373（注明来意）；交流群691530437

### QWearNT 更新日志:
	
2.0-beta04:

	- 现在回复可以看到昵称和时间了
	- 支持回复消息
	- 支持长按消息@别人
	- 设置添加聊天字体大小调节
	- 也许修复了气泡大小错位的问题
	
2.0-beta03:

	- 合并了爅峫的深色主题，感谢爅峫
	- 移除了日志记录功能，理论可减少约20%存储占用
	- 添加了设置项-使用单行输入，有助于解决某些输入法bug
	- 删除消息双击朗读
	- 删除加好友方式中的碰一碰
	- 现在可以正常显示引用了回复的消息中的图片
	
2.0-beta02:

	- 修复保存图片闪退
	- 修复复制含图片消息闪退
	- [开发中]展示消息引用的回复块
	
2.0-beta01:

	- 第三方输入法支持
	- 微调UI
	- 简化"我的"页面
	- 复制消息功能
	- 移除好友列表通话键
