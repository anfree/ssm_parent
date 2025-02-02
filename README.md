# ssm_parent
This is a minimized SSM project built on Maven.

issues：
Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
java没有保存形参的记录:queryAll(int offset, int limit) -> queryAll(arg1,arg2)
因此，要用 @Param 注解告诉MyBatis映射参数名称。
