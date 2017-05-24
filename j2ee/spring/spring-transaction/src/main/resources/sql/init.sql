CREATE TABLE `tx` (
  `id` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `version` int(11) NOT NUll default 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='test';

CREATE TABLE `sfu` (
  `id` int(11) NOT NULL,
  `amount` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='test select for update';