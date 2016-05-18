本应用意指了解logback的运行机制，以及logback一些特性
一些细节请在logback.xml里面去观察

1.导入的包以及在使用上面跟直接使用log4j没有任何差别，只有日志描述文件有差别
     log4j-->log4j.properties
     logback-->logback.xml
     
2.可定制log的输出，具体观察logback.xml
               现象:虽然类里面都有log的各个级别的输出，不过可以指定某些包才输出，在此之外的包虽然有log.xxx，不过不会输出
               
3.也可以针对级别进行日志的输出，比如DEBUG的话，这个会将4个级别都输出，如果设置ERROR，那么只有error级别的才会输出