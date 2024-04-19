create database musica;
use musica;

create table Record_label(
id int primary key auto_increment,
name varchar(100));

create table Artist(
id int primary key auto_increment,
record_label_id int,
foreign key(record_label_id) references record_label(id)
on update cascade on delete cascade,
name varchar(100));

create table Album(
id int primary key auto_increment,
artist_id int,
foreign key(artist_id) references Artist(id)
on update cascade on delete cascade,
name varchar(100),
date_release date
);

create table Song(
id int primary key auto_increment,
album_id int,
foreign key(album_id) references album(id)
on update cascade on delete cascade,
name varchar(100),
duration double
);

INSERT INTO record_label VALUES(1,'Blackened');
INSERT INTO record_label VALUES(2,'Warner Bros');
INSERT INTO record_label VALUES(3,'Universal');
INSERT INTO record_label VALUES(4,'MCA');
INSERT INTO record_label VALUES(5,'Elektra');
INSERT INTO record_label VALUES(6,'Capitol');

-- Artist table: id, name,record_label_id
-- Artist data
INSERT INTO Artist VALUES(1, 1,'Metallica');
INSERT INTO Artist VALUES(2, 1,'Megadeth');
INSERT INTO Artist VALUES(3, 1,'Anthrax');
INSERT INTO Artist VALUES(4, 2,'Eric Clapton');
INSERT INTO Artist VALUES(5, 2,'ZZ Top');
INSERT INTO Artist VALUES(6, 2,'Van Halen');
INSERT INTO Artist VALUES(7, 3,'Lynyrd Skynyrd');
INSERT INTO Artist VALUES(8, 3,'AC/DC');
INSERT INTO Artist VALUES(9, 6,'The Beatles');

-- Album Table: id, name , date_release, artist_id
-- Album data
INSERT INTO album VALUES(1, 1, '...And Justice For All',"1988-09-23");
INSERT INTO album VALUES(2, 1, 'Black Album',"1991-12-19");
INSERT INTO album VALUES(3, 1, 'Master of Puppets',"1986-06-04");
INSERT INTO album VALUES(4, 2, 'Endgame',"2009-11-14");
INSERT INTO album VALUES(5, 2, 'Peace Sells',"1986-03-18");
INSERT INTO album VALUES(6, 3, 'The Greater of 2 Evils',"2004-08-01");
INSERT INTO album VALUES(7, 4, 'Reptile',"2001-02-01");
INSERT INTO album VALUES(8, 4, 'Riding with the King',"2000-10-28");
INSERT INTO album VALUES(9, 5, 'Greatest Hits',"1992-07-10");
INSERT INTO album VALUES(10, 6, 'Greatest Hits',"2004-04-21");
INSERT INTO album VALUES(11, 7, 'All-Time Greatest Hits',"1975-01-30");
INSERT INTO album VALUES(12, 8, 'Greatest Hits',"2003-12-25");
INSERT INTO album VALUES(13, 9, 'Sgt. Pepper''s Lonely Hearts Club Band', "1967-02-16");

