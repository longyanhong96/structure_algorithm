DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` varchar(255) NOT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        `sex` varchar(25) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `user` VALUES ('101', 'aaa', 'f');
INSERT INTO `user` VALUES ('102', 'bbb', 'm');

update `user` set name='ccc' where id='101';