# 限高牌数字显示问题修复（版本 2）

## 问题
自定义限高牌的数字和文字完全不可见。

## 已修复的问题

### 1. 颜色问题（严重）
- **问题**: 文字颜色使用黑色 `0xFF000000`，在白色背景上不可见
- **修复**: 改为白色 `0xFFFFFFFF`

### 2. 渲染状态设置不完整
- **问题**: 缺少深度测试、混合模式、多边形偏移等关键设置
- **修复**: 参考了 RenderRoadText 的完整实现，添加：
  - `glPolygonOffset(-1.0F, -10.0F)` 解决 Z-fighting
  - `glDepthMask(false)` 禁用深度写入
  - 正确的混合模式设置
  - 禁用雾气影响

### 3. 深度偏移缺失
- **问题**: 缺少基于位置的微小深度偏移，导致与其他方块重叠
- **修复**: 添加 `getMicroDepthOffset()` 方法

### 4. 纹理绑定缺失
- **问题**: 在 renderText() 之前没有绑定纹理
- **修复**: 在渲染前调用 `signFontRenderer.bindTexture()`

### 5. 缩放比例不合适
- **问题**: 缩放比例 1.0F 可能导致文字太小
- **修复**: 调整位置和保留 1.0F 缩放（会根据实际情况微调）

### 6. 调试信息不足
- **问题**: 无法诊断问题所在
- **修复**: 添加详细的调试输出

## 修改的文件

### 1. RenderHeightLimitSignCustom.java
- 添加 `enableTexture2D()` 启用纹理
- 添加 `getMicroDepthOffset()` 方法
- 调整渲染流程，添加纹理绑定
- 添加详细的调试输出
- 改进错误恢复代码

### 2. SignFontLoader.java
- 添加详细的初始化和加载日志
- 记录每个字符的纹理坐标

### 3. SignFontRenderer.java
- 改进混合模式设置
- 添加 `glDepthMask(false)` 禁用深度写入
- 添加 `drawSmoothString()` 调试日志
- 添加 `bindTexture()` 方法

### 4. TileEntityHeightLimitSignCustom.java
- 添加构造函数日志
- 改进默认值设置

### 5. GuiHeightLimitSignCustom.java
- 标签添加黄色和粗体效果

## 调试输出说明

启动游戏后，在日志中搜索以下关键词：
- `[SignFontLoader]` - 字体加载信息
- `[SignFontRenderer]` - 字体渲染信息
- `[RenderHeightLimit]` - 渲染信息

## 测试步骤

1. **完全重启游戏**（重要：清除缓存）
   ```bash
   # 删除 Minecraft 缓存
   rm -rf ~/.minecraft/cache
   ```

2. **生成限高牌**
   - 获得方块
   - 放置在地面

3. **查看日志输出**
   - 应该看到字体加载成功
   - 应该看到渲染调用

4. **检查方块**
   - 方块上应该显示数字（如 4.8）
   - 数值可以通过右键打开 GUI 设置

## 可能的后续调整

如果文字仍然不可见，可以调整：

1. **缩放比例**（RenderHeightLimitSignCustom.java:67）
   ```java
   GlStateManager.scale(2.0F, 2.0F, 2.0F); // 改大
   ```

2. **垂直位置**（RenderHeightLimitSignCustom.java:64）
   ```java
   GlStateManager.translate(0.0F, 0.0F, 0.0F); // 调整
   ```

3. **渲染颜色**
   ```java
   fontRenderer.drawSmoothString(..., 0xFFFFFFFF); // 改为其他颜色
   ```

## 关键代码片段

```java
// 深度偏移（解决重叠）
private float getMicroDepthOffset(BlockPos pos) {
    int hash = Math.abs((pos.getX() * 31 + pos.getY()) * 31 + pos.getZ());
    float offset = (hash % 10) / 100000.0f + 0.0001f;
    return Math.max(0.0001f, Math.min(offset, 0.0002f));
}

// 混合模式（参考 RenderRoadText）
GlStateManager.enableBlend();
GlStateManager.tryBlendFuncSeparate(
    GlStateManager.SourceFactor.SRC_ALPHA,
    GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
    GlStateManager.SourceFactor.ONE,
    GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
);

// 多边形偏移（解决 Z-fighting）
GlStateManager.enablePolygonOffset();
GlStateManager.doPolygonOffset(-1.0F, -10.0F);

// 深度测试
GlStateManager.enableDepth();
GlStateManager.depthMask(false);
```
