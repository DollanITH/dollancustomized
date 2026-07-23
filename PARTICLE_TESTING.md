# 粒子系统测试指南

## 测试命令

### 1. 基础粒子测试
```
/testparticle
```
生成带有自定义蓝花楹纹理的粒子效果。

### 2. 简单粒子测试
```
/testparticle simple
```
生成不依赖自定义纹理的简单粒子效果（纯色）。

### 3. 调试粒子测试
```
/testparticle debug
```
生成红色调试粒子，用于测试粒子系统是否正常工作。

## 故障排除

### 如果粒子没有显示

1. **首先尝试调试粒子**
   ```
   /testparticle debug
   ```
   如果显示了红色粒子，说明粒子系统工作正常，但纹理加载有问题。

2. **检查日志输出**
   调试粒子会在控制台输出加载信息，查看是否有错误。

3. **检查资源包**
   确保 `src/main/resources/assets/dollancustomized/textures/particles/` 目录下有：
   - `jacaranda_0.png` 到 `jacaranda_11.png`

### 纹理加载问题

如果看到类似 "particleTexture is null" 的错误：

1. 确保所有12个纹理文件都存在
2. 检查文件路径是否正确
3. 尝试重新构建项目

### 性能测试

使用以下命令生成大量粒子进行性能测试：
```
/testparticle simple
```
重复执行多次，观察帧率变化。

## 实现细节

### 粒子类型

1. **JacarandaParticle**
   - 使用12个不同的纹理文件
   - UV映射为4×3网格
   - 紫色调颜色

2. **SimpleJacarandaParticle**
   - 不使用自定义纹理
   - 使用基础渲染
   - 紫色调颜色

3. **DebugJacarandaParticle**
   - 红色粒子，便于调试
   - 输出加载信息到控制台

### 纹理管理

- 使用 `ParticleTextureManager` 管理纹理
- 通过 `TextureStitchEvent` 注册纹理
- 在 `ClientProxy.init()` 中预加载纹理

## 下一步

1. 如果调试粒子工作正常，但正常粒子不显示，检查纹理文件
2. 如果都不工作，检查 Forge 粒子系统配置
3. 考虑实现精灵表以提高性能