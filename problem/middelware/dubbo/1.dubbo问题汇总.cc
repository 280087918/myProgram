1.若出现Failed to save registry store file, cause: Can not lock the registry cache file这种关键词，证明dubbo本地读取的缓存文件被锁定了
  解决办法:
  修改dubbo的启动文件，指定自己的缓存文件的位置
  wrapper.java.additional.6=-Ddubbo.registry.file=/root/.dubbo/dubbo-registry-es.cache