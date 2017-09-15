INSERT INTO `cluster` VALUES (3,'local_test','localhost:2181','2016-08-03 01:49:21');
INSERT INTO `permission` VALUES (3,'local_test','/test','2016-08-03 01:56:44'),(4,'local_test','/test/sub1','2016-08-03 01:56:45'),(5,'local_test','/test/sub1/ss1','2016-08-03 01:56:46'),(6,'local_test','/test/sub2','2016-08-03 01:56:49');
INSERT INTO `permission_team` VALUES (2,4,5,10,'2016-08-03 01:56:45'),(3,6,5,0,'2016-08-03 01:56:49');
INSERT INTO `team` VALUES (1,'admin',1,'2016-08-03 01:45:00'),(5,'theone',1,'2016-08-03 01:56:45');
INSERT INTO `user` VALUES (1,'banchuanyu','2016-08-03 01:43:50'),(2,'weichuyang','2016-08-03 01:43:50'),(3,'testuser','2016-08-03 01:43:50');
INSERT INTO `user_team` VALUES (1,1,1,100,10,'2016-08-03 01:47:44'),(2,2,1,100,10,'2016-08-03 01:47:44'),(3,3,1,100,10,'2016-08-03 01:48:14'),(12,1,5,100,10,'2016-08-03 01:56:49'),(13,3,5,100,10,'2016-08-03 02:00:43');
INSERT INTO `snapshot` VALUES (1,'local_test','/test/test2','','2016-11-02 09:06:06',30,'2016-09-20 02:07:59',10,0,'AUTO_CREATED','AUTO_REVIEWED'),(2,'local_test','/test/test2','111','2016-11-02 09:06:06',30,'2016-11-02 09:06:10',10,1,'banchuanyu','banchuanyu');
INSERT INTO `review_request` VALUES (1,'local_test','/test/test2',1,2,10,'banchuanyu','banchuanyu','2016-11-02 09:06:06','2016-11-02 09:06:10',30);