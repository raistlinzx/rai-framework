# 功能说明:

1. 根据数据库表结构，自动生成对应Java对象
2. 根据数据库表结构，自动生成对应Hibernate HBM文件
3. 支持外键映射
4. 可增加支持其他数据库
5. 运行com.rai.framework.util.Main main()

## Ver1.1

1. 支持Oracle数据库
2. 支持OneToMany映射List
3. 更新Oracle数据类型映射

## Ver1.0

1. 实现MySQL数据库映射
2. 配置文件config.properties
3. 类型对应文件datatype.properties
4. 数据库表转换接口 com.rai.framework.util.interfaces.DatabaseUtil
5. Java对象生成接口 com.rai.framework.util.interfaces.ClassGenerator
6. HBM转换接口 com.rai.framework.util.interfaces.HBMGenerator
