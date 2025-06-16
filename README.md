# Table Creation
create table myusers(
userId int(4) primary key,
fullName char(15),
email char(30),
phone long,
password char(15),
otp int(6),
status char(15)
);
# Insert Query
insert into myusers(userId,fullName,email,phone,password,status) 
values(101,'Neelaveni','neelimadande23.mca@gmail.com',9052260983,'neelima123','Active');


