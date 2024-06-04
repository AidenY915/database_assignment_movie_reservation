USE db1;

INSERT INTO user VALUES("user1", "사용자1", "01000000000", "user1@naver.com", "user1", 0);
INSERT INTO user VALUES("root", "관리자", "01011111111", "root@naver.com", "1234", 1);

INSERT INTO movie(movie_name, running_time, age_rating, director_name, genre, release_date, movie_info, rating_information) VALUES('퓨리오사: 매드맥스 사가', 148, 15, '조지 밀러', '액션', '2024-05-22', '문명 붕괴 45년 후,
황폐해진 세상 속 누구에게도 알려지지 않은
풍요가 가득한 ‘녹색의 땅’에서 자란 ‘퓨리오사’(안야 테일러-조이)는
바이커 군단의 폭군 ‘디멘투스’(크리스 헴스워스)의 손에 모든 것을 잃고 만다.
가족도 행복도 모두 빼앗기고 세상에 홀로 내던져진 ‘퓨리오사’는
반드시 고향으로 돌아가겠다는 어머니와의 약속을 지키기 위해
인생 전부를 건 복수를 시작하는데...
‘매드맥스’ 시리즈의 전설적인 사령관 ‘퓨리오사’의 대서사시
5월 22일, 마침내 분노가 깨어난다!', 4.5);

INSERT INTO actor(actor_name) VALUES("크리스 햄스워드");
INSERT INTO actor(actor_name) VALUES("안야 테일러 조이");

INSERT INTO casting VALUES(1,1);
INSERT INTO casting VALUES(2,1);

INSERT INTO screening_hall VALUES(NULL, 10000, "1관");
INSERT INTO screening_hall VALUES(NULL, 10000, "2관");
INSERT INTO screening_hall VALUES(NULL, 10000, "3관");

INSERT INTO screening_schedule(hall_no, screening_date, screening_day, screening_session, screening_start_time, movie_no) VALUES(1, '2024-06-03', 'MON', 1, '13:00:00', 1);
INSERT INTO screening_schedule(hall_no, screening_date, screening_day, screening_session, screening_start_time, movie_no) VALUES(3, '2024-06-05', 'WEN', 2, '12:00:00', 1);
INSERT INTO screening_schedule(hall_no, screening_date, screening_day, screening_session, screening_start_time, movie_no) VALUES(2, '2024-06-04', 'TUE', 3, '15:00:00', 1);

INSERT INTO seat VALUES(1, 'A01');
INSERT INTO seat VALUES(1, 'A02');
INSERT INTO seat VALUES(1, 'A03');
INSERT INTO seat VALUES(1, 'A04');
INSERT INTO seat VALUES(1, 'A05');
