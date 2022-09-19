
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

SELECT MEMBER_ID, MEMBER_NM , MEMBER_GENDER 
FROM MEMBER;

SELECT * FROM MEMBER;

UPDATE MEMBER
SET MEMBER_NM = '냐냐', MEMBER_GENDER ='M'
WHERE MEMBER_ID = 'user03';

ROLLBACK;

