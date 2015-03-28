function digital_clock()
{
	var date=new Date();
	var hours=date.getHours();
	var minutes=date.getMinutes();
	var seconds=date.getSeconds();
	var diem = "AM";
	
	/*
	 * Calls the setDiem function to set 'AM' or 'PM'
	 */
	diem=setDiem(hours, diem);
	/*
	*Calls the changeHours function to convert hours from a 24 hr. format to 12. hr. format 
	*/
	hours=changeHours(hours);
	/*
	*Calls the addZero function to add a zero infront of minutes or seconds if they are below 10, i.e.
	*to make it look like 12:07:09, not 12:7:9
	*/
	minutes=addZero(minutes);
	seconds=addZero(seconds);
	/*
	*Puts hours in the element with the hours id,
	*minutes in the element with the minutes id,
	*and seconds in the element with the seconds id
	*/
	document.getElementById('hours').innerHTML = hours;
	document.getElementById('minutes').innerHTML = minutes;
	document.getElementById('seconds').innerHTML = seconds;
	document.getElementById('diem').innerHTML = diem;
	/*
	*Runs every half second
	*/
	setTimeout('digital_clock()', 500);
}
/*
Adds a zero infront of minutes or seconds
*/
function addZero(min_or_sec)
{
	if (min_or_sec < 10)
	{
		min_or_sec="0" + min_or_sec;
	}
	return min_or_sec;
}

/*
 * Sets diem to either 'AM' or 'PM'
 */
function setDiem(h, d)
{
	if (h > 11) 
	{ 
		d="PM";
	}
	return d;
}

/*
 * Converts hours from a 24 hr. format to 12. hr. format 
 */
function changeHours(h)
{
	if (h == 0) 
	{
		h = 12;
	} else if (h > 12) 
	{ 
		diem="PM";
		h = h - 12;
	}
	return h;
}