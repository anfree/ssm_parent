# ssm_parent
我搭建这个基于maven构建的最小化的ssm项目，是为了以后能复用它，作为我所有ssm框架的父项目。

issues：
Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
java没有保存形参的记录:queryAll(int offset, int limit) -> queryAll(arg1,arg2)
因此，要用 @Param 注解告诉MyBatis映射参数名称。
