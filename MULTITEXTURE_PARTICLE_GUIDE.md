# 多纹理蓝花楹粒子系统指南

## 概述
系统现在支持使用12个不同的粒子纹理文件，每个粒子随机选择一个纹理，创建丰富的视觉效果。

## 文件结构

### 纹理文件
需要在以下位置放置12个粒子纹理文件：
```
src/main/resources/assets/dollancustomized/textures/particles/
├── jacaranda_0.png
├── jacaranda_1.png
├── jacaranda_2.png
├── jacaranda_3.png
├── jacaranda_4.png
├── jacaranda_5.png
├── jacaranda_6.png
├── jacaranda_7.png
├── jacaranda_8.png
├── jacaranda_9.png
├── jacaranda_10.png
└── jacaranda_11.png
```

### 核心实现
- **MultiTextureJacarandaParticle.java**: 支持多纹理的粒子类
- **ParticleTextureEventHandler.java**: 纹理注册事件处理器

## 纹理布局

系统将12个纹理排列成4列×3行的网格：

```
UV 空间映射 (4x3)
┌─────┬─────┬─────┬─────┐
│ 0,0 │ 1,0 │ 2,0 │ 3,0 │  Row 0
├─────┼─────┼─────┼─────┤
│ 0,1 │ 1,1 │ 2,1 │ 3,1 │  Row 1
├─────┼─────┼─────┼─────┤
│ 0,2 │ 1,2 │ 2,2 │ 3,2 │  Row 2
└─────┴─────┴─────┴─────┘
```

- 每个纹理占据 UV 空间的 0.25×0.333
- 通过 `textureIndex` 决定使用哪个纹理
- UV 计算公式：
  - `u = (textureIndex % 4) * 0.25`
  - `v = (textureIndex / 4) * 0.333`

## 测试命令

### 1. 多纹理粒子测试
```
/testparticle multitex
```
生成使用全部12个纹理的粒子效果。

### 2. 其他测试选项
```
/testparticle simple    - 简单粒子（不依赖纹理）
/testparticle debug     - 调试粒子（红色）
/testparticle working   - 工作粒子
/testparticle basic     - 基本粒子
```

## 实现细节

### 1. 纹理预加载
```java
static {
    // 初始化12个纹理位置
    for (int i = 0; i < 12; i++) {
        TEXTURE_LOCATIONS[i] = new ResourceLocation("dollancustomized", "textures/particles/jacaranda_" + i);
    }
    preloadTextures();
}
```

### 2. 纹理注册
```java
@SubscribeEvent
public void onTextureStitchEvent(TextureStitchEvent.Pre event) {
    // 注册所有12个蓝花楹粒子纹理
    for (int i = 0; i < 12; i++) {
        ResourceLocation texture = new ResourceLocation("dollancustomized", "textures/particles/jacaranda_" + i);
        event.getMap().registerSprite(texture);
    }
}
```

### 3. 随机纹理选择
```java
// 随机选择一个纹理
this.textureIndex = rand.nextInt(12);
```

## 性能优化

1. **静态纹理数组**: 避免重复创建 ResourceLocation 对象
2. **预加载**: 在静态代码块中预加载所有纹理
3. **错误处理**: 如果纹理加载失败，使用备用方案

## 故障排除

### 如果纹理没有显示

1. **检查纹理文件**: 确保12个 PNG 文件都存在
2. **检查路径**: 确保路径正确
3. **测试命令**: 使用 `/testparticle multitex` 测试
4. **查看日志**: 检查是否有纹理加载错误

### 如果出现崩溃

1. **简化测试**: 先使用 `/testparticle simple` 确保基本功能
2. **逐步调试**: 从简单到复杂逐步测试
3. **检查内存**: 确保足够的内存加载纹理

## 扩展说明

如果需要添加更多纹理：
1. 修改 `TEXTURE_LOCATIONS` 数组大小
2. 更新 UV 映射逻辑
3. 调整网格布局（如 4x4 或 6x2）

系统设计为可扩展的，可以轻松支持更多纹理变体。