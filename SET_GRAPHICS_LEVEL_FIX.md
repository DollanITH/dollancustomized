# setGraphicsLevel 方法渲染总线修复总结

## 修复内容

### 1. 问题
- `setGraphicsLevel` 方法需要集成到渲染总线上
- 方法需要根据渲染距离自动调整叶子渲染模式

### 2. 实现方案

#### 2.1 创建了全局叶子渲染控制器
- 文件位置：`src/main/java/com/jhxiaochen/dollancustomized/proxy/render/GlobalLeavesRenderer.java`
- 功能：监听 `RenderWorldLastEvent` 渲染事件
- 根据渲染距离自动切换渲染模式

#### 2.2 修改了 JacarandaLeaves 类
- 添加了 `setGraphicsLevelStatic(boolean fancy)` 静态方法
- 允许从渲染总线调用设置

#### 2.3 在 ClientProxy 中注册了渲染处理器
- 在 `preInit()` 方法中注册了 `GlobalLeavesRenderer`
- 确保在游戏启动时就开始监听渲染事件

### 3. 工作原理

1. 游戏运行时，`GlobalLeavesRenderer` 监听 `RenderWorldLastEvent` 事件
2. 根据渲染距离判断模式：
   - 渲染距离 ≥ 8：使用 Fancy 模式（高质量）
   - 渲染距离 < 8：使用 Fast 模式（低质量）
3. 调用 `JacarandaLeaves.setGraphicsLevelStatic()` 设置渲染级别
4. `setGraphicsLevelStatic()` 内部创建临时实例调用 `setGraphicsLevel`

### 4. 修改的文件

#### 新增文件：
- `GlobalLeavesRenderer.java` - 渲染控制器
- `SET_GRAPHICS_LEVEL_FIX.md` - 说明文档

#### 修改文件：
- `JacarandaLeaves.java` - 添加了静态方法
- `ClientProxy.java` - 注册了渲染处理器

### 5. 注意事项

- 确保 proxy/render 目录存在
- 渲染总线的优先级可以根据需要调整
- 可以根据其他条件（如性能设置、维度等）来调整渲染模式

### 6. 测试建议

1. 启动游戏
2. 移动观察渲染距离的变化
3. 切换远近查看叶子渲染模式的差异
4. 检查粒子效果是否随渲染模式变化

## 恢复的文件

我已经通过 git checkout 恢复了你被误删的 client 目录及其所有内容。