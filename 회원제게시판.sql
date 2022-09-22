
--1. 예전 SQL방식을 허용한다는 뜻
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;
--2. 사용자 계정 생성
CREATE USER member_lsy IDENTIFIED BY member1234;

--3.생성한 사용자 계정에 권한 부여
GRANT CONNECT,RESOURCE,CREATE VIEW TO  member_lsy;
--CONNECT  : DB 접속 관련 권한을 묶어둔 ROLE입니다.
--RESOURCE : DB 사용을 위한 기본 객체 생성 권한을 묶어돈 ROLE입니다.
--CREATE VIEW : view  생성할 수 있는 권한

--4.테이블 스페이스 할당
ALTER USER member_lsy DEFAULT
TABLESPACE SYSTEM QUOTA UNLIMITED ON SYSTEM;
--시스템 안에서 무제한으로 TABLESPACE를 활용할 공간을 주겠다.

--------------------------------------------------------------------------------------------------


--member_이니셜 계정

--회원 테이블
--회원 번호,아이디,비밀번호,이름,성별,가입일,탈퇴 여부
CREATE TABLE MEMBER (
	MEMBER_NO NUMBER PRIMARY KEY, --왜 회원번호를 PRIMARY KEY로 만들까? ID도 되는뎅?
								  -- (1) 문자보다 숫자 비교가 더 빠르기 때문
								  -- (2) 탈퇴할 경우, 아이디를 다시 만들 수도 있고.. 
								  --     새로운 사람이 가입했을 경우 등 중복돼도 되게 해야 되기 때문...
	MEMBER_ID VARCHAR2(30) NOT NULL,
	MEMBER_PW VARCHAR2(30) NOT NULL,
	MEMBER_NM VARCHAR2(30) NOT NULL,
	MEMBER_GENDER CHAR(1) CHECK( MEMBER_GENDER IN ('M','F')),
	ENROLL_DATE DATE DEFAULT SYSDATE,
	SECESSION_FL CHAR(1) DEFAULT 'N' CHECK (SECESSION_FL IN ('Y','N'))
);

SELECT * FROM MEMBER;


--주석 달기
COMMENT ON COLUMN "MEMBER".MEMBER_NO IS '회원번호';
COMMENT ON COLUMN "MEMBER".MEMBER_ID IS '회원 아이디';
COMMENT ON COLUMN "MEMBER".MEMBER_PW IS '회원 비밀번호';
COMMENT ON COLUMN "MEMBER".MEMBER_NM IS '회원 이름';
COMMENT ON COLUMN "MEMBER".MEMBER_GENDER IS '회원 성별';
COMMENT ON COLUMN "MEMBER".ENROLL_DATE IS '회원 가입일';
COMMENT ON COLUMN "MEMBER".SECESSION_FL IS '탈퇴여부(Y/N)';

--회원번호 SEQUENCE 만들기
CREATE SEQUENCE SEQ_MEMBER_NO 
START WITH 1
INCREMENT BY 1
NOCYCLE 
NOCACHE;--쓰는 이유 :

--회원 가입 INSERT 
INSERT INTO "MEMBER"
VALUES (SEQ_MEMBER_NO.NEXTVAL,'user01','pass01','하나','M',DEFAULT,DEFAULT);

INSERT INTO "MEMBER"
VALUES (SEQ_MEMBER_NO.NEXTVAL,'user02','pass02','둘둘이','M',DEFAULT,DEFAULT);

INSERT INTO "MEMBER"
VALUES (SEQ_MEMBER_NO.NEXTVAL,'user03','pass03','세세','M',DEFAULT,DEFAULT);

COMMIT;

--아이디 중복 확인
--(중복되는 아이디가 입력되어도 탈퇴한 계정이면 중복이 아니라고 판별해보기!)

--ID가 user01이면서 탈퇴하지 않은 회원을 조회(COUNT를 써서 0이 아닌 숫자가 나오면 중복이 있는 것임.)
--(중복이면 1, 아니면 0이 나옴.)
SELECT COUNT(*) FROM "MEMBER"
WHERE MEMBER_ID = 'user01'
AND SECESSION_FL = 'N';

