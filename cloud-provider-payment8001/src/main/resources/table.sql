

CREATE TABLE payment (
                      id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                      `serial` VARCHAR(200) DEFAULT null,
                      PRIMARY KEY(id)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8