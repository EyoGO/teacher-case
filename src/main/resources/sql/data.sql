INSERT INTO users (first_name, last_name, email, password, role, birthday, gender)
VALUES ('Yurii', 'Hentash', 'gentashyura@gmail.com', 'Welcome1', 'ADMIN', '2000-06-26', 'MALE'),
 ('Bob', 'Singer', 'bsinger@gmail.com', 'Welcome1', 'USER', '1067-04-22', 'MALE');

INSERT INTO unit (unit_name, parent_id, managed_by_admin)
VALUES ('Безперервний професійний розвиток', -1, false),
       ('Професійна діяльність', -1, false),
       ('Впровадження та використання сучасних цифрових технологій', -1, false),
       ('Партнерська взаємодія з учасниками освітнього процесу', -1, false),
       ('Участь в організації  безпечного та здорового освітнього середовища закладу', -1, false),
       ('Участь в управлінських процесах', -1, false),
       ('Штрафні санкції', -1, true),

       ('Активність в шкільних МО, творчих групах інших формах.', 1, false),
       ('Активність в  МО, творчих групах, інших формах в ТГ.', 1, false),
       ('Активність в  МО, творчих групах, інших формах ( за межами ТГ).', 1, false),
       ('Онлайн вебінари, семінари (підтверджені сертифікатами)', 1, false),
       ('Власні авторські доробки (друковані та на блогах, сайтах: посібники, публікації , статті, уроки, сценарії  та ін.)', 1, false),
       ('Участь в проєктах, педагогічних конкурсах.', 1, false),
       ('Реалізація інновацій в роботі ', 1, false),
       ('Наставництво, супервізія інших вчителів, надання рекомендацій вчителю', 1, false),
       ('Власний блог вчителя (портфоліо, сайт)', 1, false),
       ('Курси підвищення кваліфікації', 1, false),

       ('Власні авторські методики (з коротким описом)', 2, false),
       ('Участь в апробації нових технологій, підручників.', 2, false),
       ('Використання сучасних методик та технологій', 2, false),
       ('Робота з обдарованими дітьми (олімпіади, конкурси, проекти та ін.) на рівні ТГ', 2, false),
       ('Робота з обдарованими дітьми (олімпіади, конкурси, проекти та ін.) на рівні району', 2, false),
       ('Робота з обдарованими дітьми (олімпіади, конкурси, проекти та ін.) на рівні області', 2, false),
       ('Робота з обдарованими дітьми (олімпіади, конкурси, проекти та ін.) на всеукраїнському рівні', 2, false),


       ('Використання в роботі відкритих електронних ресурсів педагогічного спрямування', 3, false),
       ('Організація електронного (цифрового) освітнього простору закладу освіти', 3, false),

       ('Проведення майстер-класів за інтересами', 4, false),
       ('Конструктивна робота з батьками в тому числі в проекті "Канікули з батьками"', 4, false),
       ('Організація спільних екскурсій, відвідування культурних закладів', 4, false),
       ('Профорієнтаційна робота серед учнів та батьків', 4, false),
       ('Проведення заходів, свят для учнів (батьків) ', 4, false),
       ('Популяризація діяльності закладу в пресі, медіа, сайтах, соцмережах та ін.', 4, false),
       ('Робота з громадскістю', 4, false),

       ('Робота на волонтерських засадах ', 5, false),
       ('Організація роботи кабінету, проектування осередків навчання, виховання  та розвитку', 5, false),
       ('Профілактично-просвітницька робота', 5, false),

       ('Подання ініціатив, пропозицій до удосконалення  освітнього процесу в  закладі', 6, false),
       ('Робота із забезпечення  функціонування системи внутрішнього самооцінювання освітнього процесу закладу', 6, false),
       ('Впровадження в роботі власних критеріїв оцінювання, моніторингів знань', 6, false),
       ('Діяльність в робочих групах, комісіях закладу', 6, false),
       ('Виступи на педагогічній раді', 6, false),

       ('Порушення внурішнього розпорядку закладу', 7, true),
       ('Скарги', 7, true),
       ('Ведення документації', 7, true),
       ('Не виконання рішень педагогічної ради, наказів', 7, true),
       ('Порушення академічної доброчесності', 7, true),
       ('Порушення професійної етики', 7, true);

INSERT INTO activity (user_id, unit_id, activity_name, description, author_id)
VALUES (1, 8, 'Перша активність першого користувача', 'Опис першої активності.', 1),
       (1, 9, 'Друга активність першого користувача', 'Опис другої активності.', 1),
       (1, 8, 'Третя активність першого користувача', 'Опис третьої активності.', 1),
       (2, 8, 'Перша активність другого користувача', 'Опис першої активності.', 2),
       (2, 8, 'Друга активність другого користувача', 'Опис другої активності.', 2);