SELECT MEMBER_NO, MEMBER_ID, MEMBER_NM, MEMBER_GENDER, 
TO_CHAR(ENROLL_DATE,'YYYY"년" MM"월" DD"일" HH24:MI:SS') ENROLL_DATE 
FROM "MEMBER"
WHERE MEMBER_ID = 'user01' 
AND MEMBER_PW = 'pass01'
AND SECESSION_FL = 'N'; 
--(아이디/비번 일치하고) 탈퇴를 안 한 회원






--2. 회원 목록 조회(아이디,이름,성별)
--조건1. 탈퇴 회원 미포함
--조건2. 가입일 내림차순
--[참고] 날짜보다 숫자 비교를 하는 게 더 빠름 -ENROLL_DATE보다 MEMBER_NO로 하는 게 더 빠름
--(나중에 가입한 회원의 번호가 더 크니까 MEMBER_NO로 해도 상관 없음...)
SELECT MEMBER_ID, MEMBER_NM , MEMBER_GENDER 
FROM MEMBER
WHERE SECESSION_FL ='N'
ORDER BY  MEMBER_NO DESC;


SELECT * 
FROM MEMBER
WHERE SECESSION_FL ='N'
ORDER BY ENROLL_DATE DESC;

--3. 회원 정보 수정(이름,성별)
UPDATE MEMBER
SET MEMBER_NM = '냐냐2', MEMBER_GENDER ='M'
WHERE MEMBER_ID = 'user03';

--4. 비밀번호 변경
UPDATE "MEMBER" 
SET MEMBER_PW ='새로운비번'
WHERE MEMBER_ID = 'user03'
AND MEMBER_PW = '현재 비밀번호';

--5. 회원 탈퇴(SECESSION_FL 컬럼의 값을 'Y'로 변경)
UPDATE "MEMBER" SET
SECESSION_FL = 'Y'
WHERE MEMBER_NO = ?
AND MEMBER_PW = ?;

SELECT * FROM MEMBER;


ROLLBACK;


---------------------------------------------------------------------

--게시판 테이블
CREATE TABLE "BOARD" (
	BOARD_NO NUMBER CONSTRAINT BOARD_PK PRIMARY KEY,
	BOARD_TITLE VARCHAR2(500) NOT NULL,
	BOARD_CONTENT VARCHAR2(4000) NOT NULL,
	CREATE_DT DATE DEFAULT SYSDATE,
	READ_COUNT NUMBER DEFAULT 0,--첨에 아무 값도 안 넣으면 NULL인데 조회수는 처음에 기본값이 0이니까
	DELETE_FL CHAR(1) DEFAULT 'N' CHECK( DELETE_FL IN ('N','Y')),--이 글이 삭제가 됐는지 안 됐는지 알려주는 신호
	
	MEMBER_NO NUMBER CONSTRAINT BOARD_WRITER_FK REFERENCES MEMBER--누가 이 글을 썼는지 알려주는 외래키 제약조건
);
SELECT * FROMㅋ BOARD;

COMMENT ON COLUMN "BOARD".BOARD_NO 		IS '게시글 번호';
COMMENT ON COLUMN "BOARD".BOARD_TITLE 	IS '게시글 제목';
COMMENT ON COLUMN "BOARD".BOARD_CONTENT IS '게시글 내용';
COMMENT ON COLUMN "BOARD".CREATE_DT 	IS '게시글 작성일';
COMMENT ON COLUMN "BOARD".READ_COUNT 	IS '게시글 조회수';
COMMENT ON COLUMN "BOARD".DELETE_FL 	IS '게시글 삭제여부';
COMMENT ON COLUMN "BOARD".MEMBER_NO 	IS '작성자 회원번호';

--게시판 번호 시퀀스
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE; --NOCHACHE옵션 추가(미리 번호를 생성해놓지 않겠다. 번호가 밀리는 것을 방지하기 위해...)



--게시판 샘플 데이터
SELECT * FROM "MEMBER"
WHERE SECESSION_FL = 'N';

INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '샘플 제목1', '샘플 내용1입니다',
   TO_DATE('2022-09-10 10:30:12', 'YYYY-MM-DD HH24:MI:SS'), DEFAULT, DEFAULT, 3);--3번 회원의 9/10 게시글
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '샘플 제목2', '샘플 내용2입니다',
   TO_DATE('2022-09-11 10:30:12', 'YYYY-MM-DD HH24:MI:SS'), DEFAULT, DEFAULT, 3);--3번 회원의 9/11 게시글
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '샘플 제목3', '샘플 내용3입니다',
   TO_DATE('2022-09-12 10:30:12', 'YYYY-MM-DD HH24:MI:SS'), DEFAULT, DEFAULT, 3);--3번 회원의 9/12 게시글

