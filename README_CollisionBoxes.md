# 路标杆碰撞箱修复总结

## 已修复的方块类

### 1. 主路标 (RoadSignpost)
- **边界框类**: `RoadSignpostBoundingBox.java`
- **特点**: 包含主杆、多个标志牌和细节部件
- **碰撞箱数量**: 9个（主杆+各标志牌组件）
- **特殊功能**: 多层标志牌设计，每个方向都有精确碰撞检测

### 2. 单杆路标 (RoadSignpostPole)
- **边界框类**: `RoadSignpostPoleBoundingBox.java`
- **特点**: 简单的单杆设计
- **碰撞箱数量**: 1个
- **特殊功能**: 基础电线杆碰撞检测

### 3. 双杆路标 (RoadSignpostPoleDouble)
- **边界框类**: `RoadSignpostPoleDoubleBoundingBox.java`
- **特点**: 左右双杆设计
- **碰撞箱数量**: 2个（左杆+右杆）
- **特殊功能**: 双杆独立碰撞检测

### 4. 水平杆路标 (RoadSignpostPoleHorizontal)
- **边界框类**: `RoadSignpostPoleHorizontalBoundingBox.java`
- **特点**: 水平排列的多根杆子
- **碰撞箱数量**: 3个（不同高度的水平杆）
- **特殊功能**: 多层水平结构碰撞检测

### 5. 左向杆路标 (RoadSignpostPoleLeft)
- **边界框类**: `RoadSignpostPoleLeftBoundingBox.java`
- **特点**: L型结构，垂直+水平杆
- **碰撞箱数量**: 2个（垂直杆+水平杆）
- **特殊功能**: 复杂形状的精确碰撞

### 6. 右向杆路标 (RoadSignpostPoleRight)
- **边界框类**: `RoadSignpostPoleRightBoundingBox.java`
- **特点**: L型结构，但水平杆位置不同
- **碰撞箱数量**: 2个（垂直杆+水平杆）
- **特殊功能**: 右向特殊设计的碰撞检测

### 7. 电杆上路标 (RoadSignpostOnThePole)
- **边界框类**: `RoadSignpostOnThePoleBoundingBox.java`
- **特点**: 安装在电线杆上的路标
- **碰撞箱数量**: 1个（根据方向变化）
- **特殊功能**: 方向依赖的碰撞形状

### 8. 半路标 (RoadSignpostHalf)
- **边界框类**: `RoadSignpostHalfBoundingBox.java`
- **特点**: 半截路标设计
- **碰撞箱数量**: 1个
- **特殊功能**: 简化的碰撞检测

## 技术实现

### 旋转算法
所有边界框都基于西向（WEST）基础设计，通过旋转算法计算其他方向：
- **西→北**: Y轴旋转90度
- **西→南**: Y轴旋转270度
- **西→东**: Y轴旋转180度

### 碰撞检测机制
1. **精确碰撞**: 用于物理交互（玩家行走、物品掉落等）
2. **选择碰撞**: 用于玩家点击和方块选择（仅在玩家交互时激活）

### 坐标转换
所有模型坐标都除以16.0转换为方块坐标系统，确保精确对齐。

## 使用方法

1. 每个方块类都使用对应的边界框类
2. 通过 `getAllCollisionBoxes()` 获取所有碰撞箱
3. 通过 `getDisplayBoundingBox()` 获取选择边界框
4. 玩家交互时会自动添加选择碰撞箱

## 注意事项

- 所有碰撞箱都考虑了方块的朝向
- 选择碰撞箱通常比实际碰撞箱更大，便于玩家交互
- 复杂形状的方块使用多个碰撞箱组合