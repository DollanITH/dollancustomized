# Particle Fixes V2 - 材质和方向修复

## 问题修复

### 1. 材质显示问题
- **问题**: 粒子材质不显示或显示为白色方块
- **原因**: 纹理注册方式不正确，粒子无法正确加载材质
- **修复**: 
  - 创建了 `SimplePetalParticle` 作为测试粒子
  - 使用简单的UV映射 (0.0-1.0) 来确保纹理加载
  - 暂时使用基础方块纹理而不是自定义粒子纹理

### 2. 粒子方向问题
- **问题**: 粒子应该向下飘落而不是向上或水平飘散
- **参考**: 
  - suikecherry的树叶粒子实现
  - Minecraft 1.12.2标准粒子行为
- **修复**:
  - 设置 `motionY = -0.05` 确保向下运动
  - 添加重力 `particleGravity = 0.005F`
  - 减少水平速度让粒子主要向下飘落

## 新增文件

### 1. SimplePetalParticle.java
- 简单的花瓣粒子实现
- 使用基础方块纹理进行测试
- 确保粒子正确向下飘落

### 2. JacarandaPetalParticle.java
- 完整的蓝花楹花瓣粒子
- 包含生命周期管理
- 重力影响和淡出效果

### 3. PetalTestCommand.java
- 新的测试命令 `/testpetal`
- 生成20个测试粒子
- 验证粒子运动和渲染

## 修改的文件

### 1. ParticleManager.java
- 更新粒子生成参数:
  ```java
  // 基础粒子
  double motionY = -0.03 - RAND.nextDouble() * 0.025; // 向下飘落
  
  // 风吹粒子
  double motionY = -0.035 - RAND.nextDouble() * 0.015; // 风吹粒子也向下
  ```

### 2. DollanCustomized.java
- 注册新的测试命令:
  ```java
  event.registerServerCommand(new PetalTestCommand());
  ```

## 测试方法

1. **进入游戏**
2. **打开聊天框**
3. **输入命令**: `/testpetal`
4. **观察**: 应该看到紫色的方块粒子从你头顶向下飘落

## 验证要点

1. **粒子材质**: 粒子应该显示为紫色（不是白色）
2. **运动方向**: 粒子应该向下飘落
3. **生命周期**: 粒子应该在落地前消失

## 下一步

1. 验证简单粒子正常工作
2. 切换到完整的JacarandaPetalParticle
3. 实现自定义纹理（如果简单粒子工作正常）

## 注意事项

- 当前使用的是简单方形粒子进行测试
- 确保材质文件存在于正确位置:
  ```
  src/main/resources/assets/dollancustomized/textures/particles/jacaranda_0.png
  ```
- 如果简单粒子不工作，可能是材质路径问题