COMMIT;

---------------------------------------------------------------------
--게시글 번호,  내용, 작성자명, 작성일, 조회수, 댓글 목록(작성일 오름차순)
--댓글 테이블 생성
CREATE TABLE "COMMENT"(
	COMMENT_NO NUMBER,
	COMMENT_CONTENT VARCHAR2(900) NOT NULL,
	CREATE_DT DATE DEFAULT SYSDATE,	
	DELETE_FL CHAR(1) DEFAULT 'N' CHECK (DELETE_FL IN ('N','Y')),
	MEMBER_NO NUMBER REFERENCES "MEMBER", -- 실제로 존재하는 회원만 쓸 수 있다(누가 댓글을 썼는가...)
	BOARD_NO NUMBER REFERENCES "BOARD",--실제로 존재하는 게시글에만 댓을 쓸 수 있음
	CONSTRAINT COMMENT_PK PRIMARY KEY(COMMENT_NO)
);
SELECT * FROM "COMMENT";

--주석 달기
COMMENT ON COLUMN "COMMENT".COMMENT_NO 		IS '댓글 번호';
COMMENT ON COLUMN "COMMENT".COMMENT_CONTENT IS '댓글 내용';
COMMENT ON COLUMN "COMMENT".CREATE_DT 		IS '댓글 작성일';
COMMENT ON COLUMN "COMMENT".DELETE_FL 		IS '댓글 삭제여부';
COMMENT ON COLUMN "COMMENT".MEMBER_NO 		IS '댓글 작성자 회원 번호';
COMMENT ON COLUMN "COMMENT".BOARD_NO 		IS '댓글이 작성된 게시글 번호';


--댓글 번호 시퀀스 생성
CREATE SEQUENCE SEQ_COMMENT_NO NOCACHE;

SELECT * FROM "BOARD"
WHERE DELETE_FL = 'N' ;--3번 게시글

--댓글 샘플 데이터 삽입
INSERT INTO "COMMENT"
VALUES (SEQ_COMMENT_NO.NEXTVAL, '댓글 샘플 1',DEFAULT,DEFAULT,3,3 );
INSERT INTO "COMMENT"
VALUES (SEQ_COMMENT_NO.NEXTVAL, '댓글 샘플 2',DEFAULT,DEFAULT,3,3 );
INSERT INTO "COMMENT"
VALUES (SEQ_COMMENT_NO.NEXTVAL, '댓글 샘플 3',DEFAULT,DEFAULT,3,3 );

COMMIT;

--게시글 목록 조회(댓글 수 포함)
--<댓글 수 나타내는 법>
	--댓글 수를 나타내는 COMMEMT_COUNT컬럼을 만들고 싶다.
	--게시글 번호에 따른 각각의 댓글 수
	--보통은 서브쿼리를 먼저 해석하고 메인쿼리를 해석하는데
	--상관커리는 메인쿼리 먼저 해석하고 서브쿼리 나중에 해석...
	--근데 상관커리 중에서도 단일행 서브쿼리(스칼라 서브쿼리) 쓸거임
	--SELECT절에 (서브쿼리) 별칭 이렇게 넣는다.
	
	--<문제> COMMENT테이블의 BOARD_NO 와 BOARD테이블의 BOARD_NO가 이름이 같음.
	--<해결방법> 각각의 테이블에 별칭 지정...
SELECT BOARD_NO,BOARD_TITLE,MEMBER_NM,READ_COUNT,CREATE_DT,
	( SELECT COUNT(*)
	FROM "COMMENT" C
	WHERE C.BOARD_NO = B.BOARD_NO) COMMENT_COUNT
FROM "BOARD" B
JOIN "MEMBER" USING (MEMBER_NO)
WHERE DELETE_FL = 'N'
ORDER BY BOARD_NO DESC;--최신글부터 조회(CREATE_DT로 해도 되지만 느려지기 때문에 BOARD_NO로 하기 큰 번호일수록 최신글) 

