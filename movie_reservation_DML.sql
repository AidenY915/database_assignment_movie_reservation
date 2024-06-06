use db1;
-- 유저 데이터 삽입(user)
INSERT INTO user VALUES("user1", "사용자1", "01000000000", "user1@naver.com", "user1", "0");
INSERT INTO user VALUES("root", "관리자", "01011111111", "root@naver.com", "1234", "1");



-- 영화 데이터 삽입(movie)
INSERT INTO movie (movie_no, movie_name, running_time, age_rating, director_name, Genre, release_date, movie_info, rating_information) VALUES
(1, '어벤져스: 엔드게임', 181, 13, '안소니 루소, 조 루소', '액션, 어드벤처, 드라마', '2019-04-26', '남은 어벤져스는 타노스와의 대규모 결전을 위해 사라진 동료들을 되찾는 방법을 찾아야 합니다.', 8.4),
(2, '대부', 175, 17, '프랜시스 포드 코폴라', '범죄, 드라마', '1972-03-24', '마피아 가족의 수장 비토 콜레오네와 그의 아들 마이클 콜레오네의 이야기를 중심으로 한 범죄 드라마.', 9.2),
(3, '쇼생크 탈출', 142, 17, '프랭크 다라본트', '드라마', '1994-09-23', '억울하게 종신형을 선고받은 앤디 듀프레인이 쇼생크 감옥에서 희망을 잃지 않고 탈출을 계획하는 이야기.', 9.3),
(4, '쉰들러 리스트', 195, 17, '스티븐 스필버그', '전기, 드라마, 역사', '1993-12-15', '나치 독일의 사업가 오스카 쉰들러가 유대인 노동자들을 고용해 그들의 목숨을 구한 실화를 바탕으로 한 드라마.', 9.0),
(5, '포레스트 검프', 142, 13, '로버트 저메키스', '드라마, 로맨스', '1994-07-06', '지능은 낮지만 순수한 마음을 가진 포레스트 검프가 인생의 중요한 순간들을 경험하며 겪는 이야기.', 8.8),
(6, '글래디에이터', 155, 17, '리들리 스콧', '액션, 모험, 드라마', '2000-05-05', '로마 제국의 장군 막시무스가 배신당하고 노예 검투사가 되어 복수를 위해 싸우는 이야기.', 8.5),
(7, '다크 나이트', 152, 13, '크리스토퍼 놀란', '액션, 범죄, 드라마', '2008-07-18', '배트맨이 고담시의 범죄를 퇴치하는 과정에서 사이코패스 범죄자 조커와의 치열한 싸움.', 9.0),
(8, '타이타닉', 195, 13, '제임스 카메론', '드라마, 로맨스', '1997-12-19', 'RMS 타이타닉 호의 비극적인 침몰 사건을 배경으로, 다른 신분의 두 청춘, 잭과 로즈의 로맨스.', 7.9),
(9, '라이언 일병 구하기', 169, 17, '스티븐 스필버그', '드라마, 전쟁', '1998-07-24', '노르망디 상륙작전 후, 잃어버린 라이언 일병을 구하기 위해 특수 임무를 수행하는 미군 부대.', 8.6),
(10, '반지의 제왕: 왕의 귀환', 201, 13, '피터 잭슨', '액션, 모험, 드라마', '2003-12-17', '사우론의 군대에 맞서 중간계를 구하기 위해 프로도와 그의 친구들이 마지막 전투를 벌이는 이야기.', 9.0),
(11, '인셉션', 148, 13, '크리스토퍼 놀란', '액션, 모험, SF', '2010-07-16', '꿈을 통해 타인의 잠재의식에 들어가 아이디어를 심는 특수 기술을 사용하는 도둑, 코브와 그의 팀.', 8.8),
(12, '매트릭스', 136, 17, '워쇼스키 형제', '액션, SF', '1999-03-31', '가상 현실 세계와 인간 세계의 경계에서 살아가는 해커 네오가 진실을 깨닫고, 인류를 구하기 위해 싸우는 이야기.', 8.7);





-- 배우 데이터 삽입(actor)
INSERT INTO actor (actor_name) VALUES 
('로버트 다우니 주니어'),
('크리스 에반스'),
('스칼렛 요한슨'),
('말론 브란도'),
('알 파치노'),
('제임스 칸'),
('팀 로빈스'),
('모건 프리먼'),
('윌리엄 새들러'),
('리암 니슨'),
('벤 킹슬리'),
('랄프 파인즈'),
('톰 행크스'),
('로빈 라이트'),
('게리 시니즈'),
('러셀 크로우'),
('호아킨 피닉스'),
('코니 닐슨'),
('크리스찬 베일'),
('히스 레저'),
('아론 에크하트'),
('레오나르도 디카프리오'),
('케이트 윈슬렛'),
('빌리 제인'),
('맷 데이먼'),
('톰 시즈모어'),
('엘리야 우드'),
('이안 맥켈런'),
('비고 모텐슨'),
('조셉 고든 레빗'),
('엘렌 페이지'),
('키아누 리브스'),
('로런스 피시번'),
('캐리앤 모스');