-- Song table: id, name,duration,album_id
-- Song data
INSERT INTO song VALUES(1,1,'One',7.25);
INSERT INTO song VALUES(2,1,'Blackened',6.42);
INSERT INTO song VALUES(3,2,'Enter Sandman',5.3);
INSERT INTO song VALUES(4,2,'Sad But True',5.29);
INSERT INTO song VALUES(5,3,'Master of Puppets',8.35);
INSERT INTO song VALUES(6,3,'Battery',5.13);
INSERT INTO song VALUES(7,4,'Dialectic Chaos',2.26);
INSERT INTO song VALUES(8,4,'Endgame',5.57);
INSERT INTO song VALUES(9,5,'Peace Sells',4.09);
INSERT INTO song VALUES(10,5,'The Conjuring',5.09);
INSERT INTO song VALUES(11,6,'Madhouse',4.26);
INSERT INTO song VALUES(12,6,'I am the Law',6.03);
INSERT INTO song VALUES(13,7,'Reptile',3.36);
INSERT INTO song VALUES(14,7,'Modern Girl',4.49);
INSERT INTO song VALUES(15,8,'Riding with the King',4.23);
INSERT INTO song VALUES(16,8,'Key to the Highway',3.39);
INSERT INTO song VALUES(17,9,'Sharp Dressed Man',4.15);
INSERT INTO song VALUES(18,9,'Legs',4.32);
INSERT INTO song VALUES(19,10,'Eruption',1.43);
INSERT INTO song VALUES(20,10,'Hot For Teacher',4.43);
INSERT INTO song VALUES(21,11,'Sweet Home Alabama',4.45);
INSERT INTO song VALUES(22,11,'Free Bird',14.23);
INSERT INTO song VALUES(23,12,'Thunderstruck',4.52);
INSERT INTO song VALUES(24,12,'T.N.T',3.35);
INSERT INTO song VALUES(25,13,'Sgt. Pepper''s Lonely Hearts Club Band', 2.0333);
INSERT INTO song VALUES(26,13,'With a Little Help from My Friends', 2.7333);
INSERT INTO song VALUES(27,13,'Lucy in the Sky with Diamonds', 3.4666);
INSERT INTO song VALUES(28,13,'Getting Better', 2.80);
INSERT INTO song VALUES(29,13,'Fixing a Hole', 2.60);
INSERT INTO song VALUES(30,13,'She''s Leaving Home', 3.5833);
INSERT INTO song VALUES(31,13,'Being for the Benefit of Mr. Kite!',2.6166);
INSERT INTO song VALUES(32,13,'Within You Without You',5.066);
INSERT INTO song VALUES(33,13,'When I''m Sixty-Four',2.6166);
INSERT INTO song VALUES(34,13,'Lovely Rita', 2.7);
INSERT INTO song VALUES(35,13,'Good Morning Good Morning', 2.6833);
INSERT INTO song VALUES(36,13,'Sgt. Pepper''s Lonely Hearts Club Band (Reprise)', 1.3166);
INSERT INTO song VALUES(37,13,'A Day in the Life', 5.65);



select*from album;
select*from artist;
select* from record_label;
select*from song;




select*from artist a inner join album al on a.id=al.artist_id;

drop table album,artist,record_label,song;




-- 1.List all artists for each record label sorted by artist name
SELECT a.name, rl.name AS artist_name
FROM artist a
JOIN record_label rl ON rl.id = a.record_label_id
ORDER BY  a.name;

-- 2.Which record labels have no artists?
SELECT * FROM record_label rl left join artist a on a.record_label_id=rl.id where a.id is null;

-- Query 3. List the number of songs per artist in descending order
select a.name, count(s.album_id) nCanzoni from artist a join album album on album.artist_id=a.id join song s on album.id=s.album_id group by a.name order by nCanzoni desc;

-- query 4 Which artist or artists have recorded the most number of songs?
select a.name, count(s.album_id) nCanzoni from artist a join album album on album.artist_id=a.id join song s on album.id=s.album_id group by a.name order by nCanzoni desc limit 1;

-- 5. Which artist or artists have recorded the least number of songs?
CREATE VIEW viewArtisti AS
SELECT a.name AS artist_name, COUNT(s.id) AS num_songs_recorded
FROM Artist a
LEFT JOIN Album al ON a.id = al.artist_id
LEFT JOIN Song s ON al.id = s.album_id
GROUP BY a.id
HAVING num_songs_recorded = (
    SELECT MIN(song_count)
    FROM (
        SELECT COUNT(s.id) AS song_count
        FROM Artist ar
        LEFT JOIN Album al ON ar.id = al.artist_id
        LEFT JOIN Song s ON al.id = s.album_id
        GROUP BY ar.id
    ) AS song_counts
);
select*from viewArtisti;

drop view viewArtisti;

-- 6  How many artists have recorded the least number of songs? Hint: we can wrap the
-- results of query 5. with another select to give us total artist count.

select count(artist_name) from viewArtisti;



-- 7.For each artist and album how many songs were less than 5 minutes long?
select a.name,count(s.id)
from artist a
inner join album al on a.id=al.artist_id
inner join song s on al.id=s.album_id
where s.duration>5
group by a.name
order by a.name;

-- numero 8  Display a table of all artists, albums, songs and song duration, all ordered in ascending
-- order by artist, album and song

SELECT artist.Name AS
artist, album.name AS
album, song.Name AS
song, song.duration AS
duration
FROM artist
JOIN album ON artist.Id = album.artist_id
JOIN song ON album.id = song.album_id
ORDER BY Artist.Name, album.Name, song.Name;


