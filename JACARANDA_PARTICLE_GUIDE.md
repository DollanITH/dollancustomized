# 蓝花楹粒子系统指南

## 概述
本项目使用自定义粒子系统来创建蓝花楹花瓣飘落效果。

## 文件结构

### 1. JSON 定义文件
```
assets/dollancustomized/particles/jacaranda.json
```
定义粒子纹理和尺寸。

### 2. 粒子类
```
src/main/java/com/jhxiaochen/dollancustomized/particle/JacarandaParticle.java
```
粒子实现类，处理粒子行为和渲染。

### 3. 粒子管理器
```
src/main/java/com/jhxiaochen/dollancustomized/particle/ParticleManager.java
```
提供粒子生成接口。

## 纹理要求

### 当前实现 - 使用12个单独文件
项目使用12个单独的粒子纹理文件：
- `jacaranda_0.png` 到 `jacaranda_11.png`
- 每个文件都是独立的粒子帧
- 通过 TextureStitchEvent 自动注册到材质图集

### 实现特点
- 不需要精灵表，直接使用单独纹理
- 每个粒子随机选择一个纹理文件
- 通过 `ParticleTextureManager` 管理纹理加载

## 代码说明

### 粒子渲染
粒子渲染代码已经适配了4列×3行的精灵表布局：
```java
// UV坐标计算
int texX = textureIndex % 4;  // 4列
int texY = textureIndex / 4;  // 3行
f = (float)texX / 4.0F;
f1 = f + 0.25F;  // 每帧宽度1/4
f2 = (float)texY / 3.0F;
f3 = f2 + 0.33333334F;  // 每帧高度1/3
```

### 粒子生成
粒子通过 `ParticleManager` 类生成，支持不同强度：
- 基础粒子：30分之1概率
- 微风效果：20分之1概率  
- 强风效果：15分之1概率
- 自然消散：100分之1概率

## 使用方法

### 1. 生成粒子
```java
ParticleManager.spawnJacarandaParticles(world, x, y, z, rand);
```

### 2. 生成破坏粒子
```java
ParticleManager.spawnBreakParticles(world, x, y, z);
```

## 注意事项

1. 使用单独的纹理文件实现，每个粒子占用一个独立的纹理位置
2. 通过 TextureStitchEvent 自动注册纹理，确保正确加载
3. 粒子颜色设置为蓝花楹的紫色色调
4. 粒子会受重力影响向下飘落
5. 实现了12个不同的粒子纹理变体，增加视觉效果多样性