-- 영화-배우 조인 데이터(casting) 

INSERT INTO casting (actor_no, movie_no) VALUES 
(1, 1),  -- 로버트 다우니 주니어 - 어벤져스: 엔드게임
(2, 1),  -- 크리스 에반스 - 어벤져스: 엔드게임
(3, 1),  -- 스칼렛 요한슨 - 어벤져스: 엔드게임
(4, 2),  -- 말론 브란도 - 대부
(5, 2),  -- 알 파치노 - 대부
(6, 2),  -- 제임스 칸 - 대부
(7, 3),  -- 팀 로빈스 - 쇼생크 탈출
(8, 3),  -- 모건 프리먼 - 쇼생크 탈출
(9, 3),  -- 윌리엄 새들러 - 쇼생크 탈출
(10, 4), -- 리암 니슨 - 쉰들러 리스트
(11, 4), -- 벤 킹슬리 - 쉰들러 리스트
(12, 4), -- 랄프 파인즈 - 쉰들러 리스트
(13, 5), -- 톰 행크스 - 포레스트 검프
(14, 5), -- 로빈 라이트 - 포레스트 검프
(15, 5), -- 게리 시니즈 - 포레스트 검프
(16, 6), -- 러셀 크로우 - 글래디에이터
(17, 6), -- 호아킨 피닉스 - 글래디에이터
(18, 6), -- 코니 닐슨 - 글래디에이터
(19, 7), -- 크리스찬 베일 - 다크 나이트
(20, 7), -- 히스 레저 - 다크 나이트
(21, 7), -- 아론 에크하트 - 다크 나이트
(22, 8), -- 레오나르도 디카프리오 - 타이타닉
(23, 8), -- 케이트 윈슬렛 - 타이타닉
(24, 8), -- 빌리 제인 - 타이타닉
(13, 9), -- 톰 행크스 - 라이언 일병 구하기 (중복)
(25, 9), -- 맷 데이먼 - 라이언 일병 구하기
(26, 9), -- 톰 시즈모어 - 라이언 일병 구하기
(27, 10), -- 엘리야 우드 - 반지의 제왕: 왕의 귀환
(28, 10), -- 이안 맥켈런 - 반지의 제왕: 왕의 귀환
(29, 10), -- 비고 모텐슨 - 반지의 제왕: 왕의 귀환
(22, 11), -- 레오나르도 디카프리오 - 인셉션 (중복)
(30, 11), -- 조셉 고든 레빗 - 인셉션
(31, 11), -- 엘렌 페이지 - 인셉션
(32, 12), -- 키아누 리브스 - 매트릭스
(33, 12), -- 로런스 피시번 - 매트릭스
(34, 12); -- 캐리앤 모스 - 매트릭스




--  상영관 데이터 삽입(screening_hall)
INSERT INTO screening_hall (hall_no, standard_price, hall_name) VALUES
(1, 15000, 'IMAX'),
(2, 12000, '4DX'),
(3, 10000, 'Standard'),
(4, 14000, 'Dolby Cinema'),
(5, 13000, 'ScreenX'),
(6, 11000, 'Gold Class'),
(7, 12500, 'VIP Theater'),
(8, 10000, 'Regular'),
(9, 9500, 'Economy'),
(10, 13500, 'Premiere'),
(11, 15000, 'Super Screen'),
(12, 16000, 'Luxury Lounge');



-- 상영 일정 데이터 삽입(screening_schedule)
INSERT INTO screening_schedule (hall_no, screening_date, screening_day, screening_session, screening_start_time, movie_no) VALUES
(1, '2024-06-06', 'Thu', 1, '12:00:00', 1),
(2, '2024-06-07', 'Fri', 2, '14:00:00', 1),
(3, '2024-06-08', 'Sat', 3, '16:00:00', 1),
(4, '2024-06-09', 'Sun', 4, '18:00:00', 1),

(5, '2024-06-06', 'Thu', 1, '20:00:00', 2),
(6, '2024-06-07', 'Fri', 2, '22:00:00', 2),
(7, '2024-06-08', 'Sat', 3, '10:00:00', 2),
(8, '2024-06-09', 'Sun', 4, '12:00:00', 2),

(9, '2024-06-06', 'Thu', 1, '14:00:00', 3),
(10, '2024-06-07', 'Fri', 2, '16:00:00', 3),
(11, '2024-06-08', 'Sat', 3, '18:00:00', 3),
(12, '2024-06-09', 'Sun', 4, '20:00:00', 3),

(1, '2024-06-06', 'Thu', 1, '22:00:00', 4),
(2, '2024-06-07', 'Fri', 2, '10:00:00', 4),
(3, '2024-06-08', 'Sat', 3, '12:00:00', 4),
(4, '2024-06-09', 'Sun', 4, '14:00:00', 4),

