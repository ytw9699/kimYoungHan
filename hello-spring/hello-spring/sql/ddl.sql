1. drop table if exists member cascade;

2. 생성
create table member

(

    id bigint generated by default as identity,

    name varchar(255),

    primary key(id)

);

3. 인서트
    insert into member(name) values('spring')
