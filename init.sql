SHOW DATABASES;

# 创建数据库
CREATE DATABASE IF NOT EXISTS plms CHARACTER SET 'utf8';

# 选中数据库
USE plms;

# 创建车位信息表
CREATE TABLE IF NOT EXISTS stall_message
(
    id         int PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    totality   int    NOT NULL DEFAULT 0 COMMENT '总车位数量',
    occupy     int    NOT NULL DEFAULT 0 COMMENT '占用中的车位数量',
    salary     double NOT NULL DEFAULT 0 COMMENT '停车收费价格 元/每小时',
    vip_salary double NOT NULL DEFAULT 0 COMMENT '会员的收费价格 元/每天'
);

# 创建在车库中的车辆信息
CREATE TABLE IF NOT EXISTS in_garage
(
    id           int PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID，删除时候用',
    plate_number varchar(20) NOT NULL UNIQUE COMMENT '车牌号',
    start_time   datetime    NOT NULL COMMENT '进入车库的时间'
);

# 创建所有车辆进出记录
CREATE TABLE IF NOT EXISTS all_record
(
    id           int PRIMARY KEY COMMENT '主键ID，依赖于in_garage中id',
    plate_number varchar(20) NOT NULL COMMENT '车牌号',
    start_time   datetime    NOT NULL COMMENT '进入车库的时间',
    end_time     datetime    NOT NULL COMMENT '驶出车库的时间',
    charge       double      NOT NULL COMMENT '收取的费用'
);

# 会员信息
CREATE TABLE IF NOT EXISTS vip
(
    id           int PRIMARY KEY AUTO_INCREMENT COMMENT '会员ID',
    username     varchar(20) NOT NULL COMMENT '会员名称',
    phone_num    char(11)    NOT NULL COMMENT '会员手机号',
    plate_number varchar(20) NOT NULL UNIQUE COMMENT '会员车牌号',
    start_time   date        NOT NULL COMMENT '会员开始时间',
    end_time     date        NOT NULL COMMENT '会员到期时间',
    charge       double      NOT NULL COMMENT '收取的费用'
);

# 管理员
CREATE TABLE IF NOT EXISTS admin
(
    id            int PRIMARY KEY AUTO_INCREMENT COMMENT '管理员id',
    username      varchar(30)  NOT NULL UNIQUE COMMENT '用户名',
    password      varchar(200) NOT NULL COMMENT '密码',
    email         varchar(20)  NOT NULL UNIQUE COMMENT '邮箱',
    head_portrait varchar(200) NOT NULL UNIQUE DEFAULT 'https://plms-images.oss-cn-beijing.aliyuncs.com/avatar.gif' COMMENT '头像链接'
);

# 添加默认管理员账号
INSERT INTO admin(username, password, email)
VALUES ('admin', 'admin', 'admin@qq.com');

# 停车场信息初始化
INSERT INTO stall_message(totality, occupy, salary, vip_salary)
VALUES (50, 0, 3, 10);