(5, '2024-06-06', 'Thu', 1, '16:00:00', 5),
(6, '2024-06-07', 'Fri', 2, '18:00:00', 5),
(7, '2024-06-08', 'Sat', 3, '20:00:00', 5),
(8, '2024-06-09', 'Sun', 4, '22:00:00', 5),

(9, '2024-06-06', 'Thu', 1, '10:00:00', 6),
(10, '2024-06-07', 'Fri', 2, '12:00:00', 6),
(11, '2024-06-08', 'Sat', 3, '14:00:00', 6),
(12, '2024-06-09', 'Sun', 4, '16:00:00', 6),

(1, '2024-06-06', 'Thu', 1, '18:00:00', 7),
(2, '2024-06-07', 'Fri', 2, '20:00:00', 7),
(3, '2024-06-08', 'Sat', 3, '22:00:00', 7),
(4, '2024-06-09', 'Sun', 4, '10:00:00', 7),

(5, '2024-06-06', 'Thu', 1, '12:00:00', 8),
(6, '2024-06-07', 'Fri', 2, '14:00:00', 8),
(7, '2024-06-08', 'Sat', 3, '16:00:00', 8),
(8, '2024-06-09', 'Sun', 4, '18:00:00', 8),

(9, '2024-06-06', 'Thu', 1, '20:00:00', 9),
(10, '2024-06-07', 'Fri', 2, '22:00:00', 9),
(11, '2024-06-08', 'Sat', 3, '10:00:00', 9),
(12, '2024-06-09', 'Sun', 4, '12:00:00', 9),

(1, '2024-06-06', 'Thu', 1, '14:00:00', 10),
(2, '2024-06-07', 'Fri', 2, '16:00:00', 10),
(3, '2024-06-08', 'Sat', 3, '18:00:00', 10),
(4, '2024-06-09', 'Sun', 4, '20:00:00', 10),

(5, '2024-06-06', 'Thu', 1, '22:00:00', 11),
(6, '2024-06-07', 'Fri', 2, '10:00:00', 11),
(7, '2024-06-08', 'Sat', 3, '12:00:00', 11),
(8, '2024-06-09', 'Sun', 4, '14:00:00', 11),

(9, '2024-06-06', 'Thu', 1, '16:00:00', 12),
(10, '2024-06-07', 'Fri', 2, '18:00:00', 12),
(11, '2024-06-08', 'Sat', 3, '20:00:00', 12),
(12, '2024-06-09', 'Sun', 4, '22:00:00', 12);


-- 좌석 데이터 삽입(seat)

DROP PROCEDURE IF EXISTS loopSeatInsert;

DELIMITER $$
CREATE PROCEDURE loopSeatInsert()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE j INT;
    WHILE i <= 12 DO
        SET j = 1;
        WHILE j <= 10 DO
            INSERT INTO SEAT VALUES (i, concat('A', j));
            INSERT INTO SEAT VALUES (i, concat('B', j));
            INSERT INTO SEAT VALUES (i, concat('C', j));
            INSERT INTO SEAT VALUES (i, concat('D', j));
            INSERT INTO SEAT  VALUES (i, concat('E', j));
            SET j = j + 1;
        END WHILE;
        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL loopSeatInsert();

INSERT INTO booking (payment_method, payment_status, payment_amount, payment_date, schedule_no, seat_no, user_id) VALUES
('카드', '결제완료', 15000, '2024-06-04', 1, 'A1', 'user1'),  -- IMAX
('현금', '미결제', 12000, '2024-06-04', 2, 'B3', 'user1'),  -- 4DX
('카드', '결제완료', 10000, '2024-06-04', 3, 'A3', 'user1'),  -- Standard
('카드', '결제완료', 14000, '2024-06-04', 4, 'A4', 'user1'),  -- Dolby Cinema
('현금', '미결제', 13000, '2024-06-04', 5, 'A5', 'user1'),  -- ScreenX
('카드', '결제완료', 11000, '2024-06-05', 6, 'B1', 'user1'),  -- Gold Class
('카드', '결제완료', 12500, '2024-06-05', 7, 'C2', 'user1'),  -- VIP Theater
('현금', '미결제', 10000, '2024-06-05', 8, 'D4', 'user1'),  -- Regular
('카드', '결제완료', 9500, '2024-06-05', 9, 'E5', 'user1'),  -- Economy
('카드', '결제완료', 13500, '2024-06-05', 10, 'A6', 'user1'),  -- Premiere
('현금', '미결제', 15000, '2024-06-05', 11, 'B7', 'user1'),  -- Super Screen
('카드', '결제완료', 16000, '2024-06-05', 12, 'C8', 'user1');  -- Luxury Lounge


-- 예매 티켓 삽입(ticket)	
INSERT INTO movie_ticket (booking_no) VALUES
(1),
(2),
(3),
(4),
(5),
(6);

