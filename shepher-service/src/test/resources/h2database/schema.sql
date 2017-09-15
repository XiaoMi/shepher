CREATE TABLE `cluster` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `config` varchar(255) NOT NULL DEFAULT '',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY  `idx_cluster_name` (`name`)
);

CREATE TABLE `need_review` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cluster` varchar(255) NOT NULL DEFAULT '',
  `reviewer_list` varchar(1024) NOT NULL DEFAULT '',
  `path_prefix` varchar(255) NOT NULL DEFAULT '',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ;
CREATE INDEX `idx_path_prefix_cluster` ON `need_review`  (`path_prefix`,`cluster`);

CREATE TABLE `permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cluster` varchar(255) NOT NULL DEFAULT '',
  `path` varchar(255) NOT NULL DEFAULT '',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_permission_path_cluster` (`path`,`cluster`)
);

CREATE TABLE `permission_team` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `permission_id` int(11) unsigned NOT NULL DEFAULT '0',
  `team_id` int(11) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ;
CREATE INDEX `idx_permission_team` ON `permission_team` (`permission_id`,`team_id`);

CREATE TABLE `review_request` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cluster` varchar(255) NOT NULL DEFAULT '',
  `path` varchar(255) NOT NULL DEFAULT '',
  `snapshot` int(11) unsigned NOT NULL DEFAULT '0',
  `new_snapshot` int(11) unsigned NOT NULL DEFAULT '0',
  `review_status` tinyint(3) NOT NULL DEFAULT '0',
  `creator` varchar(255) NOT NULL DEFAULT '',
  `reviewer` varchar(255) NOT NULL DEFAULT '',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `action` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ;
CREATE INDEX `idx_review_request_path_cluster` ON  `review_request`(`path`,`cluster`);

CREATE TABLE `snapshot` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cluster` varchar(255) NOT NULL DEFAULT '',
  `path` varchar(255) NOT NULL DEFAULT '',
  `content` mediumtext NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `action` tinyint(3) NOT NULL DEFAULT '0',
  `zk_mtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(3) NOT NULL DEFAULT '0',
  `zk_version` int(11) DEFAULT '-1',
  `creator` varchar(255) NOT NULL DEFAULT '',
  `reviewer` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ;
CREATE INDEX `idx_path_cluster_zkmtime_zkversion` ON  `snapshot`(`path`,`cluster`,`zk_mtime`,`zk_version`);

CREATE TABLE `team` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `owner` int(11) unsigned NOT NULL DEFAULT '0',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_team_name` (`name`)
) ;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY  `idx_user_name` (`name`)
) ;


CREATE TABLE `user_team` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL DEFAULT '0',
  `team_id` int(11) unsigned NOT NULL DEFAULT '0',
  `role` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ;
CREATE INDEX `idx_team_user` ON `user_team` (`team_id`,`user_id`);