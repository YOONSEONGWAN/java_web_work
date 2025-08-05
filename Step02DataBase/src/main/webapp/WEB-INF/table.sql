CREATE TABLE gallery (
	num NUMBER PRIMARY KEY,
	title VARCHAR2(100) NOT NULL,
	writer VARCHAR2(20) NOT NULL,
	content CLOB,
	createdAt DATE DEFAULT SYSDATE
);

-- gallery 테이블은 board_seq를 공유

CREATE TABLE gallery_image(
	num NUMBER PRIMARY KEY,
	gallery_num NUMBER REFERENCES gallery(num),
	saveFileName VARCHAR2(100) NOT NULL,
	createdAt DATE DEFAULT SYSDATE
);

CREATE SEQUENCE gallery_image_seq;

CREATE TABLE comments(
	num	NUMBER PRIMARY KEY, -- 댓글의 글번호
	writer VARCHAR2(20) NOT NULL, -- 작성자
	content VARCHAR2(500) NOT NULL, -- 내용
	targetWriter VARCHAR2(20) NOT NULL, -- 누구에게 작성한 댓글인지
	groupNum NUMBER NOT NULL, -- 댓글의 그룹번호
	parentNum NUMBER NOT NULL, -- 부모가 되는 원글의 글 번호
	deleted CHAR(3) DEFAULT 'no', -- 댓글을 삭제 했는지 여부 
	createdAt DATE DEFAULT SYSDATE -- 댓글 작성일 
);

CREATE SEQUENCE comments_seq;

CREATE TABLE board(
	num NUMBER PRIMARY KEY,
	writer VARCHAR2(25) NOT NULL,
	title VARCHAR2(100) NOT NULL,
	content CLOB,
	viewCount NUMBER DEFAULT 0,
	createdAt DATE DEFAULT SYSDATE
);

CREATE SEQUENCE board_seq;


CREATE TABLE USERS(
	num NUMBER PRIMARY KEY, -- 회원의 고유번호
	userName VARCHAR2(20) UNIQUE, -- 아이디
	password VARCHAR2(100) NOT NULL,-- 패스워드
	email VARCHAR2(50) UNIQUE, -- 이메일
	profileImage VARCHAR2(100), -- 프로필 이미지 정보 (처음 가입시에는 null)
	role VARCHAR2(10) DEFAULT 'ROLE_USER', -- 역할 ROLE_USER(일반사용자) | ROLE_STAFF(직원) | ROLE_ADMIN(최고권한자)
	updatedAt DATE, -- 수정 날짜
	createdAt DATE -- 가입 날짜
);

CREATE SEQUENCE users_seq;

CREATE TABLE MEMBER(
	NUM NUMBER PRIMARY KEY,
	NAME VARCHAR2(20),
	ADDR VARCHAR2(50)
);

CREATE SEQUENCE MEMBER_SEQ

CREATE TABLE BOOK (
	NUM NUMBER PRIMARY KEY,
	TITLE VARCHAR2(50),
	AUTHOR VARCHAR2(50),
	PUBLISHER VARCHAR2(50)
);

CREATE SEQUENCE BOOK_SEQ;