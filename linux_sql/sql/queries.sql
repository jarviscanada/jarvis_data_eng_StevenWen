--Modifying Data

-- Question 1: The club is adding a new facility - a spa. We need to add it into the facilities table.
INSERT into cd.facilities(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values (9, 'Spa', 20, 30, 100000, 800);

--Question 2 adding the spa to the facilities table again. This time, though, we want to automatically generate the value for the next facid, rather than specifying it as a constant.

INSERT into cd.facilities(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
    values ((SELECT max(facid)+1 FROM cd.facilities), 'Spa', 20, 30, 100000, 800);

-- Question 3 We made a mistake when entering the data for the second tennis court. The initial outlay was 10000 rather than 8000: you need to alter the data to fix the error.

UPDATE cd.facilities
	SET initialoutlay = 10000
	WHERE name = 'Tennis Court 2';

-- Question 4 We want to alter the price of the second tennis court so that it costs 10% more than the first one. Try to do this without using constant values for the prices, so that we can reuse the statement if we want to.
UPDATE cd.facilities
	SET
		membercost = (SELECT membercost*1.1 FROM cd.facilities WHERE name = 'Tennis Court 1'),
		guestcost = (SELECT guestcost*1.1 FROM cd.facilities WHERE name = 'Tennis Court 1')
WHERE name = 'Tennis Court 2';

-- Question 5 Delete all bookings

DELETE FROM cd.bookings;

------ Question 6 Delete a member from the cd.members table

DELETE FROM cd.members WHERE memid = 37;

-- Basics
------ Question 1 How can you produce a list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost? Return the facid, facility name, member cost, and monthly maintenance of the facilities in question.

SELECT facid, name, membercost, monthlymaintenance
FROM cd.facilities
WHERE membercost < monthlymaintenance*1/50
	AND membercost > 0;

------ Question 2 How can you produce a list of all facilities with the word 'Tennis' in their name?

SELECT *
FROM cd.facilities
WHERE name LIKE '%Tennis%'


------ Question 3 How can you retrieve the details of facilities with ID 1 and 5? Try to do it without using the OR operator.

SELECT *
FROM cd.facilities
WHERE facid in(1,5);


------ Question 4 How can you produce a list of members who joined after the start of September 2012? Return the memid, surname, firstname, and joindate of the members in question.


SELECT memid, surname, firstname, joindate
FROM cd.members
WHERE joindate >= '2012-09-01';


------ Question 5 Combining results from multiple queries

SELECT surname
FROM cd.members m
UNION
SELECT name
FROM cd.facilities f;


-- JOIN

------ Question 1 How can you produce a list of the start times for bookings by members named 'David Farrell'?

SELECT starttime
FROM cd.bookings b
LEFT JOIN cd.members m
ON b.memid = m.memid
WHERE m.surname = 'Farrell'
	AND m.firstname = 'David';


------ Question 2 How can you produce a list of the start times for bookings for tennis courts, for the date '2012-09-21'? Return a list of start time and facility name pairings, ordered by the time.

SELECT b.starttime AS start, f.name
FROM cd.bookings b
LEFT JOIN cd.facilities f
	ON b.facid = f.facid
WHERE b.starttime >= '2012-09-21'
	AND b.starttime < '2012-09-22'
	AND f.name LIKE 'Tennis Court%'
ORDER BY b.starttime;

------ Question 3 How can you output a list of all members, including the individual who recommended them (if any)? Ensure that results are ordered by (surname, firstname).

SELECT m.firstname as memfname, m.surname as memsname,
		r.firstname as recfname, r.surname as recsname
FROM cd.members m
LEFT JOIN cd.members r
ON r.memid = m.recommendedby
ORDER BY memsname, memfname;


------ Question 4 How can you output a list of all members who have recommended another member? Ensure that there are no duplicates in the list, and that results are ordered by (surname, firstname).

SELECT DISTINCT r.firstname, r.surname
FROM cd.members m
LEFT JOIN cd.members r
ON r.memid = m.recommendedby
WHERE m.recommendedby IS NOT NULL
ORDER BY surname, firstname;


------ Question 5 How can you output a list of all members, including the individual who recommended them (if any), without using any joins? Ensure that there are no duplicates in the list, and that each firstname + surname pairing is formatted as a column and ordered.

SELECT DISTINCT m.firstname ||' '|| m.surname as member,
	(
	 SELECT r.firstname ||' '|| r.surname as recommender
	 FROM cd.members r
	 WHERE r.memid = m.recommendedby
	)
FROM cd.members m
ORDER BY member;



-- Aggregation
------ Question 1 Produce a count of the number of recommendations each member has made. Order by member ID.

SELECT recommendedby, count(*)
FROM cd.members
WHERE recommendedby is not NULL
GROUP BY recommendedby
ORDER BY recommendedby;


------ Question 2 Produce a list of the total number of slots booked per facility. For now, just produce an output table consisting of facility id and slots, sorted by facility id.

SELECT facid, sum(slots) as "Total Slots"
FROM cd.bookings
GROUP BY facid
ORDER BY facid;


------ Question 3 Produce a list of the total number of slots booked per facility in the month of September 2012. Produce an output table consisting of facility id and slots, sorted by the number of slots.

SELECT facid, sum(slots) as "Total Slots"
FROM cd.bookings
WHERE starttime >= '2012-09-01'
	and starttime < '2012-10-01'
GROUP BY facid
ORDER BY sum(slots);


------ Question 4 Produce a list of the total number of slots booked per facility per month in the year of 2012. Produce an output table consisting of facility id and slots, sorted by the id and month.

SELECT facid, extract(month from starttime) as month, sum(slots) as "Total Slots"
FROM cd.bookings
WHERE extract(year from starttime) = 2012
GROUP BY facid, month
ORDER BY facid, month;


------ Question 5  Find the total number of members (including guests) who have made at least one booking.

SELECT count(DISTINCT memid)
FROM cd.bookings;


------ Question 6 Produce a list of each member name, id, and their first booking after September 1st 2012. Order by member ID.

SELECT m.surname, m.firstname, m.memid, min(starttime) as starttime
FROM cd.bookings b
INNER JOIN cd.members m
	ON b.memid = m.memid
WHERE starttime >= '2012-09-01'
GROUP BY m.surname, m.firstname, m.memid
ORDER BY memid;


------ Question 7 Produce a list of member names, with each row containing the total member count. Order by join date, and include guest members.

SELECT count(*) over(), firstname, surname
FROM cd.members
ORDER BY joindate;


------ Question 8 Produce a monotonically increasing numbered list of members (including guests), ordered by their date of joining. Remember that member IDs are not guaranteed to be sequential.

SELECT ROW_NUMBER()OVER(ORDER BY joindate), firstname, surname
FROM cd.members
ORDER BY joindate;


------ Question 9

SELECT facid, total
FROM(
	SELECT facid, SUM(slots) as total, rank()OVER(ORDER BY SUM(slots) DESC) as rank
	FROM cd.bookings
	GROUP BY facid
)
WHERE rank = 1;


-- String

------ Question 1 Output the names of all members, formatted as 'Surname, Firstname'

SELECT surname || ', ' || firstname as name
FROM cd.members;


------ Question 2 You've noticed that the club's member table has telephone numbers with very inconsistent formatting. You'd like to find all the telephone numbers that contain parentheses, returning the member ID and telephone number sorted by member ID.

SELECT memid, telephone
FROM cd.members
WHERE telephone ~ '^\([0-9]{3}\) [0-9]{3}-[0-9]{4}$';


------ Question 3

SELECT SUBSTRING(surname, 1, 1) AS letter, COUNT(*)
FROM cd.members
GROUP BY letter
ORDER BY letter;

