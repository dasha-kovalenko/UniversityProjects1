SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `authors` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

REPLACE INTO `books` (`id`, `title`, `authors`) VALUES
(1, 'Курс алгебры', 'Винберг Э. В.'),
(2, 'Теория чисел', 'Нестеренко'),
(3, 'Обыкновенные ДУ', 'Мазаник'),
(4, 'Сборник задач', 'Демидович');

DROP TABLE IF EXISTS `books_topics`;
CREATE TABLE IF NOT EXISTS `books_topics` (
  `book_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  KEY `book_id` (`book_id`,`topic_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

REPLACE INTO `books_topics` (`book_id`, `topic_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 3),
(3, 2),
(3, 3),
(4, 3);

DROP TABLE IF EXISTS `topics`;
CREATE TABLE IF NOT EXISTS `topics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

REPLACE INTO `topics` (`id`, `topic`) VALUES
(1, 'ГА'),
(2, 'ДУ'),
(3, 'Математика');
