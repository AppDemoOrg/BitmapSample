
## 图片的压缩
1、PNG图片压缩（瘦身）

## PNG图片压缩（瘦身）
1、移动平台多使用png，除非已全面支持webp    
2、否则就面临png图片瘦身需求    

### 无损压缩ImageOption
1、无损压缩，移除冗余的元数据以及非必要的颜色配置    
2、不牺牲质量，减少了空间占用，提高了加载速度    

### 有损压缩ImageAlpha
1、处理过的图片，需要设计师视觉检视    

### 有损压缩TingPNG
1、处理过的图片，需要设计师视觉检视    
2、需要在图片大小和质量之间找到平衡     

### PNG/JPEG转换为WebP
1、APP支持4.0及以上，直接使用系统的api   
2、4.0一下，集成第三方的webp-android-backport
3、无损压缩的webp比png少45%的文件大小

### 尽量使用NinePatch格式的PNG图
1、体积小、拉伸不变形      
2、AS已集成PNG转NinePatch功能