--회원번호가 ''일 때의 댓글 수를 보고 싶다.
SELECT COUNT(*)
FROM "COMMENT"
WHERE BOARD_NO ='3';

--------------------------------------------------------------------------------------------------------------
--~분전,~초전,~시간전...

--날짜끼리는 +/- 가능, 크기 비교 가능	
	
--현재시간(오전 11시 19분임) 1일하고 11시간 지나니까 결과 1.47나옴.

--현재시간이랑 22일 자정은 11.XX == 11시간(687분)(4185초가) 나온다...대충 그정도 지났다는거지

SELECT FLOOR((SYSDATE -TO_DATE('2022-09-22')) * 24 * 60)
FROM DUAL;
--24시간 차이면 1이 나온다.
--12시간 차이면 0.5가 나온다. ==1/24*12니까
--1시간 차이면 1/24
--30분 차이면 1/24를 또 60으로 나눠서 곱하기 30해줘.
--1분 차이 == 1/24/60
--1초 차이 == 1/24/60/60


--WHEN SYSDATE - CREATE-DT < 1/24/60 (현재시간에서 작성시간을 뺏더니 , 그게 1/24/60 (=1분)미만이면 = 몇 초 전이면)
--THEN FLOOR((SYSDATE - CREATE-DT) * 24 * 60 * 60 ) || '초 전'
SELECT BOARD_NO,BOARD_TITLE,MEMBER_NM,READ_COUNT,
--
	CASE 
		WHEN (SYSDATE - CREATE_DT) < 1/24/60
		THEN FLOOR((SYSDATE - CREATE_DT) * 24 * 60 * 60 )|| '초 전'
--		
		WHEN (SYSDATE - CREATE_DT) < 1/24
		THEN FLOOR((SYSDATE - CREATE_DT) * 24 * 60)|| '분 전'
--		
		WHEN (SYSDATE - CREATE_DT) < 1/24
		THEN FLOOR((SYSDATE - CREATE_DT) * 24)|| '시간 전'
--	
		ELSE TO_CHAR(CREATE_DT,'YYYY-MM-DD')
	END CREATE_DT,
--	
	( SELECT COUNT(*)
	FROM "COMMENT" C
	WHERE C.BOARD_NO = B.BOARD_NO) COMMENT_COUNT
FROM "BOARD" B
JOIN "MEMBER" USING (MEMBER_NO)
WHERE DELETE_FL = 'N'
ORDER BY BOARD_NO DESC;--최신글부터 조회(CREATE_DT로 해도 되지만 느려지기 때문에 BOARD_NO로 하기 큰 번호일수록 최신글) 

INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '샘플 제목4', '샘플 내용4입니다',
   TO_DATE('2022-09-10 10:30:12', 'YYYY-MM-DD HH24:MI:SS'), DEFAULT, DEFAULT, 3);
INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, '샘플 제목5', '샘플 내용5입니다',
   SYSDATE, DEFAULT, DEFAULT, 3);
COMMIT;

----------------------------------------------------------------------------------------
--게시글 상세 조회(게시글 내용 조회)

SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT,
	 MEMBER_NO, MEMBER_NM, READ_COUNT,
	 TO_CHAR(CREATE_DT,'YYYY-MM-DD HH24:MI:SS') CREATE_DT
FROM "BOARD"
JOIN "MEMBER" USING(MEMBER_NO)
WHERE DELETE_FL = 'N' --삭제가 안된 글...
AND BOARD_NO = 3; --글 1개만 보고 싶으니까...

--[상세 조회] 후 할 거 1) 특정 게시글의 댓글 목록을 조회해보자!(작성일 오름차순)
SELECT COMMENT_NO, COMMENT_CONTENT, 
	   MEMBER_NO, MEMBER_NM,
	   TO_CHAR(CREATE_DT,'YYYY-MM-DD HH24:MI:SS') CREATE_DT
FROM "COMMENT"
JOIN "MEMBER" USING (MEMBER_NO)
WHERE DELETE_FL ='N'
AND BOARD_NO = 3
ORDER BY COMMENT_NO ASC;


--[상세 조회] 후 할 거 2) 상세 조회된 게시글의 조회수를 증가시키겠다!
--UPDATE일 때만 대입연산자(=) (?)

UPDATE "BOARD" SET
READ_COUNT = READ_COUNT + 1
WHERE BOARD_NO = 3;

COMMIT;





